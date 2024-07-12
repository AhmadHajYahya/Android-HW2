package com.ahmad.ahmadhajyahya_hw2;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.ahmad.ahmadhajyahya_hw2.Utilities.SoundPlayer;

public class GameManager {
    private Context context;
    private final int COLS = 5;
    private final int ROWS = 5;
    private int lives = 3;
    private int coins = 0;
    private SoundPlayer soundPlayer;


    public GameManager( Context context) {

        this.context = context;
        soundPlayer = new SoundPlayer(context);
    }

    public void decreaseLive() {
        lives--;
    }

    public int getLives() {
        return lives;
    }

    public void lose(){
        Toast.makeText(context, "Game Over!", Toast.LENGTH_LONG).show();
    }
    public void vibrate() {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    public void makeCrashSound(){
        soundPlayer.playSound(R.raw.crash);
    }
    public void makeCoinSound(){
        soundPlayer.playSound(R.raw.coin);
    }

    public void addCoin(){
        this.coins++;
    }

    public int getCoins(){
        return this.coins;
    }
    public int getCOLS() {
        return COLS;
    }

    public int getROWS() {
        return ROWS;
    }
}
