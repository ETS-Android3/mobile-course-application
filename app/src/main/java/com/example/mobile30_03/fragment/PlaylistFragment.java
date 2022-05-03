package com.example.mobile30_03.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mobile30_03.R;
import com.example.mobile30_03.databinding.FragmentPlaylistBinding;
import com.example.mobile30_03.models.Music;
import com.example.mobile30_03.models.Playlist;
import com.example.mobile30_03.utils.MediaPlayerManager;
import com.example.mobile30_03.utils.PlaylistsAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlaylistFragment extends Fragment {
    private String TAG = "PlaylistFragment";
    private FragmentPlaylistBinding binding;
    public PlaylistFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false);
        List<Playlist> playlists = new ArrayList<>();
        List<Music> songs = MediaPlayerManager.getInstance().getAllSongs();
        playlists.add(new Playlist("Playlist 1", "This is playlist 1", R.drawable.playlist_clip, songs));
        playlists.add(new Playlist("Playlist 2", "This is playlist 2", R.drawable.allsong_clip, songs));
        playlists.add(new Playlist("Playlist 3", "This is playlist 3", R.drawable.player_clip, songs));
        playlists.add(new Playlist("Playlist 4", "This is playlist 4", R.drawable.ic_baseline_shuffle,songs));

        binding.rvPlaylists.setAdapter(new PlaylistsAdapter(playlists));
        binding.rvPlaylists.setHasFixedSize(true);
        binding.rvPlaylists.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));

        return binding.getRoot();
    }
}