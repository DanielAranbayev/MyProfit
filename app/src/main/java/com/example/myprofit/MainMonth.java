package com.example.myprofit;

import static com.example.myprofit.CalendarUtils.daysInMonthArray;
import static com.example.myprofit.CalendarUtils.monthYearFromDate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class MainMonth extends AppCompatActivity implements CalendarAdapter.OnItemListener, View.OnClickListener {
    TextView monthYearText,mainusername;
    RecyclerView calendarRecyclerView;
    ImageView user3;
    public DrawerLayout drawerLayout;
    public NavigationView menunav;
    ImageView btnmenu;
    StorageReference storageReference;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_month);
        storageReference = FirebaseStorage.getInstance().getReference();
        loadProfilePic();
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.Imenu);
        user3 = (ImageView) findViewById(R.id.user3);
        user3.setOnClickListener(this);
        btnmenu =(ImageView) findViewById(R.id.btnmenu);
        btnmenu.setOnClickListener(this);

        menunav = (NavigationView) findViewById(R.id.menunav);

        mainusername = (TextView) findViewById(R.id.mainusername);

        // Retrieve the saved data from SharedPreferences
        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        // Display the retrieved username in the TextView
        mainusername.setText("Hello " + username);


        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.Ihome) {
                startActivity(new Intent(getApplicationContext(), MainMyProfit.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
                return true;
            } else if (itemId == R.id.Iperson) {
                startActivity(new Intent(getApplicationContext(), MainPersonal.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
                return true;
            } else if (itemId == R.id.Imenu) {
                return true;
            }
            return false;
        });

        drawerLayout =findViewById(R.id.drawerLayout);
        menunav = (NavigationView) findViewById(R.id.menunav);
        menunav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.info)
                {
                    Dialog d = new Dialog(MainMonth.this);
                    d.setContentView(R.layout.info);
                    d.setCancelable(true);
                    d.show();
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
    }
    public void loadProfilePic()
    {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        StorageReference propicReference = storageReference.child("propic/" + currentUser.getUid());
        propicReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(MainMonth.this).load(uri).into(user3);
            }
        });
    }
    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);

    }
    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));// הצגת התאריך והשנה הנוכחיים
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);// קבלת רשימת התאריכים בחודש הנבחר

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);// יצירת מתאם לרשימת התאריכים
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);// הגדרת סידור התצוגה של הרשימה
        calendarRecyclerView.setLayoutManager(layoutManager);// הגדרת המנהל של התצוגה
        calendarRecyclerView.setAdapter(calendarAdapter);// הגדרת המתאם של התצוגה
    }


    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date !=null)
        {
        CalendarUtils.selectedDate = date;// קביעת התאריך הנבחר
        setMonthView();// עדכון התצוגה
        }
    }

    @Override
    public void onClick(View v) {
        if (v==btnmenu)
        {
            drawerLayout.openDrawer(menunav);
        }
    }
}