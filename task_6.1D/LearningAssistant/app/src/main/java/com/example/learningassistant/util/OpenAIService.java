package com.example.learningassistant.util;

import com.example.learningassistant.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OpenAIService {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = BuildConfig.OPENAI_API_KEY;
    private static final MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");

    private static OpenAIService instance;
    private final OkHttpClient client;

    private OpenAIService() {
        client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();
    }

    public static OpenAIService getInstance() {
        if (instance == null) {
            instance = new OpenAIService();
        }
        return instance;
    }

    public interface Callback {
        void onSuccess(String response);
        void onFailure(String error);
    }

    public void sendPrompt(String prompt, Callback callback) {
        try {
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt);

            JSONArray messages = new JSONArray();
            messages.put(message);

            JSONObject body = new JSONObject();
            body.put("model", "gpt-4o-mini");
            body.put("messages", messages);
            body.put("max_tokens", 500);
            body.put("temperature", 0.7);

            RequestBody requestBody = RequestBody.create(body.toString(), JSON_TYPE);
            Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build();

            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Network error: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String bodyStr = response.body() != null ? response.body().string() : "";
                    if (!response.isSuccessful()) {
                        callback.onFailure("API error " + response.code() + ": " + bodyStr);
                        return;
                    }
                    try {
                        JSONObject json = new JSONObject(bodyStr);
                        String content = json.getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content");
                        callback.onSuccess(content.trim());
                    } catch (JSONException e) {
                        callback.onFailure("Parse error: " + e.getMessage());
                    }
                }
            });
        } catch (JSONException e) {
            callback.onFailure("Request error: " + e.getMessage());
        }
    }
}
