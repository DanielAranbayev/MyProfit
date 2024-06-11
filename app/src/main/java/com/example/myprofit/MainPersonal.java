package com.example.myprofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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

public class MainPersonal extends AppCompatActivity implements View.OnClickListener {
    Button btnbmi;
    TextView btndetails,mainusername;
    ImageView user2;
    public DrawerLayout drawerLayout;
    public NavigationView menunav;
    ImageView btnmenu;
    StorageReference storageReference;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_personal);
        storageReference = FirebaseStorage.getInstance().getReference();
        loadProfilePic();
        btnbmi = (Button)findViewById(R.id.btnbmi);
        btndetails = (TextView) findViewById(R.id.btndetails);
        btnbmi.setOnClickListener(this);
        btndetails.setOnClickListener(this);
        mainusername = (TextView) findViewById(R.id.mainusername);

        user2 = (ImageView) findViewById(R.id.user2);
        user2.setOnClickListener(this);
        btnmenu =(ImageView) findViewById(R.id.btnmenu);
        btnmenu.setOnClickListener(this);

        drawerLayout =findViewById(R.id.drawerLayout);
        menunav = (NavigationView) findViewById(R.id.menunav);


        // Retrieve the saved data from SharedPreferences
        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String username = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString("email", "");

        // Display the retrieved username in the TextView
        mainusername.setText("Hello " + username);
        if (savedInstanceState == null) {
            // If no saved instance state, create a new DetailsFragment instance
            DetailsFragment detailsFragment = DetailsFragment.newInstance(name, username, email);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, detailsFragment, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
        }

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
            } else if (itemId == R.id.Ischedule) {
                startActivity(new Intent(getApplicationContext(), MainMonth.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }

            return false;
        });

        menunav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.info)
                {
                    Dialog d = new Dialog(MainPersonal.this);
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
    //reloading the pic from firebase
    public void loadProfilePic()
    {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        StorageReference propicReference = storageReference.child("propic/" + currentUser.getUid());
        propicReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(MainPersonal.this).load(uri).into(user2);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v==btnbmi) {
            btnbmi.setBackground(getResources().getDrawable(R.drawable.edit_textback));
            btndetails.setBackgroundColor(Color.WHITE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, new BmiFragment(), null) // Create instance of fragment
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
        }
        if (v==btndetails)
        {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String name = sharedPreferences.getString("name", "");
            String username = sharedPreferences.getString("username", "");
            String email = sharedPreferences.getString("email", "");
            DetailsFragment detailsFragment = DetailsFragment.newInstance(name, username, email);
            btnbmi.setBackgroundColor(Color.WHITE);
            btndetails.setBackground(getResources().getDrawable(R.drawable.edit_textback));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, detailsFragment, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
        }
        if (v==btnmenu)
        {
            drawerLayout.openDrawer(menunav);
        }
    }
}