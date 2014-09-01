package de.u5b.pikdroid.android;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

import de.u5b.pikdroid.R;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.system.render.MySurfaceView;

/**
 * The main AndroidActivity
 */
public class PikdroidActivity extends Activity {

    private Engine engine;
    private TextView pikdroidCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activity Flags
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                             WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN |
                             WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //setContentView(R.layout.activity_pikdroid);
        //pikdroidCount = (TextView) findViewById(R.id.text_pikdroid_count);
        //pikdroidCount.setText("Pikdroids: " + 0);


        // Create Rendering Surface
        //MySurfaceView view = (MySurfaceView) findViewById(R.id.view);
        MySurfaceView view = new MySurfaceView(this);
        engine = new Engine(this);
        view.setEngine(engine);
        engine.play();
        setContentView(view);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pikdroid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void update() {

        //pikdroidCount.setText("Pikdroids: " + engine.getPikdroidCount());

    }
}
