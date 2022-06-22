package com.pinkoochintantest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        Boolean isLogin = preferences.getBoolean("isLogin", false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                if(isLogin) {
                    i = new Intent(SplashActivity.this, MainActivity.class);
                }else {
                    i = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}