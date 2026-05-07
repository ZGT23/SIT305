package com.example.llmchatbot;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * ApiLlmService 使用 OpenAI API 进行通信。
 * 密钥通过 BuildConfig 安全获取，不会出现在代码库中。
 */
public class ApiLlmService implements LlmService {

    private static final String BASE_URL = "https://api.openai.com/";
    private final OpenAiApi api;

    public ApiLlmService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(OpenAiApi.class);
    }

    @Override
    public void getResponse(String userMessage, LlmResponseCallback callback) {
        // 从 BuildConfig 获取密钥（由 Gradle 注入）
        String apiKey = "Bearer " + BuildConfig.OPENAI_API_KEY;

        OpenAiRequest request = new OpenAiRequest("gpt-3.5-turbo", userMessage);

        api.getChatCompletion(apiKey, request).enqueue(new Callback<OpenAiResponse>() {
            @Override
            public void onResponse(@NonNull Call<OpenAiResponse> call, @NonNull Response<OpenAiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String botText = response.body().choices.get(0).message.content;
                    callback.onResponse(botText.trim());
                } else {
                    callback.onError("API 错误: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<OpenAiResponse> call, @NonNull Throwable t) {
                callback.onError("网络错误: " + t.getMessage());
            }
        });
    }

    // --- OpenAI Retrofit 接口 ---
    interface OpenAiApi {
        @POST("v1/chat/completions")
        Call<OpenAiResponse> getChatCompletion(
                @Header("Authorization") String auth,
                @Body OpenAiRequest request
        );
    }

    // --- 数据模型 ---
    static class OpenAiRequest {
        String model;
        List<Message> messages = new ArrayList<>();

        OpenAiRequest(String model, String content) {
            this.model = model;
            this.messages.add(new Message("user", content));
        }

        static class Message {
            String role;
            String content;
            Message(String role, String content) {
                this.role = role;
                this.content = content;
            }
        }
    }

    static class OpenAiResponse {
        List<Choice> choices;
        static class Choice {
            Message message;
        }
        static class Message {
            String content;
        }
    }
}
