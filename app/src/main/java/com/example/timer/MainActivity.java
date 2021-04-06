package com.example.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    boolean running;
    Timer timer;
    TimerTask timerTask;
    int sec;
    TextView start_time_tv,end_time_tv,curr_time_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        running = false;
        start_time_tv = findViewById(R.id.tv_start_time);
        end_time_tv = findViewById(R.id.tv_end_time);
        curr_time_tv = findViewById(R.id.tv_curr_time);
        sec = 0;
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        
        int key_code  = event.getKeyCode();
        int action = event.getAction();

        if(key_code == KeyEvent.KEYCODE_VOLUME_DOWN && KeyEvent.ACTION_DOWN == action)
        {
            return true;
        }
        if(key_code == KeyEvent.KEYCODE_VOLUME_DOWN && KeyEvent.ACTION_UP == action){
            if(!running){
                LocalDateTime now = LocalDateTime.now();
                start_time_tv.setText(""+now.getHour()+" : "+now.getMinute()+" : "+now.getSecond());
                end_time_tv.setText("Yet to determine");
                timer = new Timer();
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        int i = sec;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                changeView(i);
                            }
                        });
                    }
                };

                timer.schedule(timerTask,0,1000);
                running = true;
            }
            else{
                LocalDateTime now = LocalDateTime.now();
                end_time_tv.setText(""+now.getHour()+" : "+now.getMinute()+" : "+now.getSecond());
                timer.cancel();
                running = false;
                sec = 0;
            }

        }
        if(key_code==KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
    public void changeView(int i){
        sec++;

        String x = ""+((int)(i/3600))+" : "+((int)(i/60))+" : "+(i%60);
        curr_time_tv.setText(x);
    }


}