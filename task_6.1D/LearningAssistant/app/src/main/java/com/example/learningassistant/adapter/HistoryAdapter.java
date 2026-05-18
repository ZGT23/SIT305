package com.example.learningassistant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learningassistant.R;
import com.example.learningassistant.model.QuizHistory;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<QuizHistory> historyList;

    public HistoryAdapter(List<QuizHistory> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuizHistory history = historyList.get(position);
        holder.tvTopic.setText(history.getTopic());
        holder.tvScore.setText("Score: " + history.getScore() + " / " + history.getTotalQuestions());
        holder.tvDate.setText(history.getDate());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTopic, tvScore, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTopic = itemView.findViewById(R.id.tvHistoryTopic);
            tvScore = itemView.findViewById(R.id.tvHistoryScore);
            tvDate = itemView.findViewById(R.id.tvHistoryDate);
        }
    }
}
