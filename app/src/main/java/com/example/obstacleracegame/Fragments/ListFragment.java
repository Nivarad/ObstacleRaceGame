package com.example.obstacleracegame.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obstacleracegame.Adapters.RecordAdapter;
import com.example.obstacleracegame.Interfaces.Map_Callback;
import com.example.obstacleracegame.Models.RecordsList;
import com.example.obstacleracegame.R;
import com.example.obstacleracegame.Utilities.MySP;

public class ListFragment extends Fragment {

    private RecyclerView record_LST_records;
    private RecordsList recordsList;
    private Map_Callback map_callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        recordsList = MySP.getInstance().loadFromJson();
        RecordAdapter recordAdapter = new RecordAdapter(recordsList, map_callback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        record_LST_records.setAdapter(recordAdapter);
        record_LST_records.setLayoutManager(linearLayoutManager);
    }

    private void findViews(View view) {
        record_LST_records = view.findViewById(R.id.record_LST_records);
    }

    public void setMap_callback(Map_Callback map_callback) {
        this.map_callback = map_callback;
    }
}
