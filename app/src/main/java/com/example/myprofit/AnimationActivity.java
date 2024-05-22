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
        // Create an Intent to start MainActivity
        Intent intent = new Intent(this,MainActivity.class);
        // Define a Runnable that will start MainActivity and finish this activity after a delay
        Runnable timer = new Runnable()
        {
            @Override
            public void run()
            {
                // Start MainActivity
                startActivity(intent);
                // Finish this activity
                finishActivity(0);
            }
        };
        // Create a Handler to post the Runnable with a delay of 1800 milliseconds (1.8 seconds)
        Handler h = new Handler();
        h.postDelayed(timer, 1800);
    }
}