package com.example.myprofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    TextView btnSignIn;
    EditText etNameSN, etNameAcountSN,etEmailSN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etNameSN = (EditText) findViewById(R.id.etNameSN);
        etNameAcountSN = (EditText) findViewById(R.id.etNameAcountSN);
        etEmailSN = (EditText) findViewById(R.id.etEmailSN);

        btnSignIn = (TextView) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==btnSignIn)
        {
            String Name = etNameSN.getText().toString();
            String Username = etNameAcountSN.getText().toString();
            String Email = etEmailSN.getText().toString();

            Intent intent = new Intent(this, MainMyProfit.class);
            intent.putExtra("name", Name);
            intent.putExtra("username", Username);
            intent.putExtra("email", Email);
            intent.putExtra("fragment", "your_fragment_tag");
            startActivity(intent);

        }
    }
}