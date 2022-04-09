package com.example.mobile30_03.music;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.mobile30_03.R;
import com.example.mobile30_03.models.AppUsers;
import com.example.mobile30_03.models.Music;
import com.example.mobile30_03.models.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    audioMediaOperations();
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Intent operations
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", 1);
        String logType = intent.getStringExtra("type");
        User currentUser = AppUsers.getUserById(userId);
        //Send welcome mail if log type is Register
        if (logType.equals("Kayıt")) sendMail(currentUser);

        //XML
//        TextView tv_songlist = findViewById(R.id.tv_songlist);
//        Button btn_music = findViewById(R.id.btn_music);
//        Button btn_file = findViewById(R.id.btn_file);
        GridView gv_songs = findViewById(R.id.gv_songs);

        //Read audio files from external storage
        //Permissions
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            for (Music audioMediaOperation : audioMediaOperations()) {
//                tv_songlist.append(String.format("%s \n",audioMediaOperation.song_name));
            }
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //TODO
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
        } else {
            requestPermissionLauncher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }

//        btn_music.setOnClickListener(view -> musicClicked());
//
//        btn_file.setOnClickListener(view -> {
//            Log.d("btn_file", "File clicked!");
//        });

    }

    private List<Music> audioMediaOperations(){
        List<Music> songs = new ArrayList<>();
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
                selection,
                selectionArgs,
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
        return songs;
    }

    private void musicClicked() {
        Log.d("btn_music", "Music clicked!");
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }

    @SuppressLint("DefaultLocale")
    private void sendMail(User user){
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.putExtra(Intent.EXTRA_EMAIL,user.email); //TODO: MAILI ALMIYOR
        mailIntent.putExtra(Intent.EXTRA_SUBJECT,String.format("ID: %d - Başarılı Kayıt", user.id));
        mailIntent.putExtra(Intent.EXTRA_TEXT,String.format("ID: %d - Username: %s \nPhone: %s", user.id, user.username, user.phoneNumber));
        mailIntent.setType("message/rfc822");
        startActivity(mailIntent);
    }
}
//    READING MP3 FILES, AS FILE
//    private void audioFiles(){
//        Log.i("audioFiles","start");
//        String[] dirs = {
//                Environment.DIRECTORY_MUSIC,
//                Environment.DIRECTORY_DOWNLOADS,
//                Environment.DIRECTORY_PODCASTS,
//        };
//        for (String dirString : dirs) {
//            Log.i("audioFiles","dirs for");
//            File dir = Environment.getExternalStoragePublicDirectory(dirString);
//            File[] files = dir.listFiles();
//            for (File file : files) {
//                Log.i("audioFiles","file");
//                if (file.getName().endsWith("mp3")) {
//                    Log.i("Match",file.getName());
//                }
//            }
//        }
//    }