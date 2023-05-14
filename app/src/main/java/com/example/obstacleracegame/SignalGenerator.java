package com.example.obstacleracegame;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.example.obstacleracegame.Logic.DataManager;

public class SignalGenerator {
    private static SignalGenerator instance = null;
    private Context context;
    private static Vibrator vibrator;
    private static MediaPlayer mediaPlayer;

    private SignalGenerator(Context context) {
        this.context = context;
    }

    public static void init(Context context) {

        instance = new SignalGenerator(context);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//            if (mediaPlayer == null) {
//                mediaPlayer = MediaPlayer.create(context, DataManager.getSoundCrashID());
//            }

    }

    public static SignalGenerator getInstance() {
        return instance;
    }

    public void toast(String text, int length) {
        Log.d("context", String.valueOf(context));
        Toast
                .makeText(context, text, length)
                .show();
    }

    public void vibrate(long length) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(length, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(length);
        }
    }

    public void sound(int soundID) {
        mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(context, soundID);
        if (soundID == DataManager.getSoundCoinID())
            mediaPlayer.setVolume(0.5f, 0.5f);
        mediaPlayer.start();
    }

    public static void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
