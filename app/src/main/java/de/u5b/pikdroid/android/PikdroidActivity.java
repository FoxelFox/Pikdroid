package de.u5b.pikdroid.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import de.u5b.pikdroid.R;
import de.u5b.pikdroid.game.Engine;
import de.u5b.pikdroid.system.input.InputSystem;
import de.u5b.pikdroid.system.render.RenderSystem;

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

        setContentView(R.layout.activity_pikdroid);
        pikdroidCount = (TextView) findViewById(R.id.text_pikdroid_count);
        pikdroidCount.setText("Pikdroids: " + 0);


        // Create Rendering Surface
        MySurfaceView view = (MySurfaceView) findViewById(R.id.view);
        //MySurfaceView view = new MySurfaceView(this);
        engine = new Engine(this);
        engine.play();

        view.setRenderer(engine.getSystemManager().getSystem(RenderSystem.class));
        view.setInputHandler(engine.getSystemManager().getSystem(InputSystem.class));


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
        pikdroidCount.post(new Runnable() {
            public void run() {
                pikdroidCount.setText("Pikdroids: " + engine.getPikdroidCount());
            }
        });
    }
}
