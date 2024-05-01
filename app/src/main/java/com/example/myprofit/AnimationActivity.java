package com.example.myprofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AnimationActivity extends AppCompatActivity {
    Animation splashAnimation;
    ImageView splashLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        splashAnimation = AnimationUtils.loadAnimation(this,R.anim.logo_animation);
        splashLogo = findViewById(R.id.logo);
        splashLogo.startAnimation(splashAnimation);
        Intent intent = new Intent(this,MainActivity.class);
        Runnable timer = new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(intent);
                finishActivity(0);
            }
        };
        Handler h = new Handler();
        h.postDelayed(timer, 4000);
    }
}