package com.hougr.eventbuspro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hougr.eventbus.EventBusPro;
import com.hougr.eventbus.BusObserver;
import com.hougr.eventbus.util.ThreadUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Chap02ConcurrentActivity extends AppCompatActivity {
    private TextView mShowTv;
    private ScheduledExecutorService timerThreadPool;
    private ExecutorService pool100000;
    int[] counterInts = {0};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_02_concurrent);
        mShowTv = findViewById(R.id.show_timer_tv);

        EventBusPro.get()
                .with("multi_thread_count", Integer.class)
                .observe(this, new BusObserver<Integer>() {
                    @Override
                    public void onValueChanged(Integer s) {
//                        mShowTv.setText(s);
                        Log.d("HougrLog", s+"");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        endTest(timerThreadPool);
        endTest(pool100000);
    }

    public void go2second(View v) {
        startActivity(new Intent(this, Chap02ConcurrentActivity2.class));
    }

    public void testConcurrent(View v) {
        pool100000 = Executors.newFixedThreadPool(8);
        Runnable busSetRunnable = new Runnable() {
            @Override
            public void run() {
//                if (i[0] >=100000) {
//                    return;
//                }
//                synchronized (Chap02ConcurrentActivity.class) {

//                counterInts[0]++;
                EventBusPro.get()
                        .with("multi_thread_count", Integer.class)
                        .setValue(++counterInts[0]);

//                }
            }
        };
        for (int i =0; i <50000; i++) {
            pool100000.execute(busSetRunnable);
        }
    }
//    public void startTiming(View v) {
//        int[] i = new int[]{0};
//        timerThreadPool = Executors.newScheduledThreadPool(3);
//        timerThreadPool.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                i[0]++;
//                EventBusPro.get()
//                        .with("multi_thread_count", Integer.class)
//                        .setValue(ThreadUtil.isOnMainThreaad()+": "+i[0]+":"+Thread.currentThread().toInteger());
//            }
//        },0,1, TimeUnit.SECONDS);
//    }
//
    private void endTest(ExecutorService pool) {
        if(pool == null) {
            return;
        }
        try {
            // 向学生传达“问题解答完毕后请举手示意！”
            pool.shutdown();

            // 向学生传达“XX分之内解答不完的问题全部带回去作为课后作业！”后老师等待学生答题
            // (所有的任务都结束的时候，返回TRUE)
            if(!pool.awaitTermination(500, TimeUnit.MILLISECONDS)){
                // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
            System.out.println("awaitTermination interrupted: " + e);
            pool.shutdownNow();
        }

    }
}
