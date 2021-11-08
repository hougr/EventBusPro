package com.hougr.eventbuspro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hougr.eventbus.EventBusPro;
import com.hougr.eventbus.ObserverWrapper;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        EventBusPro.get()
                .with("sendToPage1", String.class)
//                .observeForever(new Observer<String>() {
                .observe(this, new ObserverWrapper<String>() {
//                    @Override
//                    public void onChanged(@Nullable String s) {
//                        Toast.makeText(getApplication(), "收到："+s, Toast.LENGTH_SHORT).show();
//                    }

                    @Override
                    public void onValueChanged(String s) {
                        Toast.makeText(getApplication(), "收到："+s, Toast.LENGTH_SHORT).show();
                    }

                });


        EventBusPro.get()
                .with("send2ThisPage", String.class)
//                .observeForever(new Observer<String>() {
                .observe(  this,
                        new ObserverWrapper<String>() {
                            @Override
                            public void onValueChanged(String s) {
                                Toast.makeText(getApplication(), "收到："+s, Toast.LENGTH_SHORT).show();
                            }
                        });
    }

    public void sendToNullPage (View view) {
        EventBusPro
                .get()
                .with("sendToNullPage", String.class)
                .setValue("sendToNullPage成功");
    }

    public void openPage2 (View v) {
        startActivity(new Intent(MainActivity.this, SecondActivity.class));
    }

    public void send2ThisPage (View v) {
        EventBusPro
                .get()
                .with("send2ThisPage", String.class)
                .setValue("send2ThisPage成功");
    }
}
