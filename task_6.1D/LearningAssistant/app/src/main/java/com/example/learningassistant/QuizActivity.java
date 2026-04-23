package com.example.learningassistant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.learningassistant.model.QuizQuestion;
import com.example.learningassistant.util.DummyData;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView tvHeader, tvQuestionNum, tvQuestion;
    private RadioGroup radioGroup;
    private RadioButton rbOption0, rbOption1, rbOption2;
    private Button btnNext, btnSubmit;

    private List<QuizQuestion> questions;
    private int currentIndex = 0;
    private String topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        topic = getIntent().getStringExtra("topic");
        questions = DummyData.getQuestionsForTopic(topic);

        tvHeader = findViewById(R.id.tvHeader);
        tvQuestionNum = findViewById(R.id.tvQuestionNum);
        tvQuestion = findViewById(R.id.tvQuestion);
        radioGroup = findViewById(R.id.radioGroup);
        rbOption0 = findViewById(R.id.rbOption0);
        rbOption1 = findViewById(R.id.rbOption1);
        rbOption2 = findViewById(R.id.rbOption2);
        btnNext = findViewById(R.id.btnNext);
        btnSubmit = findViewById(R.id.btnSubmit);

        tvHeader.setText(topic);
        showQuestion(currentIndex);

        btnNext.setOnClickListener(v -> {
            if (!saveAnswer()) return;
            currentIndex++;
            if (currentIndex < questions.size()) {
                showQuestion(currentIndex);
            }
        });

        btnSubmit.setOnClickListener(v -> {
            if (!saveAnswer()) return;
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("questions", new ArrayList<>(questions));
            intent.putExtra("topic", topic);
            startActivity(intent);
            finish();
        });
    }

    private void showQuestion(int index) {
        QuizQuestion q = questions.get(index);
        tvQuestionNum.setText("Question " + (index + 1) + " of " + questions.size());
        tvQuestion.setText(q.getQuestion());
        rbOption0.setText(q.getOptions()[0]);
        rbOption1.setText(q.getOptions()[1]);
        rbOption2.setText(q.getOptions()[2]);
        radioGroup.clearCheck();

        boolean isLast = (index == questions.size() - 1);
        btnNext.setVisibility(isLast ? View.GONE : View.VISIBLE);
        btnSubmit.setVisibility(isLast ? View.VISIBLE : View.GONE);
    }

    private boolean saveAnswer() {
        int checkedId = radioGroup.getCheckedRadioButtonId();
        if (checkedId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return false;
        }
        int selected = 0;
        if (checkedId == R.id.rbOption1) selected = 1;
        else if (checkedId == R.id.rbOption2) selected = 2;
        questions.get(currentIndex).setSelectedIndex(selected);
        return true;
    }
}
