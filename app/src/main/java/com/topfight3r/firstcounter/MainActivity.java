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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.os.SystemClock.uptimeMillis;

public class MainActivity extends AppCompatActivity {
    final int SPINNER_BUFFER = 50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnTouchListener(new View.OnTouchListener() {
            final int MODE = 1;
            private Handler mHandler;
            View thisView = null;
            Context thisContext = getApplicationContext();
            boolean heldDown = false;
            HeldIncrease2 thisTracker = new HeldIncrease2();
            @Override public boolean onTouch(View v, MotionEvent event) {
                thisView = v;
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("Finger down***********************************");
                        thisTracker.start(thisContext, MODE);
                        thisTracker.start();
                        displayCount(count);
                        System.out.println("Thread started...***********************************");
                        return true;
                        //break;
                    case MotionEvent.ACTION_UP:
                        System.out.println("Killing thread***********************************");
                        if(!thisTracker.isLongPressed){
                            decreaseCount(thisView);
                        }
                        thisTracker.kill();
                        return true;
                    default: displayCount(count);
                }
                return false;
            }



        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseCount(view);
            }
        });

        fab2.setOnTouchListener(new View.OnTouchListener() {
            final int MODE = 0;
            private Handler mHandler;
            View thisView = null;
            Context thisContext = getApplicationContext();
            boolean heldDown = false;
            HeldIncrease2 thisTracker = new HeldIncrease2();
            @Override public boolean onTouch(View v, MotionEvent event) {
                thisView = v;
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("Finger down on increase button");
                        thisTracker.start(thisContext, MODE);
                        thisTracker.start();
                        displayCount(count);
                        System.out.println("Thread started...***********************************");
                        return true;
                    //break;
                    case MotionEvent.ACTION_UP:
                        System.out.println("Killing thread***********************************");
                        if(!thisTracker.isLongPressed){
                            increaseCount(thisView);
                        }
                        thisTracker.kill();
                        return true;
                    default: displayCount(count);
                }
                return false;
            }



        });

        Spinner lifeTotal = (Spinner) findViewById(R.id.life_total_spinner);
        fillLifeSpinner(lifeTotal);

    displayCount(count);
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
        Spinner spinner = (Spinner)findViewById(R.id.life_total_spinner);
        fillLifeSpinner(spinner);
        spinner.setSelection(count+spinner.get-1);
        System.out.println("ayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy"+(count+SPINNER_BUFFER-1));
    }

    public void fillLifeSpinner(Spinner spinner){
        List<Integer> lifeList = new ArrayList<Integer>();
        for(int i = count + SPINNER_BUFFER; i >= count - SPINNER_BUFFER; i--){
            if (i >= 0){
                lifeList.add(i);
            }else{
                break;
            }
        }
        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, lifeList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(count);
        //findViewById(R.id.life_total_spinner).set
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

    public void resetHealth20(View v){
        count = 20;
        displayCount(count);
    }

    public void resetHealth40(View v){
        count = 40;
        displayCount(count);
    }

    class HeldIncrease2 extends Thread{
        //Boolean that states whether the button is in a held down state, or if the user is just clicking.
        //True if long press, False if click
        public boolean isLongPressed = false;
        //Boolean checking if the users finger is on the button. True if the users finger is down, false otherwise.
        public boolean heldDown = false;
        //Integer for keeping track of the current life total
        int count = 0;
        //The amount of time required for it to be considered a long press
        long pressDuration = 700;
        //The context of the UI Thread
        Context thisContext = null;
        //the mode for incrementing or decrementing
        int mode = -1;
        /*My start function, sets the current life total and initalizes heldDown
        @param v The context of the UI Thread
        @param mode the mode chosen for incrementing. Options: 0 = increase, 1 = decrease
         */
        public void start(Context v, int mode){
            this.thisContext = v;
            this.mode = mode;
            this.heldDown = true;
        }

        /*My kill function. Stops the loop in run() and uses isLongPressed to state that the user
        is not long pressing.
         */
        public void kill(){
            this.heldDown = false;
            this.isLongPressed = false;
        }

        /*Built in run function. Starts the loop that increases the life total by an increment
         */
        public void run(){

            //Necessary thread safety
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
            //The start time of the long press.
            long startTime =0;
            //Sets the start time to the current time
            startTime = uptimeMillis();
            //Loops until the users finger is lifted
            while(heldDown){
                //If the user has had their finger held down for longer than the specified number of
                // miliseconds, consider it a held down action and start incrementing
                if(uptimeMillis() - startTime > pressDuration){
                    //States that the user is holding down the button
                    this.heldDown = true;
                    //States that the user is attempting a long press and not a click
                    this.isLongPressed = true;
                    //Checks which mode was chosen
                    if (mode == 1) {
                        //Decreases count
                        this.decreaseCount(3);
                    }else{
                        //Increases count
                        this.increaseCount(3);
                    }
                    //Resets the start time
                    startTime = uptimeMillis();
                }
            }

        }

        public void increaseCount(){
            MainActivity.count ++;
        }

        public void increaseCount(int incr){
            MainActivity.count += incr;
        }
        public void decreaseCount(){
            MainActivity.count --;
        }

        public void decreaseCount(int incr){
            MainActivity.count -= incr;
        }
    }

}
