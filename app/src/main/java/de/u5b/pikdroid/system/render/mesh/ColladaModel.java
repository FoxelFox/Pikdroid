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
 * Created by Foxel on 17.09.2014.
 */
public class ColladaModel {

    private XmlPullParser parser;
    private ArrayList<VertexBuffer> buffers;
    private ShortBuffer indices;
    private int polycount;

    public ColladaModel(InputStream file) {

        parser = Xml.newPullParser();
        buffers = new ArrayList<VertexBuffer>();


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
                    buffers.get(buffers.size() -1).stride = Integer.parseInt(parser.getAttributeValue(null, "stride"));
                } else if (parser.getName().equals("polylist")) {
                    // get the number of polygons
                    polycount = Integer.parseInt(parser.getAttributeValue(null, "count"));
                } else if (parser.getName().equals("p")) {
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

        ByteBuffer bb = ByteBuffer.allocateDirect(count * 4);
        bb.order(ByteOrder.nativeOrder());

        VertexBuffer vb = new VertexBuffer();

        // read step by step the next float value
        vb.buffer =  bb.asFloatBuffer();
        while (scanner.hasNextFloat()) {
            vb.buffer.put(scanner.nextFloat());
        }
        vb.buffer.position(0);
        buffers.add(vb);
    }

    private void readIndexArray()  throws XmlPullParserException, IOException {
        parser.next();
        Scanner scanner = new Scanner(parser.getText());

        // read step by step the next index
        int size = polycount;
        for (int i = 0; i < buffers.size(); i++) {
            size *= buffers.get(i).stride;
        }

        ByteBuffer bb = ByteBuffer.allocateDirect(size * 2);
        bb.order(ByteOrder.nativeOrder());

        indices = bb.asShortBuffer();
        while (scanner.hasNextShort()) {
            indices.put(scanner.nextShort());
        }
        indices.position(0);
    }

    private void readSource() throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "source");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            if (parser.getName().equals("float_array")) {

                int cout = Integer.getInteger(parser.getAttributeValue(null, "count"));
                String values = parser.getText();

                System.out.println(cout + "::" + values);
            }
        }

    }

    public Mesh getMesh() {

        return null;
    }

    private class VertexBuffer {
        private FloatBuffer buffer;
        private int stride;

    }
}
