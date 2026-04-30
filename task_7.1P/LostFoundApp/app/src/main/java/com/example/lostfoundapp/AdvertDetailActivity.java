package com.example.lostfoundapp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lostfoundapp.data.DatabaseHelper;
import com.example.lostfoundapp.model.Advert;

public class AdvertDetailActivity extends AppCompatActivity {

    private TextView tvType, tvName, tvPhone, tvDescription, tvCategory, tvLocation, tvTimestamp;
    private ImageView ivImage;
    private Button btnRemove;
    private DatabaseHelper dbHelper;
    private int advertId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_detail);

        dbHelper = new DatabaseHelper(this);
        advertId = getIntent().getIntExtra("ADVERT_ID", -1);

        tvType = findViewById(R.id.tvDetailType);
        ivImage = findViewById(R.id.ivDetailImage);
        tvName = findViewById(R.id.tvDetailName);
        tvPhone = findViewById(R.id.tvDetailPhone);
        tvDescription = findViewById(R.id.tvDetailDescription);
        tvCategory = findViewById(R.id.tvDetailCategory);
        tvLocation = findViewById(R.id.tvDetailLocation);
        tvTimestamp = findViewById(R.id.tvDetailTimestamp);
        btnRemove = findViewById(R.id.btnRemove);

        loadAdvertDetails();

        btnRemove.setOnClickListener(v -> showDeleteConfirmation());
    }

    private void loadAdvertDetails() {
        Advert advert = dbHelper.getAdvertById(advertId);
        if (advert != null) {
            tvType.setText(advert.getType());
            tvName.setText("Name: " + advert.getName());
            tvPhone.setText("Phone: " + advert.getPhone());
            tvDescription.setText("Description: " + advert.getDescription());
            tvCategory.setText("Category: " + advert.getCategory());
            tvLocation.setText("Location: " + advert.getLocation());
            tvTimestamp.setText("Date: " + advert.getTimestamp());

            if (advert.getImageUri() != null) {
                ivImage.setImageURI(Uri.parse(advert.getImageUri()));
            }
        } else {
            Toast.makeText(this, "Error loading advert details", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Remove Advert")
                .setMessage("Are you sure you want to remove this item?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    dbHelper.deleteAdvert(advertId);
                    Toast.makeText(AdvertDetailActivity.this, "Advert removed", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
