package com.example.mobile30_03.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.example.mobile30_03.database.AppDatabase;
import com.example.mobile30_03.database.RPlaylist;
import com.example.mobile30_03.database.RSong;
import com.example.mobile30_03.models.Playlist;

import java.util.List;

public class MediaPlayerManager {
    private static MediaPlayerManager instance;
    private MediaPlayer mediaPlayer;
    private boolean isShuffle;
    private boolean isLooping;
    private int currentSongIndex;
    private List<RSong> allRSongs;
    private Playlist currentPlaylist;
    private List<RPlaylist> allRPlaylists;

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
        this.mediaPlayer = MediaPlayer.create(context, Uri.parse(currentPlaylist.getSongs()[index].uri));
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

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public boolean isPause() {
        return isShuffle;
    }

    public void updateSonglist(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        RSong[] rsongs = HelperFunctions.audioMediaOperations(context);
        db.wpDao().insertSongs(rsongs);
    }

    public void fetchPlayer(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        allRSongs = db.wpDao().getAllSongs();
        allRPlaylists = db.wpDao().getAllPlaylists();
    }

    public void setCurrentPlaylist(Playlist playlist) {
        currentPlaylist = playlist;
    }

    public Playlist getCurrentPlaylist() {
        return currentPlaylist;
    }

    public List<RPlaylist> getAllRPlaylists() {
        return allRPlaylists;
    }

    public List<RSong> getAllRSongs() {
        return allRSongs;
    }
}