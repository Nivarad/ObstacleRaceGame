package com.example.obstacleracegame.Models;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.obstacleracegame.Logic.DataManager;
import com.example.obstacleracegame.Logic.GameManager;
import com.example.obstacleracegame.R;
//import com.example.obstacleracegame.Utilities.StepDetector;
import com.example.obstacleracegame.Utilities.TiltDetector;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {

    private MaterialTextView main_LBL_score;

    private MaterialTextView main_LBL_coins;
    private ShapeableImageView[] spaceships;
    private ShapeableImageView[][] meteors;
    private ShapeableImageView[][] coins;
    private ShapeableImageView[] hearts;
    private MaterialTextView score;
    private ImageView rightArrow;
    private ImageView leftArrow;
    private GameManager gameManager;

    private MediaPlayer crashSound;

    private boolean screenIdle =true;

    private double speed;
//    private StepDetector stepDetector;
//    private final boolean sensorMode = MenuActivity.getSensorMode();

    private TiltDetector tiltDetector;

    private boolean gameOngoing = true;
    public Handler handler;

    private final Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            if (!gameOngoing) {
                // Stop the handler if the game is over
                handler.removeCallbacksAndMessages(null);
                return;
            }
            refreshUI();
            int delay= (int) (speed*1000);
            handler.postDelayed(this, delay); // Repeat every 0.8 second
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        boolean useSensors = initializeGame();


        handler = new Handler();
        handler.postDelayed(refreshRunnable, 3000);
        //setSensorMode(sensorMode);
        if(useSensors){
            tiltDetector = new TiltDetector(this);
        }
    }
    private void refreshUI() {
        if (gameManager.isLose()) {
            // If the game is over, stop the handler and open the score screen
            handler.removeCallbacksAndMessages(null);
//            openScoreScreen("Game Over!", gameManager.getScore());
            gameOngoing = false; // Set the flag to false to stop the handler from running
            gameManager.gameOver();
        } else {

            if (gameManager.checkMeteorCollision()) {
                hearts[hearts.length - gameManager.getCrashes()].setVisibility(View.INVISIBLE);
                Log.d("MainActivity", "SpaceShip exploded"); // add log message
//                Toast.makeText(getApplicationContext(), "SpaceShip exploded", Toast.LENGTH_SHORT).show();
                crashSound.start();
                crashDelay();
                clearScreen();
            } else {
                gameManager.addMeteorAndBitcoin();
//                showMeteorRefresh(gameManager.getMeteorPlaces());
//                showBitcoinRefresh(gameManager.getBitcoinPlaces());
            }
        }
        setScoreText();
        setCoinsCollected();
    }

//    private void playSensor() {
//        findViewById(DataManager.getLeftBTNID()).setVisibility(View.INVISIBLE);
//        findViewById(DataManager.getRightBTNID()).setVisibility(View.INVISIBLE);
//        StepCallback stepCallback = new StepCallback() {
//            @Override
//            public void stepLeft() {
//                gameManager.moveLeft(null);
//            }
//
//            @Override
//            public void stepRight() {
//                gameManager.moveRight(null);
//            }
//
//        };
////        stepDetector = new StepDetector(this.getApplicationContext(), stepCallback);
////        stepDetector.start();
//    }

    private void findView() {
        int rows = DataManager.getNumOfRows();
        int cols = DataManager.getNumOfCols();
        int numOfHearts = DataManager.getNumOfHearts();
        int numOfCars = DataManager.getNumOfCols();

        meteors = new ShapeableImageView[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                meteors[i][j] = findViewById(DataManager.getRocksID(i, j));
        }

        coins = new ShapeableImageView[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                coins[i][j] = findViewById(DataManager.getCoinsID(i, j));
        }

        hearts = new ShapeableImageView[numOfHearts];
        for (int i = 0; i < numOfHearts; i++)
            hearts[i] = findViewById(DataManager.getHeartsID(i));

        spaceships = new ShapeableImageView[numOfCars];
        for (int i = 0; i < numOfCars; i++)
            spaceships[i] = findViewById(DataManager.getSpaceshipID(i));

        score = findViewById(R.id.main_LBL_score);

        rightArrow = findViewById(R.id.rightArrow);
        leftArrow = findViewById(R.id.leftArrow);

        main_LBL_score = findViewById(R.id.main_LBL_score);
        main_LBL_coins=findViewById(R.id.main_LBL_coins);
    }

    public boolean initializeGame(){
        Intent intent = getIntent();

        // Retrieve the values from the Intent
        speed = getIntent().getDoubleExtra("speed", 2.0);
        boolean useSensors = getIntent().getBooleanExtra("useSensors", false);
//        gameManager = new GameManager(main_IMG_hearts.length,this.getApplicationContext());
        gameManager = new GameManager(spaceships, meteors, coins, hearts, this, score);
        crashSound = MediaPlayer.create(MainActivity.this,R.raw.sound_crash);
        clearScreen();
        setArrowsClickListeners();
        return useSensors;
    }
    public void clearScreen(){
        gameManager.clearMeteors();
        gameManager.clearBitcoins();
        gameManager.clearSpaceship();
    }
    private void setArrowsClickListeners() {
        rightArrow.setOnClickListener(iv -> clicked(iv.getTag().toString()));
        leftArrow.setOnClickListener(iv -> clicked(iv.getTag().toString()));
    }
    public void clicked(String tag) {
        int direction = tag.equals("right") ? 1 : -1;
        if(direction==1)
            gameManager.moveRight();
        else
            gameManager.moveLeft();
    }
    public void crashDelay(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameManager.resetBitcoins();
                gameManager.resetSpaceship();
                gameManager.resetMeteors();
            }
        }, 1000); // Delay in milliseconds
    }
    private void setScoreText() {
        main_LBL_score.setText("" + gameManager.getScore());
    }

    private void setCoinsCollected(){main_LBL_coins.setText("" + gameManager.getCoinsCollected());}
    public void setScreenIdle(boolean screenIdle) {
        this.screenIdle = screenIdle;
    }

    public boolean isScreenIdle() {
        return screenIdle;
    }

}