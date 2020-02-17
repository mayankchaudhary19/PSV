package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapplication.UserSession.UserSessionActivity;

public class SplashActivity extends AppCompatActivity {
    AnimationDrawable anim;
    private static int splashTimeOut = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();


        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        anim = (AnimationDrawable) constraintLayout.getBackground();
        anim.setEnterFadeDuration(1000);
        anim.setExitFadeDuration(1000);
        anim.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, UserSessionActivity.class);
                startActivity(i);
                finish();
            }
        }, splashTimeOut);
    }
}