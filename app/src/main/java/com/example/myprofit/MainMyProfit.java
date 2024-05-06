package com.example.myprofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.Nullable;

public class MainMyProfit extends AppCompatActivity implements View.OnClickListener {
    ImageView btnlst ,instagram ,facebook,user1;
    TextView WeekSchedule, trainingprogram, mainusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_myprofit);
        btnlst =(ImageView) findViewById(R.id.btnlst);
        btnlst.setOnClickListener(this);
        WeekSchedule = (TextView) findViewById(R.id.WeekSchedule);
        WeekSchedule.setOnClickListener(this);
        instagram = (ImageView) findViewById(R.id.instagram);
        instagram.setOnClickListener(this);
        facebook = (ImageView) findViewById(R.id.facebook);
        facebook.setOnClickListener(this);
        user1 = (ImageView) findViewById(R.id.user1);
        user1.setOnClickListener(this);
        trainingprogram = (TextView) findViewById(R.id.trainingprogram);
        trainingprogram.setOnClickListener(this);
        mainusername = (TextView) findViewById(R.id.mainusername);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.Ihome);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String username = extras.getString("username");

            // Display the received data in the UI
            mainusername.setText("Hello: " + username);
        }

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
        if (v==instagram)
        {
            openInstagramProfile("profitisrael");
        }
        if (v==facebook)
        {
            openWebsite("https://profitgym.co.il/");
        }
        if (v==trainingprogram)
        {
            Intent intent=new Intent(this, TrainigProgram.class);
            startActivity(intent);
        }
    }
    private void openInstagramProfile(String username) {
        Uri uri = Uri.parse("http://instagram.com/_u/" + username);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.instagram.android");

        try {
            startActivity(intent);
        } catch (Exception e) {
            // Instagram app is not installed, open in browser instead
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/" + username)));
        }
    }
    private void openWebsite(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}