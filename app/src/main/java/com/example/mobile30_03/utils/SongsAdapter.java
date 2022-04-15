package com.example.mobile30_03.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile30_03.R;
import com.example.mobile30_03.models.Music;
import com.example.mobile30_03.music.MusicActivity;
import com.example.mobile30_03.music.PlayerActivity;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MusicViewHolder> {
    private static final String TAG = SongsAdapter.class.getSimpleName();
    private List<Music> songs;
    private int mNumberItems;


    public SongsAdapter( List<Music> rvSongs) {
        mNumberItems = rvSongs.size();
        songs = rvSongs;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.c_song_item, parent, false);
        MusicViewHolder viewHolder = new MusicViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        holder.tv_song_name.setText(songs.get(position).getSong_name());
        holder.tv_song_duration.setText(HelperFunctions.milliSecondsToTimer(songs.get(position).getDuration()));
        holder.tv_song_artist.setText(songs.get(position).getArtist_name());
    }


    @Override
    public int getItemCount() {
        return mNumberItems;
    }


    class MusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_song_name;
        TextView tv_song_duration;
        TextView tv_song_artist;
        ImageView iv_album_cover;
        ImageView iv_favorite;

        public MusicViewHolder(View itemView) {
            super(itemView);
            tv_song_name = (TextView) itemView.findViewById(R.id.tv_song_name);
            tv_song_duration = (TextView) itemView.findViewById(R.id.tv_duration);
            tv_song_artist = (TextView) itemView.findViewById(R.id.tv_artist_name);
            iv_album_cover = (ImageView) itemView.findViewById(R.id.iv_album_art);
            iv_favorite = (ImageView) itemView.findViewById(R.id.iv_favorite);
            itemView.setOnClickListener(this);
            iv_favorite.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            if (view.getId() == iv_favorite.getId()){
                Log.d(TAG, "onClick: favorite clicked");
            }else{
                Intent intent = new Intent(view.getContext(), PlayerActivity.class);
                Music song = songs.get(clickedPosition);
                intent.putExtra("music", song);
                intent.putExtra("position", clickedPosition);
                view.getContext().startActivity(intent);
            }
        }
    }
}

