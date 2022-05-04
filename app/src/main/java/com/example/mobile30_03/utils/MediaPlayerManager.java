package com.example.mobile30_03.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.mobile30_03.R;
import com.example.mobile30_03.models.Music;
import com.example.mobile30_03.models.Playlist;

import java.util.List;

public class MediaPlayerManager {
    private static MediaPlayerManager instance;
    private MediaPlayer mediaPlayer;
    private boolean isShuffle;
    private boolean isLooping;
    private int currentSongIndex;
    private List<Music> allSongs;
    private Playlist currentPlaylist;

    private MediaPlayerManager() {
        mediaPlayer = new MediaPlayer();
        isShuffle = false;
        currentSongIndex = 0;
    }

    public static MediaPlayerManager getInstance() {
        if (instance == null) {
            instance = new MediaPlayerManager();
        }
        return instance;
    }

    public void play() {
        if (!mediaPlayer.isPlaying()) mediaPlayer.start();
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) mediaPlayer.pause();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(Context context, int index) {
        try {
            mediaPlayer.release();
        }catch (Exception e) {
            e.printStackTrace();
        }
        this.currentSongIndex = index;
        this.mediaPlayer = MediaPlayer.create(context, currentPlaylist.getSongs().get(index).getUri());
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    public void setShuffle(boolean shuffle) {
        isShuffle = shuffle;
    }

    public boolean isLooping() {
        return isLooping;
    }

    public void setLooping(boolean looping) {
        isLooping = looping;
    }

    public int getCurrentSongIndex() {
        return currentSongIndex;
    }

    public List<Music> getAllSongs() {
        return allSongs;
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public boolean isPause() {
        return isShuffle;
    }

    public void fetchAllSongs(Context context) {
        allSongs = HelperFunctions.audioMediaOperations(context);
        currentPlaylist = new Playlist("All Songs", "All songs on device.", R.drawable.allsong_clip,allSongs);
    }

    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    public void setCurrentPlaylist(Playlist currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }
}