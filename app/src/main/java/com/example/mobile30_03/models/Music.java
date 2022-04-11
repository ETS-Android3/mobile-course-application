package com.example.mobile30_03.models;

import android.net.Uri;

import java.io.Serializable;

public class Music implements Serializable {
    private final Uri uri;
    private final String song_name;
    private final String artist_name;
    private final String display_name;
    private final int duration;

    public Music(Uri uri, String song_name,String artist_name, String display_name, int duration) {
        this.uri = uri;
        this.song_name = song_name;
        this.artist_name = artist_name;
        this.display_name = display_name;
        this.duration = duration;
    }

    public Uri getUri() {
        return uri;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public String getSong_name() {
        return song_name;
    }

    public int getDuration() {
        return duration;
    }

    public String getDisplay_name() {
        return display_name;
    }
}