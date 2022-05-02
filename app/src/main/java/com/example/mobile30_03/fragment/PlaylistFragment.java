package com.example.mobile30_03.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class PlaylistFragment extends Fragment {
    private String TAG = "PlaylistFragment";
    private FragmentPlaylistBinding binding;
    public PlaylistFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}