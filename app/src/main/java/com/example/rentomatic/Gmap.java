package com.example.rentomatic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Gmap extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap gMap;
    double lat;
    double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.pulseMap);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Intent intent = getIntent();
        String locationname = intent.getStringExtra("location");
        Geocoder geocoder = new Geocoder(this);
        double lat = 0.0;
        double lng = 0.0;
        try {
            List<Address> addresses = geocoder.getFromLocationName(locationname, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                lat = address.getLatitude();
                lng = address.getLongitude();
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        LatLng location = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(location).title(locationname));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
    }
}