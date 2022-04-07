package com.example.mobile30_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private static final String LOG_TAG =
            HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", 1);
        String logType = intent.getStringExtra("type");
        TextView welcomeText = findViewById(R.id.tv_welcome);
        Button btn_music = findViewById(R.id.btn_music);
        welcomeText.setText(String.format("ID: %d - Başarılı %s", userId, logType));

        User currentUser = AppUsers.getUserById(userId);
        if (logType.equals("Kayıt")) sendMail(currentUser);


        btn_music.setOnClickListener(view -> musicClicked());

    }

    private void musicClicked() {
        Log.d(LOG_TAG, "Music clicked!");
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }

    private void sendMail(User user){
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.putExtra(Intent.EXTRA_EMAIL,user.email); //TODO: MAILI ALMIYOR
        mailIntent.putExtra(Intent.EXTRA_SUBJECT,String.format("ID: %d - Başarılı Kayıt", user.id));
        mailIntent.putExtra(Intent.EXTRA_TEXT,String.format("ID: %d - Username: %s \nPhone: %s", user.id, user.username, user.phoneNumber));
        mailIntent.setType("message/rfc822");
        startActivity(mailIntent);
    }
}