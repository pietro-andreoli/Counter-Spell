package com.topfight3r.firstcounter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import static android.os.SystemClock.uptimeMillis;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;
            View thisView = null;
            Context thisContext = getApplicationContext();
            boolean heldDown = false;
            HeldIncrease2 thisTracker = new HeldIncrease2();
            @Override public boolean onTouch(View v, MotionEvent event) {
                thisView = v;
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                       if(HeldIncrease.heldDown) return true;
                       // if (mHandler != null) return true;
                       // mHandler = new Handler();
                       // mHandler.postDelayed(mAction, 5000);
                        System.out.println("Finger down***********************************");
                        thisTracker.start(thisContext, count);
                        thisTracker.start();
                        System.out.println("Finger down2***********************************");
                        return true;
                        //break;
                    case MotionEvent.ACTION_UP:
                        if(!HeldIncrease.heldDown) return false;
                       // if (mHandler == null) return true;
                       // mHandler.removeCallbacks(mAction);
                       // mHandler = null;
                        System.out.println("Finger up***********************************");
                        thisTracker.kill();
                        break;
                }
                return true;
            }

            Runnable mAction = new Runnable() {
                long startTime =0;
                @Override public void run() {
                    startTime = uptimeMillis();
                    while(heldDown){
                        if(uptimeMillis() - startTime > 700){
                            decreaseCount(thisView, 3);
                        }
                    }


                }
            };


        });
      /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decreaseCount(view);
            }
        });*/
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseCount(view);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static int count = 0;
    public void displayCount (int count){
        TextView countText = (TextView) findViewById(R.id.count);
        countText.setText(String.valueOf(count));
    }

    public void increaseCount(View view){
        count ++;
        displayCount(count);
    }

    public void increaseCount(View view, int incr){
        count += incr;
        displayCount(count);
    }
    public void decreaseCount(View view){
        count --;
        displayCount(count);
    }

    public void decreaseCount(View view, int incr){
        count -= incr;
        displayCount(count);
    }

    class HeldIncrease2 extends Thread{
        public boolean heldDown = false;
        int count = 0;
        Context thisContext = null;
        public void start(Context v, int c){
            thisContext = v;
            //count = c;
            heldDown = true;
        }
        public void kill(){
            heldDown = false;
        }
        public void run(){
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            long startTime =0;
            startTime = uptimeMillis();
            while(heldDown){
                if(uptimeMillis() - startTime > 700){
                    decreaseCount(thisContext, 3);
                    startTime = uptimeMillis();
                }
            }

        }

        public void displayCount (int count){
            //TextView countText = (TextView) findViewById(R.id.count);
           // countText.setText(String.valueOf(count));
            System.out.println(count);
        }

        public void increaseCount(View view){
            count ++;
            displayCount(count);
        }

        public void increaseCount(View view, int incr){
            count += incr;
            displayCount(count);
        }
        public void decreaseCount(View view){
            count --;
            displayCount(count);
        }

        public void decreaseCount(Context c, int incr){
            MainActivity.count -= incr;
            displayCount(MainActivity.count);
        }
    }

}
