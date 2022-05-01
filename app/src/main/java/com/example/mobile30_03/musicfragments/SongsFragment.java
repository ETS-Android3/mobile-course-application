package com.example.mobile30_03.musicfragments;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile30_03.R;
import com.example.mobile30_03.databinding.FragmentSongsBinding;
import com.example.mobile30_03.models.Music;
import com.example.mobile30_03.utils.MediaPlayerManager;
import com.example.mobile30_03.utils.SongsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SongsFragment extends Fragment {
    private FragmentSongsBinding binding;

    private static final String TAG = "SongsFragment";

    private List<Music> listSongs;
    private SongsAdapter mAdapter;
    private RecyclerView mNumbersList;
    public SongsFragment() {}

    //Create MediaPlayerManager
    private MediaPlayerManager mediaPlayerManager = MediaPlayerManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentSongsBinding.inflate(inflater,container,false);

        RecyclerView rvSongs = binding.rvSongs;
        listSongs = audioMediaOperations();
        mediaPlayerManager.setMusicList(listSongs);
        mNumbersList = rvSongs;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mNumbersList.setLayoutManager(layoutManager);
        mNumbersList.setHasFixedSize(true);
        mAdapter = new SongsAdapter( listSongs);
        mNumbersList.setAdapter(mAdapter);

        return binding.getRoot();
    }

    private List<Music> audioMediaOperations(){
        List<Music> songs = new ArrayList<>();
        List<Integer> drawables = new ArrayList<>();
        drawables.add(R.drawable.image_one);
        drawables.add(R.drawable.image_two);
        drawables.add(R.drawable.image_three);
        drawables.add(R.drawable.image_four);
        drawables.add(R.drawable.image_five);
        drawables.add(R.drawable.image_six);
        drawables.add(R.drawable.image_seven);
        drawables.add(R.drawable.image_eight);

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
        try (Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(
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
                long id = cursor.getLong(idColumn);
                String song_name = cursor.getString(songColumn);
                String artist_name = cursor.getString(artistColumn);
                String display_name = cursor.getString(displayColumn);
                int duration = cursor.getInt(durationColumn);
                Uri contentUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                int art = drawables.get(1);
                songs.add(new Music(contentUri, song_name,artist_name,display_name, art, duration));
            }
        }
        return songs;
    }
}