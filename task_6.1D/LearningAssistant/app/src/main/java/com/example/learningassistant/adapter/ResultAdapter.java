package com.example.learningassistant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learningassistant.R;
import com.example.learningassistant.model.QuizQuestion;
import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private final List<QuizQuestion> questions;
    private final OnExplainClickListener listener;

    public interface OnExplainClickListener {
        void onExplainClick(int position, QuizQuestion question, ViewHolder holder);
    }

    public ResultAdapter(List<QuizQuestion> questions, OnExplainClickListener listener) {
        this.questions = questions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_result, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuizQuestion q = questions.get(position);
        holder.tvQuestion.setText((position + 1) + ". " + q.getQuestion());
        holder.tvYourAnswer.setText("Your answer: " + q.getSelectedAnswer());
        holder.tvCorrectAnswer.setText("Correct answer: " + q.getCorrectAnswer());

        boolean correct = q.isCorrect();
        holder.tvStatus.setText(correct ? "✓ Correct" : "✗ Incorrect");
        holder.tvStatus.setTextColor(holder.itemView.getContext().getColor(
            correct ? R.color.colorCorrect : R.color.colorIncorrect
        ));

        // Reset AI response state
        holder.tvPrompt.setVisibility(View.GONE);
        holder.tvAiResponse.setVisibility(View.GONE);
        holder.progressBar.setVisibility(View.GONE);
        holder.tvError.setVisibility(View.GONE);
        holder.btnExplain.setEnabled(true);

        holder.btnExplain.setOnClickListener(v ->
            listener.onExplainClick(position, q, holder)
        );
    }

    @Override
    public int getItemCount() { return questions.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvQuestion, tvYourAnswer, tvCorrectAnswer, tvStatus;
        public final TextView tvPrompt, tvAiResponse, tvError;
        public final Button btnExplain;
        public final ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvYourAnswer = itemView.findViewById(R.id.tvYourAnswer);
            tvCorrectAnswer = itemView.findViewById(R.id.tvCorrectAnswer);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvPrompt = itemView.findViewById(R.id.tvPrompt);
            tvAiResponse = itemView.findViewById(R.id.tvAiResponse);
            tvError = itemView.findViewById(R.id.tvError);
            btnExplain = itemView.findViewById(R.id.btnExplain);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
