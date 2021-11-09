package com.hougr.eventbuspro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void go2Chap01ObserveMode (View v) {
        startActivity(new Intent(MainActivity.this, Chap01ObserveModeActivity.class));
    }

    public void go2Chap02Concurrent (View v) {
        startActivity(new Intent(MainActivity.this, Chap02ConcurrentActivity.class));
    }

}
