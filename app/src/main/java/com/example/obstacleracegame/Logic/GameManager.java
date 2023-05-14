package com.example.obstacleracegame.Logic;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.obstacleracegame.Models.MainActivity;
import com.example.obstacleracegame.Models.MenuActivity;
import com.example.obstacleracegame.Models.Record;
import com.example.obstacleracegame.R;
import com.example.obstacleracegame.SignalGenerator;
import com.example.obstacleracegame.Utilities.MySP;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.Random;

public class GameManager {

    private int carIndex;
    private int crashes;
    private boolean flag = false;
    private final ShapeableImageView[] spaceships;
    private final ShapeableImageView[][] meteors;
    private final ShapeableImageView[][] coins;
    private final ShapeableImageView[] hearts;
    private final MaterialTextView main_LBL_score;
    private int score =0;
    private final int numOfRows = DataManager.getNumOfRows();
    private final int numOfColumns = DataManager.getNumOfCols();

    private final Context context;
    private Record record;
    private double latitude;
    private double longitude;

    private MediaPlayer sound_coin;

    private int life=3;

    private int coinsCollected;

    public GameManager(ShapeableImageView[] spaceships, ShapeableImageView[][] meteors, ShapeableImageView[][] coins, ShapeableImageView[] hearts, Context context, MaterialTextView main_LBL_score) {
        this.meteors = meteors;
        this.coins = coins;
        this.spaceships = spaceships;
        this.hearts = hearts;
        carIndex = DataManager.getCarStartPosition();
        this.crashes = 0;
        this.main_LBL_score = main_LBL_score;
        this.context = context;
        this.coinsCollected=0;
        this.sound_coin =MediaPlayer.create(context, R.raw.sound_coin);
        SignalGenerator.init(context);
    }

    public void moveLeft() {
        if (carIndex > 0) {
            spaceships[carIndex].setVisibility(View.INVISIBLE);
            spaceships[carIndex - 1].setVisibility(View.VISIBLE);
            carIndex--;
        }
    }

    public void moveRight() {
        if (carIndex < numOfColumns - 1) {
            spaceships[carIndex].setVisibility(View.INVISIBLE);
            spaceships[carIndex + 1].setVisibility(View.VISIBLE);
            carIndex++;
        }
    }

    public void randomObstacle() {
        Random rand = new Random();
        int col = rand.nextInt(5);
        int type = rand.nextInt(4);
        if (!flag) {
            if (type == 3)
                coins[0][col].setVisibility(View.VISIBLE);
            else
                meteors[0][col].setVisibility(View.VISIBLE);
            flag = true;
        } else {
            flag = false;
        }
    }

    public void siftDown() {
        for (int i = numOfRows - 1; i > 0; i--) {
            for (int j = 0; j < numOfColumns; j++) {
                meteors[i][j].setVisibility(meteors[i - 1][j].getVisibility());
                meteors[i - 1][j].setVisibility(View.INVISIBLE);

                coins[i][j].setVisibility(coins[i - 1][j].getVisibility());
                coins[i - 1][j].setVisibility(View.INVISIBLE);
            }
        }
//        collision();
    }



    private void updateScoreView() {
        if (score < 10)
            main_LBL_score.setText("00" + score);
        else if (score < 100)
            main_LBL_score.setText("0" + score);
        else
            main_LBL_score.setText("" + score);
    }

//    public void startTime() {
//        if (timer == null) {
//            timer = new CountDownTimer(99999999, getDelayType()) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//                    randomObstacle();
//                    siftDown();
//                    getScore();
//                }
//
//                @Override
//                public void onFinish() {
//                    timer.cancel();
//                }
//            }.start();
//        }
//    }

//    private long getDelayType() {
//        return MenuActivity.getMode();
//    }


    public void gameOver() {
        SignalGenerator.getInstance().toast("Game Over \uD83D\uDE23", Toast.LENGTH_SHORT);
        record = setRecordLatlng();

        MySP.getInstance().saveToJason(getScore(), record.getLatitude(), record.getLongitude());
//        if (record.getLatitude() != 0.0 || record.getLongitude() != 0.0)
//            MySP.getInstance().saveToJason(getScore(), record.getLatitude(), record.getLongitude());
//        else
//            SignalGenerator.getInstance().toast("Last known location is null", Toast.LENGTH_SHORT);
        //stopTime();
        openMenuActivity();
    }

