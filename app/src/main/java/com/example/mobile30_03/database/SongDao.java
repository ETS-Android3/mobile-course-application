package com.example.mobile30_03.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SongDao {
    @Query("SELECT * FROM song")
    List<Song> getAll();

    @Query("SELECT * FROM song WHERE name IN (:songNames)")
    List<Song> loadAllByName(String[] songNames);

    @Query("SELECT * FROM song WHERE name LIKE :name AND "
            + "artist LIKE :artist LIMIT 1")
    Song findByName(String name, String artist);

    @Insert
    void insertAll(Song... songs);

    @Delete
    void delete(Song song);
}