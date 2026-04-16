package com.example.istream.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlaylistDao {
    @Insert
    void insertVideo(PlaylistVideo video);

    @Query("SELECT * FROM playlist WHERE userId = :userId")
    List<PlaylistVideo> getUserPlaylist(int userId);

    @Delete
    void deleteVideo(PlaylistVideo video);
}
