package com.example.mobile30_03.utils;

import android.content.Context;
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

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MusicViewHolder> {
    private static final String TAG = SongsAdapter.class.getSimpleName();
    private List<Music> songs;

    final private ListItemClickListener mOnClickListener;
    private int mNumberItems;


    public SongsAdapter(ListItemClickListener listener, List<Music> rvSongs) {
        mNumberItems = rvSongs.size();
        mOnClickListener = listener;
        songs = rvSongs;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.song_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        MusicViewHolder viewHolder = new MusicViewHolder(view);
        return viewHolder;
    }

    private String milliSecondsToTimer(long milliSeconds){
        String timerString="";
        String secondString;
        int hour=(int) (milliSeconds/(1000*60*60));
        int minutes=(int)(milliSeconds%(1000*60*60))/(1000*60);
        int seconds=(int)((milliSeconds%(1000*60*60))%(1000*60)/1000);
        if(hour>0){
            timerString=hour+":";
        }
        if(seconds<10){
            secondString="0"+seconds;
        }
        else {
            secondString=""+seconds;
        }
        timerString=timerString+minutes+":"+secondString;
        return timerString;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        holder.tv_song_name.setText(songs.get(position).getSong_name());
        holder.tv_song_duration.setText(milliSecondsToTimer(songs.get(position).getDuration()));
        holder.tv_song_artist.setText(songs.get(position).getArtist_name());
        holder.iv_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        
        holder.bind();
//        Log.d(TAG, "#" + position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public interface FavoriteItemClickListener{
        void onFavoriteItemClick(int clickedItemIndex);
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
            tv_song_duration = (TextView) itemView.findViewById(R.id.tv_song_duration);
            tv_song_artist = (TextView) itemView.findViewById(R.id.tv_song_artist);
            iv_album_cover = (ImageView) itemView.findViewById(R.id.iv_album_cover);
            iv_favorite = (ImageView) itemView.findViewById(R.id.iv_favorite);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }

        public void bind() {
        }
    }
}

