package com.example.learningassistant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learningassistant.adapter.InterestAdapter;
import com.example.learningassistant.util.SharedPrefManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterestSetupActivity extends AppCompatActivity {

    private static final List<String> ALL_INTERESTS = Arrays.asList(
        "Algorithms", "Data Structures", "Web Development", "Testing",
        "AI/ML", "Databases", "Android", "Python", "JavaScript", "Cloud Computing"
    );

    private final List<String> selectedInterests = new ArrayList<>();
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_setup);
        prefManager = SharedPrefManager.getInstance(this);

        RecyclerView rvInterests = findViewById(R.id.rvInterests);
        Button btnNext = findViewById(R.id.btnNext);

        InterestAdapter adapter = new InterestAdapter(ALL_INTERESTS, selectedInterests);
        rvInterests.setLayoutManager(new GridLayoutManager(this, 2));
        rvInterests.setAdapter(adapter);

        btnNext.setOnClickListener(v -> {
            if (selectedInterests.isEmpty()) {
                Toast.makeText(this, "Please select at least one interest", Toast.LENGTH_SHORT).show();
                return;
            }
            prefManager.saveInterests(selectedInterests);
            prefManager.setSetupDone(true);
            prefManager.setLoggedIn(true);
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        });
    }
}
