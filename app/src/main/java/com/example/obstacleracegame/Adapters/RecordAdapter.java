package com.example.obstacleracegame.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obstacleracegame.Interfaces.Map_Callback;
import com.example.obstacleracegame.Models.Record;
import com.example.obstacleracegame.Models.RecordsList;
import com.example.obstacleracegame.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {
    private RecordsList recordList;
    private Map_Callback map_callback;

    public RecordAdapter(RecordsList recordList, Map_Callback map_callback) {
        this.recordList = recordList;
        this.map_callback = map_callback;
        if (recordList == null) {
            recordList = new RecordsList();
            recordList.setName("ObstacleRaceGame");
        }
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        RecordViewHolder recordViewHolder = new RecordViewHolder(view);
        return recordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        Record record = getItem(position);
        holder.record_LBL_title.setText(record.getTitle());
        holder.record_LBL_points.setText(record.getScore() + "");
        holder.record_LAYOUT.setOnClickListener(v -> recordClick(record.getLatitude(), record.getLongitude()));
    }

    private void recordClick(double latitude, double longitude) {
        if (map_callback != null) {
            map_callback.recordClick(latitude, longitude);
        }
    }

    @Override
    public int getItemCount() {
        return this.recordList == null ? 0 : this.recordList.getRecords().size();
    }

    private Record getItem(int position) {
        return this.recordList.getRecords().get(position);
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        private ShapeableImageView record_IMG_cup;
        private MaterialTextView record_LBL_title;
        private MaterialTextView record_LBL_points;
        private RelativeLayout record_LAYOUT;


        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            record_IMG_cup = itemView.findViewById(R.id.record_IMG_cup);
            record_LBL_title = itemView.findViewById(R.id.record_LBL_title);
            record_LBL_points = itemView.findViewById(R.id.record_LBL_points);
            record_LAYOUT = itemView.findViewById(R.id.record_LAYOUT);
        }
    }

}
