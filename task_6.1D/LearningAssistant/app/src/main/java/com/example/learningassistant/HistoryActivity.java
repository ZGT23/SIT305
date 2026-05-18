package com.example.learningassistant;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learningassistant.adapter.HistoryAdapter;
import com.example.learningassistant.model.QuizHistory;
import com.example.learningassistant.util.SharedPrefManager;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ImageButton btnBack = findViewById(R.id.btnBack);
        RecyclerView rvHistory = findViewById(R.id.rvHistory);

        btnBack.setOnClickListener(v -> finish());

        SharedPrefManager prefManager = SharedPrefManager.getInstance(this);
        List<QuizHistory> historyList = prefManager.getQuizHistory();

        HistoryAdapter adapter = new HistoryAdapter(historyList);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(adapter);
    }
}
