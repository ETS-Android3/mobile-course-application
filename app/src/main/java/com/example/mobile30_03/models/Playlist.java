package com.example.mobile30_03.models;

import java.util.List;

public class Playlist {
    private String name;
    private String description;
    private int artworkUrl;
    private List<Music> songs;

    public Playlist(String name, String description, int artworkUrl, List<Music> songs) {
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

    public List<Music> getSongs() {
        return songs;
    }

    public int getSongCount() {
        return songs.size();
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

    public void setSongs(List<Music> songs) {
        this.songs = songs;
    }
}
