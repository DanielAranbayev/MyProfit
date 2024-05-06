package com.example.myprofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPersonal extends AppCompatActivity implements View.OnClickListener {
    Button btnbmi;
    TextView btndetails;
    ImageView user2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_personal);
        btnbmi = (Button)findViewById(R.id.btnbmi);
        btndetails = (TextView) findViewById(R.id.btndetails);
        btnbmi.setOnClickListener(this);
        btndetails.setOnClickListener(this);

        user2 = (ImageView) findViewById(R.id.user2);
        user2.setOnClickListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.Iperson);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.Ihome) {
                startActivity(new Intent(getApplicationContext(), MainMyProfit.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
                return true;
            } else if (itemId == R.id.Iperson) {
                return true;
            } else if (itemId == R.id.Imenu) {
                startActivity(new Intent(getApplicationContext(), MainMonth.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onClick(View v) {
        if (v==btnbmi) {
            btnbmi.setBackground(getResources().getDrawable(R.drawable.edit_textback));
            btndetails.setBackgroundColor(Color.WHITE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView4, BmiFragment.class,null).setReorderingAllowed(true)
                    .addToBackStack("name").commit();
        }
        if (v==btndetails)
        {
            btnbmi.setBackgroundColor(Color.WHITE);
            btndetails.setBackground(getResources().getDrawable(R.drawable.edit_textback));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView4, DetailsFragment.class,null).setReorderingAllowed(true)
                    .addToBackStack("name").commit();
        }
    }
}