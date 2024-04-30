package com.example.myprofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMyProfit extends AppCompatActivity implements View.OnClickListener {
    ImageView btnlst;
    Button WeekSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_myprofit);
        btnlst =(ImageView) findViewById(R.id.btnlst);
        btnlst.setOnClickListener(this);
        WeekSchedule = (Button) findViewById(R.id.WeekSchedule);
        WeekSchedule.setOnClickListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.Ihome);


        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.Ihome) {
                return true;
            } else if (itemId == R.id.Iperson) {
                startActivity(new Intent(getApplicationContext(), MainPersonal.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.Imenu) {
                startActivity(new Intent(getApplicationContext(), MainMenu.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onClick(View v) {
        if (v==btnlst)
        {
            Intent intent=new Intent(this, MainMonth.class);
            startActivity(intent);
        }
        if (v==WeekSchedule)
        {
            Intent intent=new Intent(this, WeekViewActivity.class);
            startActivity(intent);
        }

    }
}