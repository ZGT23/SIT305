package com.example.lostfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostfoundapp.adapter.AdvertAdapter;
import com.example.lostfoundapp.data.DatabaseHelper;
import com.example.lostfoundapp.model.Advert;

import java.util.ArrayList;
import java.util.List;

public class AdvertListActivity extends AppCompatActivity {

    private RecyclerView rvAdverts;
    private AdvertAdapter adapter;
    private DatabaseHelper dbHelper;
    private Spinner spinnerFilter;
    private List<Advert> advertList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_list);

        dbHelper = new DatabaseHelper(this);
        rvAdverts = findViewById(R.id.rvAdverts);
        spinnerFilter = findViewById(R.id.spinnerFilter);

        rvAdverts.setLayoutManager(new LinearLayoutManager(this));
        advertList = new ArrayList<>();
        adapter = new AdvertAdapter(this, advertList, advert -> {
            Intent intent = new Intent(AdvertListActivity.this, AdvertDetailActivity.class);
            intent.putExtra("ADVERT_ID", advert.getId());
            startActivity(intent);
        });
        rvAdverts.setAdapter(adapter);

        setupFilterSpinner();
    }

    private void setupFilterSpinner() {
        String[] filters = {"All", "Electronics", "Pets", "Wallets", "Keys", "Bags", "Others"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filters);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(spinnerAdapter);

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = filters[position];
                loadAdverts(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void loadAdverts(String category) {
        if (category.equals("All")) {
            advertList = dbHelper.getAllAdverts();
        } else {
            advertList = dbHelper.getAdvertsByCategory(category);
        }
        adapter.setAdverts(advertList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list when returning to this activity
        loadAdverts(spinnerFilter.getSelectedItem().toString());
    }
}
