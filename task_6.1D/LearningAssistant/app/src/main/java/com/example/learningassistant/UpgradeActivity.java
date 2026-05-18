package com.example.learningassistant;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.learningassistant.util.SharedPrefManager;

public class UpgradeActivity extends AppCompatActivity {

    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        prefManager = SharedPrefManager.getInstance(this);

        Button btnStarter = findViewById(R.id.btnPurchaseStarter);
        Button btnIntermediate = findViewById(R.id.btnPurchaseIntermediate);
        Button btnAdvanced = findViewById(R.id.btnPurchaseAdvanced);
        Button btnBack = findViewById(R.id.btnBack);

        btnStarter.setOnClickListener(v -> showPurchaseDialog("Starter", "$4.99"));
        btnIntermediate.setOnClickListener(v -> showPurchaseDialog("Intermediate", "$9.99"));
        btnAdvanced.setOnClickListener(v -> showPurchaseDialog("Advanced", "$19.99"));

        btnBack.setOnClickListener(v -> finish());
    }

    private void showPurchaseDialog(String planName, String price) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Purchase")
                .setMessage("Would you like to upgrade to the " + planName + " plan for " + price + "/month?\n\n(This is a mock payment for demonstration purposes)")
                .setPositiveButton("Purchase", (dialog, which) -> {
                    prefManager.setAccountType(planName);
                    Toast.makeText(this, "Successfully upgraded to " + planName + "!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
