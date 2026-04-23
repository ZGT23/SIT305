package com.example.learningassistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learningassistant.adapter.TaskAdapter;
import com.example.learningassistant.model.LearningTask;
import com.example.learningassistant.util.DummyData;
import com.example.learningassistant.util.OpenAIService;
import com.example.learningassistant.util.SharedPrefManager;
import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvStudyPlanPrompt, tvStudyPlanResponse, tvStudyPlanError;
    private Button btnStudyPlan;
    private ProgressBar progressStudyPlan;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        prefManager = SharedPrefManager.getInstance(this);

        TextView tvGreeting = findViewById(R.id.tvGreeting);
        TextView tvTasksDue = findViewById(R.id.tvTasksDue);
        RecyclerView rvTasks = findViewById(R.id.rvTasks);
        btnStudyPlan = findViewById(R.id.btnStudyPlan);
        tvStudyPlanPrompt = findViewById(R.id.tvStudyPlanPrompt);
        tvStudyPlanResponse = findViewById(R.id.tvStudyPlanResponse);
        tvStudyPlanError = findViewById(R.id.tvStudyPlanError);
        progressStudyPlan = findViewById(R.id.progressStudyPlan);
        Button btnLogout = findViewById(R.id.btnLogout);

        String username = prefManager.getUsername();
        tvGreeting.setText("Hello,\n" + username);

        List<LearningTask> tasks = DummyData.getLearningTasks();
        tvTasksDue.setText("You have " + tasks.size() + " tasks due");

        TaskAdapter adapter = new TaskAdapter(tasks, task -> {
            Intent intent = new Intent(this, QuizActivity.class);
            intent.putExtra("topic", task.getTitle());
            startActivity(intent);
        });
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setAdapter(adapter);

        btnStudyPlan.setOnClickListener(v -> generateStudyPlan());

        btnLogout.setOnClickListener(v -> {
            prefManager.logout();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void generateStudyPlan() {
        List<String> interests = prefManager.getInterests();
        if (interests.isEmpty()) {
            interests = Arrays.asList("Algorithms", "Data Structures");
        }

        String interestList = String.join(", ", interests);
        String prompt = "Generate a personalized 7-day study plan for a student interested in: "
            + interestList
            + ". Format as: Day 1: [topic - task]. Keep each day concise (1-2 lines).";

        tvStudyPlanPrompt.setText("Prompt sent to AI:\n" + prompt);
        tvStudyPlanPrompt.setVisibility(View.VISIBLE);
        progressStudyPlan.setVisibility(View.VISIBLE);
        tvStudyPlanResponse.setVisibility(View.GONE);
        tvStudyPlanError.setVisibility(View.GONE);
        btnStudyPlan.setEnabled(false);

        OpenAIService.getInstance().sendPrompt(prompt, new OpenAIService.Callback() {
            @Override
            public void onSuccess(String response) {
                runOnUiThread(() -> {
                    progressStudyPlan.setVisibility(View.GONE);
                    tvStudyPlanResponse.setText(response);
                    tvStudyPlanResponse.setVisibility(View.VISIBLE);
                    btnStudyPlan.setEnabled(true);
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> {
                    progressStudyPlan.setVisibility(View.GONE);
                    tvStudyPlanError.setText("Failed to generate plan: " + error);
                    tvStudyPlanError.setVisibility(View.VISIBLE);
                    btnStudyPlan.setEnabled(true);
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
