package com.example.mobile30_03.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Song implements Parcelable {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "uri")
    public String uri;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "artist")
    public String artist;

    @ColumnInfo(name = "duration")
    public int duration;

    protected Song(Parcel in) {
        uid = in.readInt();
        uri = in.readString();
        name = in.readString();
        artist = in.readString();
        duration = in.readInt();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(uid);
        parcel.writeString(uri);
        parcel.writeString(name);
        parcel.writeString(artist);
        parcel.writeInt(duration);
    }
}
