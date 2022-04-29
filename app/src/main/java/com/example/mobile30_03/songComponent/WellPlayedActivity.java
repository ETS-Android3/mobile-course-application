package com.example.mobile30_03.songComponent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mobile30_03.R;
import com.example.mobile30_03.databinding.ActivityWellPlayedBinding;

public class WellPlayedActivity extends AppCompatActivity {
    private static final String TAG = "WellPlayedActivity";
    private ActivityWellPlayedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWellPlayedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}