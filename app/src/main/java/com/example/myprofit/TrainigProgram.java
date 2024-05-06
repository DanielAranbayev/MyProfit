package com.example.myprofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TrainigProgram extends AppCompatActivity implements View.OnClickListener {
    TextView btntoning,btnmass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainig_program);
        btntoning = (TextView) findViewById(R.id.btntoning);
        btnmass = (TextView) findViewById(R.id.btnmass);
        btntoning.setOnClickListener(this);
        btnmass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==btntoning) {
            btntoning.setBackground(getResources().getDrawable(R.drawable.btntraining));
            btnmass.setBackgroundColor(Color.WHITE);
            btnmass.setTextColor(Color.BLACK);
            btntoning.setTextColor(Color.WHITE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView3, ToningFragment.class,null).setReorderingAllowed(true)
                    .addToBackStack("name2").commit();
        }
        if (view==btnmass)
        {
            btntoning.setBackgroundColor(Color.WHITE);
            btnmass.setBackground(getResources().getDrawable(R.drawable.btntraining));
            btntoning.setTextColor(Color.BLACK);
            btnmass.setTextColor(Color.WHITE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView3, MassFragment.class,null).setReorderingAllowed(true)
                    .addToBackStack("name2").commit();
        }
    }
}