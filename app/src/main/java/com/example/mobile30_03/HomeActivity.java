package com.example.mobile30_03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        TextView tv_welcome = findViewById(R.id.tv_welcome);
        TextView tv_songlist = findViewById(R.id.tv_songlist);
        Button btn_music = findViewById(R.id.btn_music);
        Button btn_file = findViewById(R.id.btn_file);

        tv_welcome.setText(String.format("ID: %d - Başarılı %s", userId, logType));
        User currentUser = AppUsers.getUserById(userId);

        if (logType.equals("Kayıt")) sendMail(currentUser);

        btn_music.setOnClickListener(view -> musicClicked());


        btn_file.setOnClickListener(view -> {
            List<Music> songs = new ArrayList<Music>();
            Uri collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

            String[] projection = {
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.DURATION
            };

            String selection = MediaStore.Audio.Media.DURATION +
                    " >= ?";
            String[] selectionArgs = new String[] {
                    String.valueOf(TimeUnit.MILLISECONDS.convert(0, TimeUnit.MINUTES))
            };

            String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

            try (Cursor cursor = getApplicationContext().getContentResolver().query(
                    collection,
                    projection,
                    null,
                    null,
                    sortOrder
            )) {
                // Cache column indices.
                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
                int displayColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
                int songColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
                int artistColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
                int durationColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);

                while (cursor.moveToNext()) {
                    Log.i("Test", "next");
                    long id = cursor.getLong(idColumn);
                    String song_name = cursor.getString(songColumn);
                    String artist_name = cursor.getString(artistColumn);
                    String display_name = cursor.getString(displayColumn);
                    int duration = cursor.getInt(durationColumn);

                    Uri contentUri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                    songs.add(new Music(contentUri, song_name,artist_name,display_name, duration));
                }
            }
            Log.i("Songs Size", String.valueOf(songs.size()));
            for (Music song : songs) {
                tv_songlist.append(String.format("%s \n",song.song_name));
            }

        });



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

class Music{
    private final Uri uri;
    final String song_name;
    private final String artist_name;
    final String display_name;
    private final int duration;

    public Music(Uri uri, String song_name,String artist_name, String display_name, int duration) {
        this.uri = uri;
        this.song_name = song_name;
        this.artist_name = artist_name;
        this.display_name = display_name;
        this.duration = duration;
    }
}