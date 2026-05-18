package com.example.learningassistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learningassistant.adapter.ResultAdapter;
import com.example.learningassistant.model.QuizHistory;
import com.example.learningassistant.model.QuizQuestion;
import com.example.learningassistant.util.OpenAIService;
import com.example.learningassistant.util.SharedPrefManager;
import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private ResultAdapter adapter;
    private List<QuizQuestion> questions;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        prefManager = SharedPrefManager.getInstance(this);
        questions = (ArrayList<QuizQuestion>) getIntent().getSerializableExtra("questions");
        String topic = getIntent().getStringExtra("topic");

        int correctCount = 0;
        for (QuizQuestion q : questions) {
            if (q.isCorrect()) correctCount++;
        }

        // Save results for 10.1D
        prefManager.updateStats(correctCount, questions.size() - correctCount);
        prefManager.saveQuizHistory(new QuizHistory(topic != null ? topic : "Quiz", correctCount, questions.size()));

        TextView tvHeader = findViewById(R.id.tvHeader);
        if (topic != null) tvHeader.setText(topic + " - Results");

        TextView tvScore = findViewById(R.id.tvScore);
        if (tvScore != null) {
            tvScore.setText("Score: " + correctCount + " / " + questions.size());
        }

        RecyclerView rvResults = findViewById(R.id.rvResults);
        Button btnContinue = findViewById(R.id.btnContinue);

        adapter = new ResultAdapter(questions, this::explainAnswer);
        rvResults.setLayoutManager(new LinearLayoutManager(this));
        rvResults.setAdapter(adapter);

        btnContinue.setOnClickListener(v -> {
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void explainAnswer(int position, QuizQuestion question, ResultAdapter.ViewHolder holder) {
        String prompt = "Quiz question: \"" + question.getQuestion() + "\"\n"
            + "Student selected: \"" + question.getSelectedAnswer() + "\"\n"
            + "Correct answer: \"" + question.getCorrectAnswer() + "\"\n"
            + "In 2-3 sentences, explain why \"" + question.getCorrectAnswer()
            + "\" is the correct answer" + (question.isCorrect() ? " (student was correct)." : " and why the student's choice was wrong.");

        holder.tvPrompt.setText("Prompt sent to AI:\n" + prompt);
        holder.tvPrompt.setVisibility(View.VISIBLE);
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.tvAiResponse.setVisibility(View.GONE);
        holder.tvError.setVisibility(View.GONE);
        holder.btnExplain.setEnabled(false);

        OpenAIService.getInstance().sendPrompt(prompt, new OpenAIService.Callback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    holder.progressBar.setVisibility(View.GONE);
                    holder.tvAiResponse.setText("AI Response:\n" + response);
                    holder.tvAiResponse.setVisibility(View.VISIBLE);
                    holder.btnExplain.setEnabled(true);
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> {
                    holder.progressBar.setVisibility(View.GONE);
                    holder.tvError.setText("Error: " + error);
                    holder.tvError.setVisibility(View.VISIBLE);
                    holder.btnExplain.setEnabled(true);
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
