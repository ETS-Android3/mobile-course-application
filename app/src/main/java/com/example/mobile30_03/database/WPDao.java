package com.example.mobile30_03.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.mobile30_03.database.relations.PlaylistsWithSongs;
import com.example.mobile30_03.database.relations.SongPlaylistCrossRef;
import com.example.mobile30_03.database.relations.SongsWithPlaylists;

import java.util.List;

@Dao
public interface WPDao {
    @Query("SELECT * FROM RUser")
    List<RUser> getAllUsers();

    @Query("SELECT * FROM RSong")
    List<RSong> getAllSongs();

    @Query("SELECT * FROM RPlaylist")
    List<RPlaylist> getAllPlaylists();

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertUser(RUser user);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertSong(RSong song);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertSongs(RSong... songs);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertPlaylist(RPlaylist playlist);

    @Delete
    void delete(RUser user);

    @Delete
    void delete(RSong song);

    @Delete
    void delete(RPlaylist playlist);

    @Transaction
    @Query("SELECT * FROM RUser WHERE username = :username")
    List<RUser> getUserWithPlaylists(String username);

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertSongPlaylistCrossRef(SongPlaylistCrossRef crossRef) ;

    @Transaction
    @Query("SELECT * FROM RSong WHERE songId = :sid")
    List<SongsWithPlaylists> getPlaylistsOfSong(int sid);

    @Transaction
    @Query("SELECT * FROM RPlaylist WHERE playlistId = :pid")
    List<PlaylistsWithSongs> getSongsOfPlaylist(int pid);

}