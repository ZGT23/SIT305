package com.example.llmchatbot;

/**
 * LlmService defines the interface for communicating with an LLM.
 */
public interface LlmService {
    /**
     * Callback interface to return the LLM response.
     */
    interface LlmResponseCallback {
        void onResponse(String response);
        void onError(String error);
    }

    /**
     * Sends a message to the LLM and gets a response.
     */
    void getResponse(String userMessage, LlmResponseCallback callback);
}
