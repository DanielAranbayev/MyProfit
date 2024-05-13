package com.example.myprofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.Nullable;

public class MainMyProfit extends AppCompatActivity implements View.OnClickListener {
    ImageView btnmenu ,instagram ,facebook,user1;
    TextView WeekSchedule, trainingprogram, mainusername;
    SharedPreferences sharedPreferences;
    public DrawerLayout drawerLayout;
    public NavigationView menunav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_myprofit);
        btnmenu =(ImageView) findViewById(R.id.btnmenu);
        btnmenu.setOnClickListener(this);
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
        drawerLayout =findViewById(R.id.drawerLayout);
        menunav = (NavigationView) findViewById(R.id.menunav);
        menunav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.info)
                {
//                    Intent intent = new Intent(this, Account.class);
//                    startActivity(intent);
                }
                if(id == R.id.logout)
                {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                if(id == R.id.pp)
                {
                    String url = "https://api.fizikal.co.il/policy.html";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
                return false;
            }
        });

        // Retrieve the saved data from SharedPreferences
        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        // Display the retrieved username in the TextView
        mainusername.setText("Hello " + username);


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
        if (v==btnmenu)
        {
            drawerLayout.openDrawer(menunav);
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