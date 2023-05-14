package com.example.obstacleracegame.Models;

import java.util.ArrayList;
import java.util.Collections;

public class RecordsList {
    private String name = "";
    private ArrayList<Record> records = new ArrayList<>();

    public RecordsList() {
    }

    public String getName() {
        return name;
    }

    public RecordsList setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public RecordsList setRecords(ArrayList<Record> records) {
        this.records = records;
        return this;
    }

    public void sortRecords() {
        Collections.sort(records);
    }

    public void addItem(String score, double latitude, double longitude) {
        if (records.size() == 10)
            records.remove(records.size() - 1);

        records.add(new Record()
                .setTitle("Score")
                .setScore(score)
                .setLatitude(latitude)
                .setLongitude(longitude)
        );
        sortRecords();
    }

    @Override
    public String toString() {
        return "RecordsList{" +
                "name='" + name + '\'' +
                ", records=" + records +
                '}';
    }
}
