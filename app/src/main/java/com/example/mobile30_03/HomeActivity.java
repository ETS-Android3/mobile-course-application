package com.example.mobile30_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private static final String LOG_TAG =
            HomeActivity.class.getSimpleName();
    private static final String email = "furkanaxcakaya@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", 1);
        String logType = intent.getStringExtra("type");
        TextView welcomeText = findViewById(R.id.tv_welcome);
        Button btn_mail = findViewById(R.id.btn_mail);
        Button btn_music = findViewById(R.id.btn_music);
        welcomeText.setText(String.format("ID: %d - Başarılı %s", userId, logType));

        btn_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User currentUser = AppUsers.getUserById(userId);
                sendMailToOwner(currentUser, logType);
            }
        });
        btn_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicClicked();
            }
        });

    }

    private void musicClicked() {
        Log.d(LOG_TAG, "Music clicked!");
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }

    private void sendMailToOwner(User user, String logType){
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.putExtra(Intent.EXTRA_EMAIL,user.email); //TODO: MAILI ALMIYOR
        mailIntent.putExtra(Intent.EXTRA_SUBJECT,String.format("ID: %d - Başarılı %s", user.id, logType));
        mailIntent.putExtra(Intent.EXTRA_TEXT,String.format("ID: %d - Username: %s \nPhone: %s", user.id, user.username, user.phoneNumber));
        mailIntent.setType("message/rfc822");
        startActivity(mailIntent);
    }
}