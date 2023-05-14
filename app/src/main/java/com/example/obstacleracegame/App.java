package com.example.obstacleracegame;

import android.app.Application;

import com.example.obstacleracegame.Utilities.MySP;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MySP.init(this);
        SignalGenerator.init(this);

    }

}
