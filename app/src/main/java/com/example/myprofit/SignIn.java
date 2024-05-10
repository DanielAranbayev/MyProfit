package com.example.myprofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    TextView btnSignIn;
    EditText etNameSN, etNameAcountSN,etEmailSN;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etNameSN = (EditText) findViewById(R.id.etNameSN);
        etNameAcountSN = (EditText) findViewById(R.id.etNameAcountSN);
        etEmailSN = (EditText) findViewById(R.id.etEmailSN);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE); // Initialize here
        btnSignIn = (TextView) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSignIn) {
            String name = etNameSN.getText().toString();
            String username = etNameAcountSN.getText().toString();
            String email = etEmailSN.getText().toString();

            // Save user data using SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", name);
            editor.putString("username", username);
            editor.putString("email", email);
            editor.apply();

            // Start the main activity
            Intent intent = new Intent(this, MainMyProfit.class);
            startActivity(intent);
        }
    }
}