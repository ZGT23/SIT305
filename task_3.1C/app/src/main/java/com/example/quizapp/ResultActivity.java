package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView tvUserName, tvScore;
    Button btnNewQuiz, btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvUserName = findViewById(R.id.tvUserName);
        tvScore = findViewById(R.id.tvScore);
        btnNewQuiz = findViewById(R.id.btnNewQuiz);
        btnFinish = findViewById(R.id.btnFinish);

        String userName = getIntent().getStringExtra("username");
        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);

        if (userName == null || userName.isEmpty()) {
            userName = "User";
        }

        tvUserName.setText("Well done, " + userName + "!");
        tvScore.setText("Your score: " + score + " / " + total);

        String finalUserName = userName;
        btnNewQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.putExtra("username", finalUserName);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        btnFinish.setOnClickListener(v -> {
            finishAffinity();
        });
    }
}