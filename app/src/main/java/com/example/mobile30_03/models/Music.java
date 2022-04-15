package com.example.mobile30_03.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Music implements Parcelable{
    private final Uri uri;
    private final String song_name;
    private final String artist_name;
    private final String display_name;
    private final int duration;

    public Music(Uri uri, String song_name,String artist_name, String display_name,  int duration) {
        this.uri = uri;
        this.song_name = song_name;
        this.artist_name = artist_name;
        this.display_name = display_name;
        this.duration = duration;
    }

    protected Music(Parcel in) {
        uri = in.readParcelable(Uri.class.getClassLoader());
        song_name = in.readString();
        artist_name = in.readString();
        display_name = in.readString();
        duration = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(uri, flags);
        dest.writeString(song_name);
        dest.writeString(artist_name);
        dest.writeString(display_name);
        dest.writeInt(duration);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

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