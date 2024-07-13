package com.ahmad.ahmadhajyahya_hw2;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ahmad.ahmadhajyahya_hw2.Utilities.SoundPlayer;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class GameManager {
    private Context context;
    private final int COLS = 5;
    private final int ROWS = 5;
    private int lives = 3;
    private int coins = 0;
    private SoundPlayer soundPlayer;

    protected static final int REQUEST_LOCATION_PERMISSION = 1;

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
    public void openDialog(String title, String message, String button) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(button, null);
        builder.create().show();
    }
    public void saveData(){
        saveScoreAndLocation();
    }
    private void saveScoreAndLocation() {
        // Check location permissions
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            getCurrentLocationAndSave();
        }
    }

    private void getCurrentLocationAndSave() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    saveScore(latitude, longitude);
                } else {
                    // Location is null, handle it accordingly
                    Log.d("ttttt","location is null");
                }
            }
        }
    }

    private void saveScore(double latitude, double longitude) {
        int currentScore = getCoins();

        SharedPreferences sharedPreferences = context.getSharedPreferences("HighScores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String highScores = sharedPreferences.getString("high_scores", "");
        highScores += currentScore + "," + latitude + "," + longitude + ";";
        editor.putString("high_scores", highScores);
        editor.apply();
        Log.d("ttttt","saved");
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocationAndSave();
            } else {
                // Permission denied, handle it accordingly
                Log.d("ttttt","permission denied");
            }
        }
    }
}
