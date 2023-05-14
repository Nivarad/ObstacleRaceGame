package com.example.obstacleracegame.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.obstacleracegame.R;
import com.example.obstacleracegame.SignalGenerator;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {
    private GoogleMap gMap;
    private final int FINE_PERMISSION_CODE = 1;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public boolean locationPermissionGranted = false;
    private View view;
    private Marker marker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);
        initMap();

        getLocationPermission();

        return view;
    }

    public void moveCamera(LatLng latLng, float zoom) {
        if (marker != null)
            marker.remove();
        marker = gMap.addMarker(new MarkerOptions()
                .position(latLng));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 0.0f));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMap -> {
            gMap = googleMap;
        });
    }

    public void getLocationPermission() {
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(view.getContext().getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(view.getContext().getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
                initMap();
            }
        } else {
            if (this.getActivity() != null)
                ActivityCompat.requestPermissions(this.getActivity(), permission, FINE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = false;
            } else {
                locationPermissionGranted = true;
                SignalGenerator.getInstance().toast("Location Permission is denied", Toast.LENGTH_SHORT);
            }
        }
    }
}
