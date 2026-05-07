package com.example.llmchatbot;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * ChatFragment 处理聊天 UI 逻辑。
 * 它通过 LlmService 接口与“后端”服务进行通信。
 */
public class ChatFragment extends Fragment {

    private static final String ARG_USERNAME = "username";
    private String username;

    private RecyclerView rvChat;
    private ChatAdapter adapter;
    private EditText etMessage;
    private ImageButton btnSend;

    private ChatRepository repository;
    private LlmService llmService;

    public static ChatFragment newInstance(String username) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME);
        }
        repository = new ChatRepository(getActivity().getApplication());
        
        // --- 切换服务层 ---
        // 如果 local.properties 中配置了 Key，则使用 ApiLlmService。
        // 如果没有配置或 Key 为占位符，可以手动切回 MockLlmService。
        llmService = new ApiLlmService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        rvChat = view.findViewById(R.id.rv_chat);
        etMessage = view.findViewById(R.id.et_message);
        btnSend = view.findViewById(R.id.btn_send);

        adapter = new ChatAdapter();
        rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
        rvChat.setAdapter(adapter);

        // 观察 Room 数据库，实现历史记录自动加载
        repository.getAllMessages().observe(getViewLifecycleOwner(), messages -> {
            adapter.setMessages(messages);
            if (!messages.isEmpty()) {
                rvChat.scrollToPosition(messages.size() - 1);
            } else {
                // 如果数据库为空，显示欢迎语
                sendBotMessage("Hello " + username + "! I am your OpenAI assistant. How can I help you today?");
            }
        });

        btnSend.setOnClickListener(v -> sendMessage());

        return view;
    }

    private void sendMessage() {
        String text = etMessage.getText().toString().trim();
        if (!TextUtils.isEmpty(text)) {
            // 1. 保存并显示用户消息
            ChatMessage userMsg = new ChatMessage(text, true, System.currentTimeMillis());
            repository.insert(userMsg);
            etMessage.setText("");

            // 2. 调用 LLM 服务层（封装好的后端调用）
            llmService.getResponse(text, new LlmService.LlmResponseCallback() {
                @Override
                public void onResponse(String response) {
                    sendBotMessage(response);
                }

                @Override
                public void onError(String error) {
                    // 如果 API Key 没配置好，可以显示更友好的提示
                    if (error.contains("401") || error.contains("Key")) {
                        sendBotMessage("API Key 验证失败，请检查 local.properties 配置。");
                    } else {
                        sendBotMessage("出错了: " + error);
                    }
                }
            });
        }
    }

    private void sendBotMessage(String text) {
        ChatMessage botMsg = new ChatMessage(text, false, System.currentTimeMillis());
        repository.insert(botMsg);
    }
}
