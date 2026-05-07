package com.example.llmchatbot;

import android.os.Handler;
import android.os.Looper;

/**
 * MockLlmService provides hardcoded responses for testing without an API key.
 */
public class MockLlmService implements LlmService {

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void getResponse(String userMessage, LlmResponseCallback callback) {
        // Simulate network delay
        handler.postDelayed(() -> {
            String response = "This is a mock response to: \"" + userMessage + "\". " +
                    "To get real AI responses, please configure your API key in ApiLlmService.";
            callback.onResponse(response);
        }, 1500);
    }
}
