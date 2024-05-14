package com.example.myprofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.accounts.Account;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.Nullable;

public class MainMyProfit extends AppCompatActivity implements View.OnClickListener {
    ImageView btnmenu ,instagram ,facebook,user1;
    TextView WeekSchedule, trainingprogram, mainusername;
    SharedPreferences sharedPreferences;
    public DrawerLayout drawerLayout;
    public NavigationView menunav;
    private static final int PICK_IMAGE_REQUEST = 1;
    StorageReference storageReference;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_myprofit);
        storageReference = FirebaseStorage.getInstance().getReference();
        loadProfilePic();
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
                    Dialog d = new Dialog(MainMyProfit.this);
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
        if (v==user1)
        {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
        }

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                user1.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            uploadImage(data.getData());
        }
    }
    public void loadProfilePic()
    {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        StorageReference propicReference = storageReference.child("propic/" + currentUser.getUid());
        propicReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(MainMyProfit.this).load(uri).into(user1);
            }
        });
    }
    public void uploadImage(Uri uri)
    {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        StorageReference reference = storageReference.child("propic/" + currentUser.getUid());
        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                uriTask.addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri url = task.getResult();
                        } else {
                        }
                    }
                });
            }
        });
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