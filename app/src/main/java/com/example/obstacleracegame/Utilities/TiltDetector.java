package com.example.obstacleracegame.Utilities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.obstacleracegame.Models.MainActivity;

public class TiltDetector implements SensorEventListener {
    private MainActivity mainActivity;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    public TiltDetector(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        sensorManager = (SensorManager) mainActivity.getSystemService(MainActivity.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            if (accelerometerSensor != null) {
                sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d("meganometer", "Z: " + sensorEvent.values[2] + ", screen: " + mainActivity.isScreenIdle());  // add log message

        if (sensorEvent.values[0] > 10 && mainActivity.isScreenIdle()) {
            Log.d("sensor","entered");
            mainActivity.clicked("right");
            mainActivity.setScreenIdle(false);
        } else if (sensorEvent.values[0] < -10 && mainActivity.isScreenIdle()) {
            Log.d("sensor","entered");
            mainActivity.clicked("left");
            mainActivity.setScreenIdle(false);
        } else if (Math.abs(sensorEvent.values[0]) < 5 && !mainActivity.isScreenIdle()) {
            mainActivity.setScreenIdle(true);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }

    public void unregisterListener() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }
}
