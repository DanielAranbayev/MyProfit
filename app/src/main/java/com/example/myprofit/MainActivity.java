package com.example.myprofit;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etPhoneNumber,etPassword;
    Button btnConnect;
    TextView tvForgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPhoneNumber =(EditText) findViewById(R.id.etPhoneNumber);
        etPassword =(EditText) findViewById(R.id.etPassword);
        btnConnect = (Button) findViewById(R.id.btnConnect);
        tvForgotPass = (TextView) findViewById(R.id.tvForgotPass);

        tvForgotPass.setPaintFlags(tvForgotPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnConnect.setOnClickListener(this);
        tvForgotPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (btnConnect==v)
        {
            Intent intent=new Intent(this,MainMyProfit.class);
            startActivity(intent);
        }
    }
}