    private Record setRecordLatlng() {
        record = new Record();
        record.setScore("" + score);
        record.setTitle("Score");

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            SignalGenerator.getInstance().toast("You should permit location access", Toast.LENGTH_SHORT);
            return null;
        }

        Location currLocation = null;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            currLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (currLocation == null && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            currLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (currLocation == null && locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            currLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        }

        if (currLocation != null) {
            latitude = currLocation.getLatitude();
            longitude = currLocation.getLongitude();
        }
        if(latitude ==0.0){
            latitude=32.095230;
        }
        if(longitude==0.0)
            longitude = 34.972642;

        record.setLatitude(latitude);
        record.setLongitude(longitude);

        return record;
    }

    public void openMenuActivity() {
        Intent intent = new Intent(context.getApplicationContext(), MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

//    public void stopTime() {
//        if (timer != null)
//            timer.cancel();
//        timer = null;
//    }
    public int getScore() {
        return score;
    }

    public void resetMeteors(){
        for(int i=0;i<meteors.length;i++){
            for(int j=0;j<meteors[0].length;j++){
                meteors[i][j].setVisibility(View.INVISIBLE);
            }
        }
    }
    public void resetBitcoins(){
        for(int i=0;i<coins.length;i++){
            for(int j=0;j<coins[0].length;j++){
                coins[i][j].setVisibility(View.INVISIBLE);
            }
        }
    }
    public void addMeteorAndBitcoin(){
        //find space for next meteor
        Random rand = new Random();
        int meteorIndex = rand.nextInt(meteors[0].length);
        int bitcoinIndex=-1;
        while(bitcoinIndex<0 || bitcoinIndex==meteorIndex){
            bitcoinIndex=rand.nextInt(coins[0].length*3);
        }


        siftDown();

        for(int i=0;i<meteors[0].length;i++){
            meteors[0][i].setVisibility(View.INVISIBLE);
        }
        meteors[0][meteorIndex].setVisibility(View.VISIBLE);

        //find space for new bitcoin which isn't the meteor place

        for(int i=0;i<coins[0].length;i++){
            coins[0][i].setVisibility(View.INVISIBLE);
        }
        if(coins[0].length>bitcoinIndex)
            coins[0][bitcoinIndex].setVisibility(View.VISIBLE);
    }
    public boolean checkMeteorCollision(){
        int lastLine = meteors.length-1;
        if(meteors[lastLine][carIndex].getVisibility()==View.VISIBLE){
            SignalGenerator.getInstance().vibrate(500);
            SignalGenerator.getInstance().toast("CRASH!", Toast.LENGTH_LONG);
            crashes++;
            return true;
        }
        this.score+=5;

        checkBitcoinCollision();
        return false;
    }
    public void checkBitcoinCollision() {
        int lastLine = coins.length - 1;
        if (coins[lastLine][carIndex].getVisibility()==View.VISIBLE) {
            this.coinsCollected++;
            sound_coin.start();

        }
    }
    public void clearMeteors() {
        for (int i = 0; i < meteors.length; i++) {
            for(int j=0;j<meteors[0].length;j++)
                meteors[i][j].setVisibility(View.INVISIBLE);
        }
    }

    public void clearBitcoins(){
        for (int i = 0; i < coins.length; i++) {
            for(int j=0;j<coins[0].length;j++)
                coins[i][j].setVisibility(View.INVISIBLE);
        }
    }

    // Set the initial state of the spaceship
    public void clearSpaceship() {
        for (int i = 0; i < spaceships.length; i++) {
            spaceships[i].setVisibility(View.INVISIBLE);
        }
        spaceships[2].setVisibility(View.VISIBLE);
    }
    public boolean isLose() {
        return life == crashes;
    }

    public int getCrashes() {
        return crashes;
    }
    public void resetSpaceship(){
        carIndex = DataManager.getCarStartPosition();
    }
    public int getCoinsCollected(){
        return this.coinsCollected;
    }
}
