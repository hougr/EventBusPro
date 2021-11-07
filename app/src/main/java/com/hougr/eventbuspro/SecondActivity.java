package com.hougr.eventbuspro;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.hougr.eventbus.EventBusPro;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        EventBusPro.get()
                .with("sendToNullPage", String.class)
//                .observeForever(new Observer<String>() {
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(getApplication(), "收到："+s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void sendToPage1(View v) {
        EventBusPro
                .get()
                .with("sendToPage1", String.class)
                .setValue("sendToPage1成功");
    }

    public void finishSelf (View v) {
        this.finish();
    }
}
