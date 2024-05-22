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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    TextView btnSignIn;
    EditText etNameSN, etNameAcountSN,etEmailSN,etPasswordSN;
    SharedPreferences sharedPreferences;
    FirebaseAuth mAuth;

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
        etPasswordSN = (EditText) findViewById(R.id.etPasswordSN);
        mAuth = FirebaseAuth.getInstance();
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
            Intent intent = new Intent(this,MainMyProfit.class);
            if (btnSignIn==v)
            {
                if (etNameSN.getText().toString().length()>0
                        && etNameAcountSN.getText().toString().length()>0
                        && etEmailSN.getText().toString().length()>0
                        && etPasswordSN.getText().toString().length()>0)
                {
                    if (etPasswordSN.getText().toString().length()>=6)
                    {
                        mAuth.createUserWithEmailAndPassword(String.valueOf(etEmailSN.getText()),String.valueOf(etPasswordSN.getText()))
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        startActivity(intent);
                                    }
                                });
                    }
                    else if (etPasswordSN.getText().toString().length()<6)
                    {
                        Toast.makeText(this, "the password must contain 6 or more characters", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "please fill the field", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}