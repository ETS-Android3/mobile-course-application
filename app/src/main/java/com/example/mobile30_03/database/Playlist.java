package com.example.mobile30_03.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Playlist implements Parcelable {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "artwork_url")
    private String artworkUrl;

    @ColumnInfo(name = "songs")
    private Song[] songs;

    protected Playlist(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        artworkUrl = in.readString();
        songs = in.createTypedArray(Song.CREATOR);
    }

    public static final Creator<Playlist> CREATOR = new Creator<Playlist>() {
        @Override
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        @Override
        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(artworkUrl);
        parcel.writeTypedArray(songs, i);
    }
}
