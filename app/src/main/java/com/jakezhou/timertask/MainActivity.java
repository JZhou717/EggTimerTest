package com.jakezhou.timertask;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timeBar;
    TextView timeText;
    Button startButton;
    boolean running = false;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeText = findViewById(R.id.timeText);

        timeBar = findViewById(R.id.timeBar);
        timeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeText.setText(secsToTime(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(running) {
                    stopTimer();
                }
                else {
                    startTimer();
                }
            }
        });

//        final Handler handler = new Handler();
//        Runnable run = new Runnable() {
//            @Override
//            public void run() {
//                handler.postDelayed(this, 2000);
//
//                Log.i("Run", "2 Seconds has passed");
//            }
//        };
//        handler.post(run);
//        //handler.postDelayed(run, 2000);
    }

    //Converts a given int representation of number of seconds and returns it in M:SS format
    private String secsToTime(int secs) {
        String min = secs / 60 + "";
        String sec = secs % 60 + "";
        if(sec.length() == 1) {
            sec = "0" + sec;
        }

        return min + ":" + sec;
    }

    private void stopTimer() {
        timer.cancel();
        running = false;
        timeBar.setEnabled(true);
        timeBar.setProgress(30);
        timeText.setText(secsToTime(timeBar.getProgress()));
        startButton.setText("START");

    }

    private void startTimer() {
        running = true;
        timeBar.setEnabled(false);
        startButton.setText("STOP");

        timer = new CountDownTimer(timeBar.getProgress() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secs_left = (int) millisUntilFinished / 1000;
                timeText.setText(secsToTime(secs_left));
            }

            @Override
            public void onFinish() {
                MediaPlayer.create(getApplicationContext(), R.raw.chicken).start();
                timeBar.setEnabled(true);
                startButton.setText("RESET");
            }
        };

        timer.start();
    }
}
