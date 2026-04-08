package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    TextView tvWelcome, tvProgress, tvQuestion;
    ProgressBar progressBar;
    RadioGroup radioGroupOptions;
    RadioButton rbOption1, rbOption2, rbOption3, rbOption4;
    Button btnSubmit;

    String[] questions = {
            "What is the capital of Australia?",
            "Which planet is known as the Red Planet?",
            "What is 5 + 3?",
            "Which language is used for Android development?",
            "Which animal is known as the king of the jungle?"
    };

    String[][] options = {
            {"Sydney", "Melbourne", "Canberra", "Brisbane"},
            {"Earth", "Mars", "Venus", "Jupiter"},
            {"6", "7", "8", "9"},
            {"Java", "HTML", "CSS", "SQL"},
            {"Tiger", "Elephant", "Lion", "Dog"}
    };

    int[] correctAnswers = {2, 1, 2, 0, 2};

    int currentQuestionIndex = 0;
    int score = 0;
    boolean isAnswered = false;
    String userName = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvWelcome = findViewById(R.id.tvWelcome);
        tvProgress = findViewById(R.id.tvProgress);
        tvQuestion = findViewById(R.id.tvQuestion);
        progressBar = findViewById(R.id.progressBar);
        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        rbOption1 = findViewById(R.id.rbOption1);
        rbOption2 = findViewById(R.id.rbOption2);
        rbOption3 = findViewById(R.id.rbOption3);
        rbOption4 = findViewById(R.id.rbOption4);
        btnSubmit = findViewById(R.id.btnSubmit);

        userName = getIntent().getStringExtra("username");
        if (userName == null || userName.isEmpty()) {
            userName = "User";
        }

        tvWelcome.setText("Welcome, " + userName);

        progressBar.setMax(questions.length);

        loadQuestion();

        btnSubmit.setOnClickListener(v -> {
            if (!isAnswered) {
                checkAnswer();
            } else {
                goToNextQuestion();
            }
        });
    }

    private void loadQuestion() {
        tvQuestion.setText(questions[currentQuestionIndex]);

        rbOption1.setText(options[currentQuestionIndex][0]);
        rbOption2.setText(options[currentQuestionIndex][1]);
        rbOption3.setText(options[currentQuestionIndex][2]);
        rbOption4.setText(options[currentQuestionIndex][3]);

        tvProgress.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.length);
        progressBar.setProgress(currentQuestionIndex + 1);

        radioGroupOptions.clearCheck();

        resetOptionStyle();
        enableOptions();

        isAnswered = false;
        btnSubmit.setText("Submit");
    }

    private void checkAnswer() {
        int selectedId = radioGroupOptions.getCheckedRadioButtonId();

        if (selectedId == -1) {
            Toast.makeText(this, "Please select an answer first.", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedButton = findViewById(selectedId);

        RadioButton[] optionButtons = {rbOption1, rbOption2, rbOption3, rbOption4};
        RadioButton correctButton = optionButtons[correctAnswers[currentQuestionIndex]];

        correctButton.setBackgroundColor(Color.GREEN);

        if (selectedButton == correctButton) {
            score++;
        } else {
            selectedButton.setBackgroundColor(Color.RED);
        }

        disableOptions();
        isAnswered = true;

        if (currentQuestionIndex == questions.length - 1) {
            btnSubmit.setText("Finish Quiz");
        } else {
            btnSubmit.setText("Next");
        }
    }

    private void goToNextQuestion() {
        if (currentQuestionIndex < questions.length - 1) {
            currentQuestionIndex++;
            loadQuestion();
        } else {
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("total", questions.length);
            intent.putExtra("username", userName);
            startActivity(intent);
            finish();
        }
    }

    private void disableOptions() {
        rbOption1.setEnabled(false);
        rbOption2.setEnabled(false);
        rbOption3.setEnabled(false);
        rbOption4.setEnabled(false);
    }

    private void enableOptions() {
        rbOption1.setEnabled(true);
        rbOption2.setEnabled(true);
        rbOption3.setEnabled(true);
        rbOption4.setEnabled(true);
    }

    private void resetOptionStyle() {
        rbOption1.setBackgroundColor(Color.TRANSPARENT);
        rbOption2.setBackgroundColor(Color.TRANSPARENT);
        rbOption3.setBackgroundColor(Color.TRANSPARENT);
        rbOption4.setBackgroundColor(Color.TRANSPARENT);
    }
}