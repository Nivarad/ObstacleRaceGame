package com.example.obstacleracegame.Logic;

import com.example.obstacleracegame.R;

public class DataManager {

    private static final int ROWS = 7;
    private static final int COLS = 5;
    private static final int HEARTS_NUM = 3;
    private static final int SPACESHIP_START_POSITION = COLS / 2;

    private static final int[][] rocksID = {
            {R.id.meteor00, R.id.meteor01, R.id.meteor02, R.id.meteor03, R.id.meteor04},
            {R.id.meteor10, R.id.meteor11, R.id.meteor12, R.id.meteor13, R.id.meteor14},
            {R.id.meteor20, R.id.meteor21, R.id.meteor22, R.id.meteor23, R.id.meteor24},
            {R.id.meteor30, R.id.meteor31, R.id.meteor32, R.id.meteor33, R.id.meteor34},
            {R.id.meteor40, R.id.meteor41, R.id.meteor42, R.id.meteor43, R.id.meteor44},
            {R.id.meteor50, R.id.meteor51, R.id.meteor52, R.id.meteor53, R.id.meteor54},
            {R.id.meteor60, R.id.meteor61, R.id.meteor62, R.id.meteor63, R.id.meteor64}

    };

    private static final int[][] coinsID = {
            {R.id.bitcoin00, R.id.bitcoin01, R.id.bitcoin02, R.id.bitcoin03, R.id.bitcoin04},
            {R.id.bitcoin10, R.id.bitcoin11, R.id.bitcoin12, R.id.bitcoin13, R.id.bitcoin14},
            {R.id.bitcoin20, R.id.bitcoin21, R.id.bitcoin22, R.id.bitcoin23, R.id.bitcoin24},
            {R.id.bitcoin30, R.id.bitcoin31, R.id.bitcoin32, R.id.bitcoin33, R.id.bitcoin34},
            {R.id.bitcoin40, R.id.bitcoin41, R.id.bitcoin42, R.id.bitcoin43, R.id.bitcoin44},
            {R.id.bitcoin50, R.id.bitcoin51, R.id.bitcoin52, R.id.bitcoin53, R.id.bitcoin54},
            {R.id.bitcoin60, R.id.bitcoin61, R.id.bitcoin62, R.id.bitcoin63, R.id.bitcoin64},

    };

    private static final int[] heartsID = {
            R.id.main_IMG_heart1, R.id.main_IMG_heart2, R.id.main_IMG_heart3
    };

    private static final int[] spaceshipID = {
            R.id.spaceship0, R.id.spaceship1, R.id.spaceship2, R.id.spaceship3, R.id.spaceship4
    };
    private static final int leftBTNID = R.id.leftArrow;
    private static final int rightBTNID = R.id.rightArrow;
//    private static final int fastButtonID = R.id.menu_BTN_fastGame;
//    private static final int slowButtonID = R.id.menu_BTN_slowGame;
//    private static final int sensorButtonID = R.id.menu_BTN_SensorGame;
    private static final int recordsButtonID = R.id.menu_BTN_Records;
    private static final int soundCrashID = R.raw.sound_crash;
    private static final int soundCoinID = R.raw.sound_coin;
    private static final float DEFAULT_ZOOM = 12.0f;
    private static final int FAST_MODE = 500;
    private static final int SLOW_MODE = 1000;

    public static int getRocksID(int rowIndex, int colIndex) {
        return rocksID[rowIndex][colIndex];
    }

    public static int getCoinsID(int rowIndex, int colIndex) {
        return coinsID[rowIndex][colIndex];
    }

    public static int getHeartsID(int index) {
        return heartsID[index];
    }

    public static int getSpaceshipID(int index) {
        return spaceshipID[index];
    }

//    public static int getFastId() {
//        return fastButtonID;
//    }
//
//    public static int getSlowId() {
//        return slowButtonID;
//    }
//
//    public static int getSensorButtonID() {
//        return sensorButtonID;
//    }

    public static int getRecordsID() {
        return recordsButtonID;
    }

    public static int getNumOfRows() {
        return ROWS;
    }

    public static int getNumOfCols() {
        return COLS;
    }

    public static int getNumOfHearts() {
        return HEARTS_NUM;
    }

    public static int getCarStartPosition() {
        return SPACESHIP_START_POSITION;
    }

    public static int getLeftBTNID() {
        return leftBTNID;
    }

    public static int getRightBTNID() {
        return rightBTNID;
    }

    public static int getSoundCrashID() {
        return soundCrashID;
    }

    public static int getSoundCoinID() {
        return soundCoinID;
    }

    public static float getDefaultZoom() {
        return DEFAULT_ZOOM;
    }

    public static int getFastMode() {
        return FAST_MODE;
    }

    public static int getSlowMode() {
        return SLOW_MODE;
    }
}
