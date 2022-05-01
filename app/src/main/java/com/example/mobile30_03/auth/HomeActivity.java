package com.example.mobile30_03.auth;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile30_03.R;
import com.example.mobile30_03.models.AppUsers;
import com.example.mobile30_03.models.Music;
import com.example.mobile30_03.models.User;
import com.example.mobile30_03.music.WellPlayedActivity;
import com.example.mobile30_03.utils.MediaPlayerManager;
import com.example.mobile30_03.utils.SongsAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        TextView tvPermission = findViewById(R.id.tvPermission);

        //Intent operations
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", 1);
        String logType = intent.getStringExtra("type");
        User currentUser = AppUsers.getUserById(userId);
        tvWelcome.setText("Welcome " + currentUser.username);

        //Send welcome mail if log type is Register
        if (logType.equals("Kayıt")) sendMail(currentUser);

        //Request Permission
        ActivityResultLauncher<String> requestReadWritePermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        //Granted
                        Snackbar.make(findViewById(R.id.activity_home), "OH YES! THANKS FOR ALLOWING US", Snackbar.LENGTH_SHORT).show();
                        proceed(currentUser);
                    } else {
                        //Denied
                        Snackbar.make(findViewById(R.id.activity_home), "OH NO... WE ARE NOT ALLOWED", Snackbar.LENGTH_SHORT).show();
                    }
                });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            //Has permission
            tvPermission.setText(R.string.permissionGranted);
            proceed(currentUser);
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //Need to show permission rationale
            tvPermission.setText(R.string.permissionRationale);
        } else {
            //First time on app, request permission
            requestReadWritePermission.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }


    }

    private void proceed(User user) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 5 seconds
                Intent intent = new Intent(HomeActivity.this, WellPlayedActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        }, 1500);
    }

    private void sendMail(User user){
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.putExtra(Intent.EXTRA_EMAIL,user.email); //TODO: MAILI ALMIYOR
        mailIntent.putExtra(Intent.EXTRA_SUBJECT,String.format(getString(R.string.registerSuccess), user.id));
        mailIntent.putExtra(Intent.EXTRA_TEXT,String.format(getString(R.string.registerMailContent), user.id, user.username, user.phoneNumber));
        mailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(mailIntent, "Send email..."));
    }

}