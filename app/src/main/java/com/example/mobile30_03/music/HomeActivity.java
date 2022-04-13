package com.example.mobile30_03.music;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

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
import com.example.mobile30_03.utils.SongsAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity
        implements SongsAdapter.ListItemClickListener{

    private List<Music> listSongs;
    private SongsAdapter mAdapter;
    private RecyclerView mNumbersList;
    private Toast mToast;

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = listSongs.get(clickedItemIndex).getSong_name() + " clicked.";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
        mToast.show();

        Log.i("onListItemClick",listSongs.get(clickedItemIndex).getUri().toString());
        musicClicked(listSongs.get(clickedItemIndex));
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Log.i("requestPermissionLauncher isGranted", "audioMediaOperations");
                    audioMediaOperations();
                } else {
                    Log.i("requestPermissionLauncher isGranted NOT", "explain todo");
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
        RecyclerView rv_songlist = findViewById(R.id.rv_songlist);

        //Read audio files from external storage
        //Permissions
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            Log.i("if ContextCompat.checkSelfPermission", "audioMediaOperations");
            listSongs = audioMediaOperations();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Log.i("else if shouldShowRequestPermissionRationale", "TODO");
            //TODO
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
        } else {
            Log.i("else", "requestPermissionLauncher");
            requestPermissionLauncher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }


        mNumbersList = (RecyclerView) findViewById(R.id.rv_songlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);
        mNumbersList.setHasFixedSize(true);
        mAdapter = new SongsAdapter( this,listSongs);
        mNumbersList.setAdapter(mAdapter);
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
//            int albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);

            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                String song_name = cursor.getString(songColumn);
                String artist_name = cursor.getString(artistColumn);
                String display_name = cursor.getString(displayColumn);
//                long album_id = cursor.getLong(albumIdColumn);
                int duration = cursor.getInt(durationColumn);
                Uri contentUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                String album_art = getCoverArtPath(this, id);
                if (album_art != null){
                    Log.i("Album art", album_art);
                }else{
                    Log.i("Album art", "NULL");
                }

                songs.add(new Music(contentUri, song_name,artist_name,display_name, duration));
            }
        }
        return songs;
    }

    private static String getCoverArtPath(Context context, long androidAlbumId) {
        String path = null;
        Cursor c = context.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + "=?",
                new String[]{Long.toString(androidAlbumId)},
                null);
        if (c != null) {
            if (c.moveToFirst()) {
                path = c.getString(0);
            }
            c.close();
        }
        return path;
    }

    private void musicClicked(Music song) {
        Log.d("btn_music", "Music clicked!");
        Intent intent = new Intent(this, MusicActivity.class);
        intent.putExtra("sng", song.getSong_name());
        intent.putExtra("dur", song.getDuration());
        intent.putExtra("uri", song.getUri().toString());
        intent.putExtra("art", song.getArtist_name());
        intent.putExtra("dsp", song.getDisplay_name());
//        intent.putExtra("alb", song.getAlbum_art());
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