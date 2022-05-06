package com.example.mobile30_03.models;

import com.example.mobile30_03.database.RSong;

public class Playlist {
    private String name;
    private String description;
    private int artworkUrl;
    private RSong[] songs;

    public Playlist(String name, String description, int artworkUrl, RSong[] songs) {
        this.name = name;
        this.description = description;
        this.artworkUrl = artworkUrl;
        this.songs = songs;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getArtworkUrl() {
        return artworkUrl;
    }

    public RSong[] getSongs() {
        return songs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArtworkUrl(int artworkUrl) {
        this.artworkUrl = artworkUrl;
    }

    public void setSongs(RSong[] songs) {
        this.songs = songs;
    }

}
