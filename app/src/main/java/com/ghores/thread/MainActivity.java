package com.ghores.thread;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_THREADS = 8;

    private Handler handler;
    private Thread[] threads;
    private int[] percents;

    private ProgressBar[] progressArrays;
    private TextView[] labels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        progressArrays = new ProgressBar[NUM_THREADS];
        progressArrays[0] = findViewById(R.id.prg_thread1);
        progressArrays[1] = findViewById(R.id.prg_thread2);
        progressArrays[2] = findViewById(R.id.prg_thread3);
        progressArrays[3] = findViewById(R.id.prg_thread4);
        progressArrays[4] = findViewById(R.id.prg_thread5);
        progressArrays[5] = findViewById(R.id.prg_thread6);
        progressArrays[6] = findViewById(R.id.prg_thread7);
        progressArrays[7] = findViewById(R.id.prg_thread8);

        labels = new TextView[NUM_THREADS];
        labels[0] =  findViewById(R.id.txt_thread1);
        labels[1] =  findViewById(R.id.txt_thread2);
        labels[2] =  findViewById(R.id.txt_thread3);
        labels[3] =  findViewById(R.id.txt_thread4);
        labels[4] =  findViewById(R.id.txt_thread5);
        labels[5] =  findViewById(R.id.txt_thread6);
        labels[6] =  findViewById(R.id.txt_thread7);
        labels[7] =  findViewById(R.id.txt_thread8);

        percents = new int[NUM_THREADS];

        final Runnable task = new Runnable() {
            @Override
            public void run() {
                Thread thread = Thread.currentThread();

                int threadIndex = 0;
                for (int i=0; i<threads.length; i++) {
                    if (threads[i] == thread) {
                        threadIndex = i;
                        break;
                    }
                }

                final int finalThreadIndex = threadIndex;

                for (int i=0; i<10000000; i++) {
                    final int percent = i / 100000;
                    percents[finalThreadIndex] = percent;
                }

                for (int i=0; i<NUM_THREADS; i++) {
                    percents[i] = 100;
                }
            }
        };

        threads = new Thread[NUM_THREADS];

        for (int i=0; i<NUM_THREADS; i++) {
            Thread thread = new Thread(task);
            thread.setName("Thread #" + i);
            threads[i] = thread;
        }

        for (int i=0; i<NUM_THREADS; i++) {
            threads[i].start();
        }

        updatePercents();
    }

    private void updatePercents() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<NUM_THREADS; i++) {
                    progressArrays[i].setProgress(percents[i]);
                    labels[i].setText("Thread #" + i + ": " + percents[i] + "%");
                }

                updatePercents();
            }
        }, 15);
    }
}