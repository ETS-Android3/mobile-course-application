package com.example.mobile30_03.musicfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile30_03.R;
import com.example.mobile30_03.databinding.FragmentPlayerBinding;

public class PlayerFragment extends Fragment {
    private FragmentPlayerBinding binding;

    public PlayerFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlayerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}