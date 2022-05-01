package com.example.mobile30_03.musicfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile30_03.R;
import com.example.mobile30_03.databinding.FragmentPlaylistBinding;

public class PlaylistFragment extends Fragment {
    private FragmentPlaylistBinding binding;

    public PlaylistFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}