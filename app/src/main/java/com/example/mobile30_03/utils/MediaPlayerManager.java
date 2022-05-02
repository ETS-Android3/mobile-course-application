package com.example.mobile30_03.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.mobile30_03.R;
import com.example.mobile30_03.models.Music;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MediaPlayerManager {
    private static MediaPlayerManager instance;
    private MediaPlayer mediaPlayer;
    private boolean isShuffle;
    private boolean isLooping;
    private int currentSongIndex;
    private List<Music> musicList;
    private List<Music> currentPlaylist;

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
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
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
        this.mediaPlayer = MediaPlayer.create(context, musicList.get(index).getUri());
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

    public List<Music> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public boolean isPause() {
        return isShuffle;
    }

    public void updateMusicList(Context context) {
        musicList = HelperFunctions.audioMediaOperations(context);
    }
}