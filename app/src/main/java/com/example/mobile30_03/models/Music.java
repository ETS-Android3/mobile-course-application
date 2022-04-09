package com.example.mobile30_03.models;

import android.net.Uri;

public class Music{
    private final Uri uri;
    public final String song_name;
    public final String artist_name;
    public final String display_name;
    public final int duration;

    public Music(Uri uri, String song_name,String artist_name, String display_name, int duration) {
        this.uri = uri;
        this.song_name = song_name;
        this.artist_name = artist_name;
        this.display_name = display_name;
        this.duration = duration;
    }
}