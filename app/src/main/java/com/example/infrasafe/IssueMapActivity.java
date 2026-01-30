package com.example.infrasafe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.firebase.firestore.FirebaseFirestore;

public class IssueMapActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_map);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        FirebaseFirestore.getInstance()
                .collection("issues")
                .get()
                .addOnSuccessListener(query -> {
                    for (var doc : query) {

                        double lat = doc.getDouble("latitude");
                        double lng = doc.getDouble("longitude");
                        String type = doc.getString("issueType");
                        String status = doc.getString("status");

                        LatLng location = new LatLng(lat, lng);

                        mMap.addMarker(new MarkerOptions()
                                .position(location)
                                .title(type)
                                .snippet("Status: " + status));
                    }
                });

        // Default camera position (example city)
        LatLng city = new LatLng(21.1458, 79.0882); // Nagpur
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(city, 12));
    }
}
