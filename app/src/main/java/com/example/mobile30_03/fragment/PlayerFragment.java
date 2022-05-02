package com.example.mobile30_03.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile30_03.models.Music;
import com.example.mobile30_03.utils.HelperFunctions;
import com.example.mobile30_03.utils.MediaPlayerManager;

public class PlayerFragment extends Fragment {
    public PlayerFragment() {}
    private static final String TAG = "SongsFragment";
    private FragmentPlayerBinding binding;

    //Create MediaPlayerManager
    private MediaPlayerManager mediaPlayerManager = MediaPlayerManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlayerBinding.inflate(inflater, container, false);
        Music currentSong = mediaPlayerManager.getMusicList()
                .get(mediaPlayerManager.getCurrentSongIndex());

        binding.tvArtistName.setText(currentSong.getArtist_name());
        binding.tvDurationTotal.setText(HelperFunctions.milliSecondsToTimer(currentSong.getDuration()));
        binding.tvSongName.setText(currentSong.getSong_name());

        return binding.getRoot();
    }
}