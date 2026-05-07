package com.example.llmchatbot;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * ChatMessage entity represents a single message in the chat history.
 * It is stored in the Room database.
 */
@Entity(tableName = "chat_messages")
public class ChatMessage {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String text;
    private boolean isUser; // true if message is from user, false if from bot
    private long timestamp;

    public ChatMessage(String text, boolean isUser, long timestamp) {
        this.text = text;
        this.isUser = isUser;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public boolean isUser() { return isUser; }
    public void setUser(boolean user) { isUser = user; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
