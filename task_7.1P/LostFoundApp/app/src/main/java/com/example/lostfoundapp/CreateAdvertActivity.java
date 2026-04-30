package com.example.lostfoundapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lostfoundapp.data.DatabaseHelper;
import com.example.lostfoundapp.model.Advert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateAdvertActivity extends AppCompatActivity {

    private RadioGroup rgType;
    private EditText etName, etPhone, etDescription, etLocation;
    private Spinner spinnerCategory;
    private ImageView ivSelectedImage;
    private Button btnSelectImage, btnSave;
    private Uri selectedImageUri;
    private DatabaseHelper dbHelper;

    private final ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    ivSelectedImage.setImageURI(uri);
                    // Take persistable URI permission to access it later
                    getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        dbHelper = new DatabaseHelper(this);

        rgType = findViewById(R.id.rgType);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etDescription = findViewById(R.id.etDescription);
        etLocation = findViewById(R.id.etLocation);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        ivSelectedImage = findViewById(R.id.ivSelectedImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnSave = findViewById(R.id.btnSave);

        // Setup Spinner
        String[] categories = {"Electronics", "Pets", "Wallets", "Keys", "Bags", "Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        btnSelectImage.setOnClickListener(v -> mGetContent.launch("image/*"));

        btnSave.setOnClickListener(v -> saveAdvert());
    }

    private void saveAdvert() {
        String type = ((RadioButton) findViewById(rgType.getCheckedRadioButtonId())).getText().toString();
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || 
            TextUtils.isEmpty(description) || TextUtils.isEmpty(location) || selectedImageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        Advert advert = new Advert();
        advert.setType(type);
        advert.setName(name);
        advert.setPhone(phone);
        advert.setDescription(description);
        advert.setCategory(category);
        advert.setLocation(location);
        advert.setImageUri(selectedImageUri.toString());
        advert.setTimestamp(timestamp);

        long id = dbHelper.insertAdvert(advert);

        if (id > 0) {
            Toast.makeText(this, "Advert saved successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to save advert", Toast.LENGTH_SHORT).show();
        }
    }
}
