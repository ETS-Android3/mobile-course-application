package com.example.mobile30_03.utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile30_03.models.Playlist;
import com.example.mobile30_03.databinding.CPlaylistItemBinding;

import java.util.List;

public class PlaylistsAdapter extends RecyclerView.Adapter<PlaylistsAdapter.PlaylistViewHolder> {
    final List<Playlist> playlists;

    public PlaylistsAdapter(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CPlaylistItemBinding binding = CPlaylistItemBinding.inflate(inflater, parent, false);
        return new PlaylistViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        holder.bind(playlist);
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }



    class PlaylistViewHolder extends RecyclerView.ViewHolder {
        CPlaylistItemBinding binding;
        public PlaylistViewHolder(@NonNull CPlaylistItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Playlist playlist) {
            binding.ivPlaylist.setImageResource(playlist.getArtworkUrl());
            binding.tvPlaylistName.setText(playlist.getName());
            binding.tvPlaylistDescription.setText(playlist.getDescription());
            binding.tvPlaylistCount.setText(String.valueOf(playlist.getSongCount()));
        }
    }



}
