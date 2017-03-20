package com.example.wave_20;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.view.Window;
import android.view.WindowManager;

public class LoadScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Для удаления статус бара
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_load_screen);

        //  Анимация светлой волны
        ImageView wave = (ImageView) findViewById(R.id.animation_wave);
        wave.setImageResource(R.drawable.wave);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.load_animation);

        wave.startAnimation(animation);

        //Анимация темной волны
        ImageView wave_bg= (ImageView) findViewById(R.id.animation_wave_bg);
        wave_bg.setImageResource(R.drawable.wave_bg);
        Animation animation_bg = AnimationUtils.loadAnimation(this,R.anim.load_animation_bg);

        wave_bg.startAnimation(animation_bg);

        //Логотип
        ImageView logo= (ImageView)findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo);
        final Intent intent = new Intent(this, MainActivity.class);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // your code to start second activity. Will wait for 3 seconds before calling this method


                startActivity(intent);

            }
        }, 5000);

    }
}