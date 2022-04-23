package com.example.mobile30_03.models;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Music implements Parcelable{
    private final Uri uri;
    private final String song_name;
    private final String artist_name;
    private final String display_name;
    private final int art;
    private final int duration;

    public Music(Uri uri, String song_name, String artist_name, String display_name, int art, int duration) {
        this.uri = uri;
        this.song_name = song_name;
        this.artist_name = artist_name;
        this.display_name = display_name;
        this.art = art;
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

    public int getArt() {
        return art;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uri.toString());
        dest.writeString(this.song_name);
        dest.writeString(this.artist_name);
        dest.writeString(this.display_name);
        dest.writeInt(this.art);
        dest.writeInt(this.duration);
    }

    private Music(Parcel in) {
        this.uri = Uri.parse(in.readString());
        this.song_name = in.readString();
        this.artist_name = in.readString();
        this.display_name = in.readString();
        this.art = in.readInt();
        this.duration = in.readInt();
    }

    public static final Parcelable.Creator<Music> CREATOR = new Parcelable.Creator<Music>() {
        public Music createFromParcel(Parcel source) {
            return new Music(source);
        }

        public Music[] newArray(int size) {
            return new Music[size];
        }
    };
}