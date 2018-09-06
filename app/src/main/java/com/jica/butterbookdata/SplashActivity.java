package com.jica.butterbookdata;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jica.butterbookdata.thread.ReadFileToDB;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReadFileToDB readFileToDB = new ReadFileToDB(this);
        readFileToDB.start();
        try {
            Thread.sleep(1000);
            Intent intent = new Intent(this, ViewActivity.class);
            startActivity(intent);
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
