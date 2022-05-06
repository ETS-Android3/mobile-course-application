package com.example.mobile30_03.utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile30_03.database.RPlaylist;
import com.example.mobile30_03.databinding.CPlaylistItemBinding;

import java.util.List;

public class PlaylistsAdapter extends RecyclerView.Adapter<PlaylistsAdapter.PlaylistViewHolder> {
    final List<RPlaylist> playlists;

    public PlaylistsAdapter() {
        this.playlists = MediaPlayerManager.getInstance().getAllRPlaylists();
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
        RPlaylist playlist = playlists.get(position);
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

        public void bind(RPlaylist playlist) {
            binding.ivPlaylist.setImageResource(Integer.parseInt(playlist.artworkUrl));
            binding.tvPlaylistName.setText(playlist.name);
            binding.tvPlaylistDescription.setText(playlist.description);
            binding.tvPlaylistCount.setText(playlist.playlistId);
        }
    }
}
