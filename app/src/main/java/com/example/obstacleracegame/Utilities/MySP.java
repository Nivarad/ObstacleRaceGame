package com.example.obstacleracegame.Utilities;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.obstacleracegame.Models.RecordsList;
import com.google.gson.Gson;

public class MySP {
    private static final String DB_FILE = "DB_FILE";

    private static MySP instance = null;
    private SharedPreferences sharedPreferences;

    private MySP(Context context) {
        sharedPreferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new MySP(context);
        }
    }

    public static MySP getInstance() {
        return instance;
    }

    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public RecordsList loadFromJson() {
        String fromSP = getString("ObstacleRaceGame", "");
        Log.d("", fromSP);
        RecordsList recordsListFromJson = new Gson().fromJson(fromSP, RecordsList.class);
        Log.d("", "" + recordsListFromJson);
        if (recordsListFromJson == null)
            recordsListFromJson = new RecordsList();

        return recordsListFromJson;
    }

    public void saveToJason(int score, double latitude, double longitude) {
        RecordsList recordsList;
        recordsList = loadFromJson();
        Log.d("records in save", "" + recordsList);
        recordsList.addItem(score + "", latitude, longitude);
        String jsonStr = new Gson().toJson(recordsList);
        putString("ObstacleRaceGame", jsonStr);
        Log.d("jsonStr", jsonStr.toString());
    }
}
