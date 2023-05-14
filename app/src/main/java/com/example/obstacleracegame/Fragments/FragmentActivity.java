package com.example.obstacleracegame.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.obstacleracegame.Interfaces.Map_Callback;
import com.example.obstacleracegame.Logic.DataManager;
import com.example.obstacleracegame.Models.MenuActivity;
import com.example.obstacleracegame.R;
import com.google.android.gms.maps.model.LatLng;

public class FragmentActivity extends AppCompatActivity {

    private ListFragment listFragment;
    private MapFragment mapFragment;
    AppCompatButton move2Menu ;

    Map_Callback map_callback = new Map_Callback() {
        @Override
        public void recordClick(double latitude, double longitude) {
            mapFragment.moveCamera(new LatLng(latitude, longitude), DataManager.getDefaultZoom());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_records);

        initFragments();
        listFragment.setMap_callback(map_callback);
        beginTransactions();

        move2Menu= findViewById(R.id.records_BUTTON_moveToMenu);
        move2Menu.setOnClickListener(v -> openMenuActivity());
    }

    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, mapFragment).commit();
    }

    private void initFragments() {
        listFragment = new ListFragment();
        mapFragment = new MapFragment();
    }
    private void openMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }


}

