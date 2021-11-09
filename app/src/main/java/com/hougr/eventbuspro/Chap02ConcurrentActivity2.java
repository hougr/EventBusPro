package com.hougr.eventbuspro;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hougr.eventbus.BusObserver;
import com.hougr.eventbus.EventBusPro;
import com.hougr.eventbus.util.ThreadUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Chap02ConcurrentActivity2 extends AppCompatActivity {
    private TextView mShowTv;
    private ScheduledExecutorService timerThreadPool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_02_concurrent2);
        mShowTv = findViewById(R.id.show_timer_tv);

        EventBusPro.get()
                .with("multi_thread_count", String.class)
                .observe(this, new BusObserver<String>() {
                    @Override
                    public void onValueChanged(String s) {
                        mShowTv.setText(s);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        endTiming();
    }

    public void send2ThisPage (View v) {
        EventBusPro
                .get()
                .with("send2ThisPage", String.class)
                .setValue("send2ThisPage成功");
    }

    public void startTiming(View v) {
        int[] i = new int[]{0};
        timerThreadPool = Executors.newScheduledThreadPool(3);
        timerThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                i[0]++;
                EventBusPro.get()
                        .with("multi_thread_count", String.class)
                        .setValue(ThreadUtil.isOnMainThread()+": "+i[0]+":"+Thread.currentThread().toString());
            }
        },0,1, TimeUnit.SECONDS);
    }

    private void endTiming() {
        if(timerThreadPool == null) {
            return;
        }
        try {
            // 向学生传达“问题解答完毕后请举手示意！”
            timerThreadPool.shutdown();

            // 向学生传达“XX分之内解答不完的问题全部带回去作为课后作业！”后老师等待学生答题
            // (所有的任务都结束的时候，返回TRUE)
            if(!timerThreadPool.awaitTermination(500, TimeUnit.MILLISECONDS)){
                // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                timerThreadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
            System.out.println("awaitTermination interrupted: " + e);
            timerThreadPool.shutdownNow();
        }

    }
}
