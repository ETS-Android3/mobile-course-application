package com.example.mobile30_03.utils;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile30_03.R;
import com.example.mobile30_03.fragment.SongsFragmentDirections;
import com.example.mobile30_03.models.Music;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MusicViewHolder> {
    private static final String TAG = "SongsAdapter";
    private final List<Music> songs;
    private final int mNumberItems;

    public SongsAdapter( List<Music> rvSongs) {
        mNumberItems = rvSongs.size();
        songs = rvSongs;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.c_song_item, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        Music music = songs.get(position);
        holder.ivAlbumArt.setImageBitmap(HelperFunctions.getBitmapFromContentURI(holder.itemView.getContext(), music.getUri()));
        holder.tvSongName.setText(songs.get(position).getSong_name());
        holder.tvDuration.setText(HelperFunctions.milliSecondsToTimer(songs.get(position).getDuration()));
        holder.tvArtistName.setText(songs.get(position).getArtist_name());

        holder.ivSongMore.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), holder.ivSongMore);
            popupMenu.getMenuInflater().inflate(R.menu.song_more_popup, popupMenu.getMenu());
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.action_add_to_playlist:
                        PopupWindow popupWindow = new PopupWindow(holder.itemView.getContext());
                        View view2 = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.add_to_playlist, null);
                        popupWindow.setContentView(view2);
                        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                        popupWindow.setFocusable(true);
                        popupWindow.showAsDropDown(holder.ivSongMore);
                        //TODO: add to playlist
                        break;
                    case R.id.action_share:
                        shareSong(holder.itemView.getContext(), music.getUri());
                        break;
                    case R.id.action_delete:
                        Snackbar.make(holder.itemView, "Are you sure you want to delete " + songs.get(position).getSong_name() +" ?", Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.RED)
                                .setAction("Yes, delete it", view1 -> {
                                    Toast.makeText(holder.itemView.getContext(), "Permission problems.. ", Toast.LENGTH_SHORT).show();
                                })
                                .show();
                        break;
                    default:
                        break;
                }
                return false;
            });
            popupMenu.show();
        });

        holder.itemView.setOnClickListener(view -> {
            NavDirections action = SongsFragmentDirections.songsToPlayer().setSongIndex(position);
            Navigation.findNavController(view).navigate(action);
        });

    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    //A function that sends an intent to share a song file
    public void shareSong(Context context, Uri uri) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("audio/*");
        context.startActivity(Intent.createChooser(shareIntent, "Share the song"));
    }

    class MusicViewHolder extends RecyclerView.ViewHolder {
        TextView tvSongName;
        TextView tvDuration;
        TextView tvArtistName;
        ImageView ivAlbumArt;
        ImageView ivSongMore;
        public MusicViewHolder(View itemView) {
            super(itemView);
            tvSongName = (TextView) itemView.findViewById(R.id.tvSongName);
            tvDuration = (TextView) itemView.findViewById(R.id.tvDuration);
            tvArtistName = (TextView) itemView.findViewById(R.id.tvArtistName);
            ivAlbumArt = (ImageView) itemView.findViewById(R.id.ivAlbumArt);
            ivSongMore = (ImageView) itemView.findViewById(R.id.ivSongMore);
        }
    }
}

