package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        btnStart = findViewById(R.id.btnStart);

        String savedUserName = getIntent().getStringExtra("username");
        if (savedUserName != null && !savedUserName.isEmpty()) {
            etName.setText(savedUserName);
        }

        btnStart.setOnClickListener(v -> {
            String userName = etName.getText().toString().trim();

            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("username", userName);
            startActivity(intent);
        });
    }
}