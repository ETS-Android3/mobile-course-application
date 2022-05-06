package com.example.mobile30_03.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RPlaylist  {
    @PrimaryKey(autoGenerate = true)
    public int playlistId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "artwork_url")
    public String artworkUrl;

    @ColumnInfo(name = "username")
    public String username;
}
