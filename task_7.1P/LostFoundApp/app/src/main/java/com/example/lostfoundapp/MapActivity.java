package com.example.lostfoundapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.lostfoundapp.data.DatabaseHelper;
import com.example.lostfoundapp.model.Advert;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseHelper dbHelper;
    private FusedLocationProviderClient fusedLocationClient;
    private EditText etRadius;
    private Button btnApplyRadius;
    private Location userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        dbHelper = new DatabaseHelper(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        etRadius = findViewById(R.id.etRadius);
        btnApplyRadius = findViewById(R.id.btnApplyRadius);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        btnApplyRadius.setOnClickListener(v -> applyRadiusFilter());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        getUserLocationAndShowMarkers();
    }

    private void getUserLocationAndShowMarkers() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            return;
        }

        mMap.setMyLocationEnabled(true);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                userLocation = location;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()), 10));
            }
            showAllMarkers();
        });
    }

    private void showAllMarkers() {
        mMap.clear();
        List<Advert> advertList = dbHelper.getAllAdverts();
        for (Advert advert : advertList) {
            LatLng latLng = new LatLng(advert.getLatitude(), advert.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(advert.getName())
                    .snippet(advert.getType() + ": " + advert.getLocation()));
        }
    }

    private void applyRadiusFilter() {
        String radiusStr = etRadius.getText().toString().trim();
        if (TextUtils.isEmpty(radiusStr)) {
            showAllMarkers();
            return;
        }

        if (userLocation == null) {
            Toast.makeText(this, "User location not available", Toast.LENGTH_SHORT).show();
            return;
        }

        double radiusKm = Double.parseDouble(radiusStr);
        mMap.clear();
        List<Advert> advertList = dbHelper.getAllAdverts();
        boolean found = false;

        for (Advert advert : advertList) {
            float[] results = new float[1];
            Location.distanceBetween(userLocation.getLatitude(), userLocation.getLongitude(),
                    advert.getLatitude(), advert.getLongitude(), results);
            
            float distanceInKm = results[0] / 1000;
            if (distanceInKm <= radiusKm) {
                LatLng latLng = new LatLng(advert.getLatitude(), advert.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(advert.getName())
                        .snippet(advert.getType() + ": " + advert.getLocation()));
                found = true;
            }
        }

        if (!found) {
            Toast.makeText(this, "No lost or found items found within this radius.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getUserLocationAndShowMarkers();
        }
    }
}
