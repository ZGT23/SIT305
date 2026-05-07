package com.example.llmchatbot;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * ChatDao provides the methods that the rest of the app uses to interact with data in the chat_messages table.
 */
@Dao
public interface ChatDao {
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    LiveData<List<ChatMessage>> getAllMessages();

    @Insert
    void insert(ChatMessage message);

    @Query("DELETE FROM chat_messages")
    void deleteAll();
}
