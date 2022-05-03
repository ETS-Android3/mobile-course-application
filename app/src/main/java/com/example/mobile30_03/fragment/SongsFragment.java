package com.example.mobile30_03.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile30_03.databinding.FragmentSongsBinding;
import com.example.mobile30_03.models.Music;
import com.example.mobile30_03.utils.MediaPlayerManager;
import com.example.mobile30_03.utils.SongsAdapter;

import java.util.List;

public class SongsFragment extends Fragment {
    private FragmentSongsBinding binding;

    private static final String TAG = "SongsFragment";

    private List<Music> listSongs;
    private SongsAdapter mAdapter;
    private RecyclerView mSongList;
    public SongsFragment() {}

    //Create MediaPlayerManager
    private MediaPlayerManager mediaPlayerManager = MediaPlayerManager.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentSongsBinding.inflate(inflater,container,false);

        mediaPlayerManager.updateMusicList(getActivity());

        mSongList = binding.rvSongs;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mSongList.setLayoutManager(layoutManager);
        mSongList.setHasFixedSize(true);
        mAdapter = new SongsAdapter(mediaPlayerManager.getAllSongs());
        mSongList.setAdapter(mAdapter);

        return binding.getRoot();
    }


}