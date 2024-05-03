package com.example.myprofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    TextView btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn = (TextView) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==btnSignIn)
        {
            Intent intent=new Intent(this,MainMyProfit.class);
            startActivity(intent);
        }
    }
}