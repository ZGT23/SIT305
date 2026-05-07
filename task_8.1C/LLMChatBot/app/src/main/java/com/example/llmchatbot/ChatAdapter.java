package com.example.llmchatbot;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * ChatAdapter binds the ChatMessage data to the item_chat_message layout.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatMessage> messages = new ArrayList<>();
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        holder.tvMessage.setText(message.getText());
        holder.tvTimestamp.setText(timeFormat.format(new Date(message.getTimestamp())));

        // Simple alignment logic
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.llContainer.getLayoutParams();
        if (message.isUser()) {
            // User message: right aligned, green bubble
            params.gravity = Gravity.END;
            holder.llContainer.setBackgroundResource(R.drawable.bg_bubble_user);
        } else {
            // Bot message: left aligned, white bubble
            params.gravity = Gravity.START;
            holder.llContainer.setBackgroundResource(R.drawable.bg_bubble_bot);
        }
        holder.llContainer.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTimestamp;
        LinearLayout llContainer;

        ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message_text);
            tvTimestamp = itemView.findViewById(R.id.tv_timestamp);
            llContainer = itemView.findViewById(R.id.ll_message_container);
        }
    }
}
