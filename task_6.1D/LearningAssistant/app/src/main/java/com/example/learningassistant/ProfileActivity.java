package com.example.learningassistant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.learningassistant.util.QRCodeHelper;
import com.example.learningassistant.util.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {

    private SharedPrefManager prefManager;
    private TextView tvProfileName, tvProfileEmail, tvTotalQuestions, tvCorrect, tvIncorrect, tvAccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        prefManager = SharedPrefManager.getInstance(this);

        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvTotalQuestions = findViewById(R.id.tvTotalQuestions);
        tvCorrect = findViewById(R.id.tvCorrect);
        tvIncorrect = findViewById(R.id.tvIncorrect);
        tvAccountType = findViewById(R.id.tvAccountType);

        Button btnUpgrade = findViewById(R.id.btnUpgrade);
        Button btnHistory = findViewById(R.id.btnHistory);
        Button btnShare = findViewById(R.id.btnShare);
        Button btnBack = findViewById(R.id.btnBack);

        loadProfileData();

        btnUpgrade.setOnClickListener(v -> {
            startActivity(new Intent(this, UpgradeActivity.class));
        });

        btnHistory.setOnClickListener(v -> {
            startActivity(new Intent(this, HistoryActivity.class));
        });

        btnShare.setOnClickListener(v -> {
            showShareDialog();
        });

        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfileData(); // Refresh data when coming back from Upgrade
    }

    private void loadProfileData() {
        tvProfileName.setText(prefManager.getUsername());
        tvProfileEmail.setText(prefManager.getEmail());
        tvTotalQuestions.setText(String.valueOf(prefManager.getTotalQuestions()));
        tvCorrect.setText(String.valueOf(prefManager.getCorrectAnswers()));
        tvIncorrect.setText(String.valueOf(prefManager.getIncorrectAnswers()));
        tvAccountType.setText(prefManager.getAccountType() + " Plan");
    }

    private void showShareDialog() {
        String shareContent = getShareBody();
        Bitmap qrCode = QRCodeHelper.generateQRCode(shareContent);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_qr_share, null);
        ImageView ivQRCode = dialogView.findViewById(R.id.ivQRCode);
        if (qrCode != null) {
            ivQRCode.setImageBitmap(qrCode);
        }

        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Share as Text", (dialog, which) -> {
                    shareProfileAsText();
                })
                .setNegativeButton("Close", null)
                .show();
    }

    private String getShareBody() {
        return "Learning Assistant Profile:\n" +
                "User: " + prefManager.getUsername() + "\n" +
                "Level: " + prefManager.getAccountType() + "\n" +
                "Stats: " + prefManager.getCorrectAnswers() + "/" + prefManager.getTotalQuestions() + " Correct";
    }

    private void shareProfileAsText() {
        String shareBody = "Check out my learning progress on Learning Assistant!\n\n" +
                "Username: " + prefManager.getUsername() + "\n" +
                "Account Type: " + prefManager.getAccountType() + "\n" +
                "Total Questions: " + prefManager.getTotalQuestions() + "\n" +
                "Correct Answers: " + prefManager.getCorrectAnswers() + "\n" +
                "Learning is better with AI!";

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Learning Progress");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}
