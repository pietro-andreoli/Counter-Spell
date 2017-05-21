package com.topfight3r.firstcounter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Handler;
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

/**
 * Created by petro_000 on 2017-05-20.
 */

public class HeldIncrease extends Thread{
    public static boolean heldDown = false;
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
        TextView countText = (TextView) ((Activity)thisContext).findViewById(R.id.count);
        countText.setText(String.valueOf(count));
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
