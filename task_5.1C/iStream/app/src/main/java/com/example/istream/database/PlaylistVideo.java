package com.example.istream.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "playlist")
public class PlaylistVideo {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int userId;
    public String videoUrl;

    public PlaylistVideo(int userId, String videoUrl) {
        this.userId = userId;
        this.videoUrl = videoUrl;
    }
}
