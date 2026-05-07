package com.example.llmchatbot;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ChatRepository abstracts access to multiple data sources (Room database).
 * It handles background execution for database operations.
 */
public class ChatRepository {
    private final ChatDao chatDao;
    private final LiveData<List<ChatMessage>> allMessages;
    private final ExecutorService executorService;

    public ChatRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        chatDao = db.chatDao();
        allMessages = chatDao.getAllMessages();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<ChatMessage>> getAllMessages() {
        return allMessages;
    }

    public void insert(ChatMessage message) {
        executorService.execute(() -> chatDao.insert(message));
    }
}
