package com.hougr.eventbuspro;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hougr.eventbus.EventBusPro;
import com.hougr.eventbus.BusObserver;


public class Chap01ObserveModeActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_01_observe_mode2);

        EventBusPro.get()
                .with("sendToNullPage", String.class)
//                .observeForever(new ObserverWrapper<String>() {
                .observe(this, new BusObserver<String>() {
                    @Override
                    public void onValueChanged(String s) {
                        Toast.makeText(getApplication(), "收到："+s, Toast.LENGTH_SHORT).show();
                    }


//                    @Override
//                    public void onChanged(@NonNull String s) {
//                        super.onChanged(s);
//                    }
                });


        EventBusPro.get()
                .with("send2ThisPage", String.class)
//                .observeForever(new Observer<String>() {
                .observe(  this,
                        new BusObserver<String>() {
                            @Override
                            public void onValueChanged(String s) {
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
