package com.example.myprofit;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEmail,etPassword;
    TextView btnConnect;
    TextView tvForgotPass,SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEmail =(EditText) findViewById(R.id.etEmail);
        etPassword =(EditText) findViewById(R.id.etPassword);
        btnConnect = (TextView) findViewById(R.id.btnConnect);
        tvForgotPass = (TextView) findViewById(R.id.tvForgotPass);
        SignIn = (TextView) findViewById(R.id.SignIn);

        tvForgotPass.setPaintFlags(tvForgotPass.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnConnect.setOnClickListener(this);
        tvForgotPass.setOnClickListener(this);
        SignIn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(this, MainMyProfit.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (btnConnect==v)
        {
            if (etEmail.getText().toString().length()>0 &&etPassword.getText().toString().length()>0)
            {
                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(String.valueOf(etEmail.getText()),String.valueOf(etPassword.getText()))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else
            {
                Toast.makeText(this, "please fill the field", Toast.LENGTH_SHORT).show();
            }
        }
        if (SignIn==v)
        {
            Intent intent=new Intent(this,SignIn.class);
            startActivity(intent);
        }
    }

}