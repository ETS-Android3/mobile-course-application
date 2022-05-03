package com.example.mobile30_03.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.mobile30_03.models.Music;

import java.util.List;

public class MediaPlayerManager {
    private static MediaPlayerManager instance;
    private MediaPlayer mediaPlayer;
    private boolean isShuffle;
    private boolean isLooping;
    private int currentSongIndex;
    private List<Music> allSongs;
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
        this.mediaPlayer = MediaPlayer.create(context, allSongs.get(index).getUri());
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

    public void setAllSongs(List<Music> allSongs) {
        this.allSongs = allSongs;
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public boolean isPause() {
        return isShuffle;
    }

    public void updateMusicList(Context context) {
        allSongs = HelperFunctions.audioMediaOperations(context);
    }

}