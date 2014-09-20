package de.u5b.pikdroid.system.render.mesh;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * This class is used to load COLLADA Files
 * Created by Foxel on 17.09.2014.
 */
public class ColladaModel {

    private XmlPullParser parser;
    private ArrayList<FloatBuffer> buffers;
    private ArrayList<Integer> strides;
    private ShortBuffer indices;
    private Mesh.Semantic[] semantics;
    private int polycount;

    public ColladaModel(InputStream file) {

        parser = Xml.newPullParser();
        buffers = new ArrayList<FloatBuffer>();
        strides = new ArrayList<Integer>();

        try {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(file, null);
            parser.nextTag();
            readMesh();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readMesh() throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "COLLADA");

        while (true) {
            if (parser.next() == XmlPullParser.START_TAG) {
                if (parser.getName().equals("float_array")) {
                    readFloatArray();
                } else if (parser.getName().equals("accessor")) {
                    // read stride for last VertexBuffer
                    strides.add(Integer.parseInt(parser.getAttributeValue(null, "stride")));
                } else if (parser.getName().equals("polylist")) {
                    readIndexArray();
                    break;
                }
            }
        }
    }

    private void readFloatArray() throws XmlPullParserException, IOException {
        int count = Integer.parseInt(parser.getAttributeValue(null, "count"));
        parser.next();

        Scanner scanner = new Scanner(parser.getText());
        scanner.useLocale(Locale.ENGLISH);

        // allocate memory for FloatBuffer
        ByteBuffer bb = ByteBuffer.allocateDirect(count * 4);
        bb.order(ByteOrder.nativeOrder());

        // read step by step the next float value
        FloatBuffer fb = bb.asFloatBuffer();
        while (scanner.hasNextFloat()) {
            fb.put(scanner.nextFloat());
        }
        fb.position(0);
        buffers.add(fb);
    }

    private void readIndexArray()  throws XmlPullParserException, IOException {
        polycount = Integer.parseInt(parser.getAttributeValue(null, "count"));

        // read semantics
        String semantic;
        semantics = new Mesh.Semantic[buffers.size()];
        for (int i = 0; i < buffers.size(); i++) {
            skipTo("input");
            semantic = parser.getAttributeValue(null, "semantic");
            if (semantic.equals(Mesh.Semantic.VERTEX.name())) {
                semantics[i] = Mesh.Semantic.VERTEX;
            } else if (semantic.equals(Mesh.Semantic.NORMAL.name())) {
                semantics[i] = Mesh.Semantic.NORMAL;
            } else if (semantic.equals(Mesh.Semantic.TEXCOORD.name())) {
                semantics[i] = Mesh.Semantic.TEXCOORD;
            }
        }

        // read Indices
        skipTo("p");
        parser.next();
        Scanner scanner = new Scanner(parser.getText());

        // calculate Buffer Size
        int size = polycount * buffers.size() * 3;

        // allocate memory
        ByteBuffer bb = ByteBuffer.allocateDirect(size * 2);
        bb.order(ByteOrder.nativeOrder());

        // read step by step the next index
        indices = bb.asShortBuffer();
        while (scanner.hasNextShort()) {
            indices.put(scanner.nextShort());
        }
        indices.position(0);
    }

    private void skipTo(String name) throws XmlPullParserException, IOException{
        while (true) {
            if (parser.next() == XmlPullParser.START_TAG) {
                if (parser.getName().equals(name)) {
                    break;
                }
            }
        }
    }

    public FloatBuffer getInterleavedVertexBuffer() {

        // calc size per vertex
        int valuePerVertex = 0;
        for (Integer stride : strides) {
            valuePerVertex += stride;
        }
        // multiply with polyCount and size of float
        int size = valuePerVertex * polycount * 3;

        // allocate memory for interleaved vertex buffer
        ByteBuffer bb = ByteBuffer.allocateDirect(size * 4);
        bb.order(ByteOrder.nativeOrder());

        FloatBuffer interleaved = bb.asFloatBuffer();

        for (int i = 0; i < polycount * (buffers.size() * 3); i += buffers.size()) {   // i vertex
            for (int j = 0; j < buffers.size(); j++) {          // j buffer
                for (int k = 0; k < strides.get(j); k++) {      // k offset for entry int j buffer
                    interleaved.put(buffers.get(j).get(indices.get(i+j) * strides.get(j) +k));
                }
            }
        }
        interleaved.position(0);
        return interleaved;
    }

    public Mesh.Semantic[] getSemantics() {
        return semantics;
    }

    public int[] getStrides() {
        int s[] = new int[strides.size()];
        for (int i = 0; i < strides.size(); ++i) {
            s[i] = strides.get(i);
        }
        return s;
    }

    public int getPolyCount() {
        return polycount;
    }

}
