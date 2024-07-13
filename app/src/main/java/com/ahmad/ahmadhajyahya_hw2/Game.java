package com.ahmad.ahmadhajyahya_hw2;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import java.util.Random;

public class Game extends AppCompatActivity {
    private final AppCompatImageView[] obstacles_IMG_hearts;
    private final AppCompatImageView obstacles_IMG_car;
    private final AppCompatImageView obstacle;
    private final AppCompatImageView obstacle1;
    private final AppCompatImageView coin;
    private TextView score;
    private int currentCarLane = 1;
    private final RelativeLayout[][] cells;
    private int obsCurrentRow = 0;
    private int obsCurrentCol = 0;
    private int obs1CurrentRow = 0;
    private int obs1CurrentCol = 0;
    private int coinCurrentRow = 0;
    private int coinCurrentCol = 0;
    private final Random random;
    private final Handler obsHandler;
    private final Handler obs1Handler;
    private final Handler moveViewsHandler;
    private final Handler coinHandler;
    private Runnable obsRunnable;
    private Runnable obs1Runnable;
    private Runnable moveViewsRunnable;
    private Runnable coinRunnable;
    private final GameManager gameManager;

    public Game(Context context, RelativeLayout[][] cells, AppCompatImageView[] obstacles_IMG_hearts, AppCompatImageView obstacles_IMG_car,
                AppCompatImageView obstacle, AppCompatImageView obstacle1, AppCompatImageView coin, TextView score) {
        gameManager = new GameManager(context);
        this.cells = cells;
        this.obstacles_IMG_hearts = obstacles_IMG_hearts;
        this.obstacles_IMG_car = obstacles_IMG_car;
        this.obstacle = obstacle;
        this.obstacle1 = obstacle1;
        this.coin = coin;
        this.score = score;

        random = new Random();
        obsHandler = new Handler();
        obs1Handler = new Handler();
        moveViewsHandler = new Handler();
        coinHandler = new Handler();

    }

    public void startGame() {
        obsRunnable = new Runnable() {
            @Override
            public void run() {
                randomObs();
                obsHandler.postDelayed(this, 4000);
            }
        };
        obs1Runnable = new Runnable() {
            @Override
            public void run() {
                randomObs1();
                obs1Handler.postDelayed(this, 3800);
            }
        };
        moveViewsRunnable = new Runnable() {
            @Override
            public void run() {
                moveObs();
                moveObs1();
                moveCoin();
                checkCollision();
                moveViewsHandler.postDelayed(this, 900);
            }
        };
        coinRunnable = new Runnable() {
            @Override
            public void run() {
                randomCoin();
                coinHandler.postDelayed(this, 4200);
            }
        };

        obsHandler.post(obsRunnable);
        obs1Handler.post(obs1Runnable);
        moveViewsHandler.post(moveViewsRunnable);
        coinHandler.post(coinRunnable);
    }
    public void stopGame() {
        obsHandler.removeCallbacks(obsRunnable);
        obs1Handler.removeCallbacks(obs1Runnable);
        moveViewsHandler.removeCallbacks(moveViewsRunnable);
        coinHandler.removeCallbacks(coinRunnable);
        finish();

    }

    public void moveRight() {
        if (currentCarLane < gameManager.getCOLS()-1) {
            currentCarLane++;
            moveCar();
        }
    }

    public void moveLeft() {
        if (currentCarLane > 0) {
            currentCarLane--;
            moveCar();
        }
    }

    private void moveCar() {
        switch (currentCarLane) {
            case 0:
                removeView(gameManager.getROWS()-1, 1, obstacles_IMG_car);
                removeView(gameManager.getROWS()-1, 2, obstacles_IMG_car);
                removeView(gameManager.getROWS()-1, 3, obstacles_IMG_car);
                removeView(gameManager.getROWS()-1, 4, obstacles_IMG_car);
                addView(gameManager.getROWS()-1, 0, obstacles_IMG_car);
                break;
            case 1:
                removeView(gameManager.getROWS()-1, 0, obstacles_IMG_car);
                removeView(gameManager.getROWS()-1, 2, obstacles_IMG_car);
                removeView(gameManager.getROWS()-1, 3, obstacles_IMG_car);
                removeView(gameManager.getROWS()-1, 4, obstacles_IMG_car);
                addView(gameManager.getROWS()-1, 1, obstacles_IMG_car);
                break;
            case 2:
                removeView(gameManager.getROWS()-1, 1, obstacles_IMG_car);
                removeView(gameManager.getROWS()-1, 0, obstacles_IMG_car);
                removeView(gameManager.getROWS()-1, 3, obstacles_IMG_car);
                removeView(gameManager.getROWS()-1, 4, obstacles_IMG_car);
                addView(gameManager.getROWS()-1, 2, obstacles_IMG_car);
                break;
            case 3:
                removeView(gameManager.getROWS()-1, 1, obstacles_IMG_car);
                removeView(gameManager.getROWS()-1, 2, obstacles_IMG_car);
                removeView(gameManager.getROWS()-1, 0, obstacles_IMG_car);
                removeView(gameManager.getROWS()-1, 4, obstacles_IMG_car);
                addView(gameManager.getROWS()-1, 3, obstacles_IMG_car);
                break;
            case 4:
                removeView(gameManager.getROWS()-1, 1, obstacles_IMG_car);
                removeView(gameManager.getROWS()-1, 2, obstacles_IMG_car);
                removeView(gameManager.getROWS()-1, 3, obstacles_IMG_car);
                removeView(gameManager.getROWS()-1, 0, obstacles_IMG_car);
                addView(gameManager.getROWS()-1, 4, obstacles_IMG_car);
                break;
        }
        checkCollision();
    }

    private void moveObs(){
        removeView(obsCurrentRow, obsCurrentCol, obstacle);
        if (obsCurrentRow < gameManager.getROWS()-1) {
            obsCurrentRow++;
        } else {
            obsCurrentRow = 0;
        }
        addView(obsCurrentRow, obsCurrentCol, obstacle);

    }

    private void moveObs1(){
        removeView(obs1CurrentRow, obs1CurrentCol, obstacle1);
        if (obs1CurrentRow < gameManager.getROWS()-1) {
            obs1CurrentRow++;
        } else {
            obs1CurrentRow = 0;
        }
        addView(obs1CurrentRow, obs1CurrentCol, obstacle1);

    }

    private void moveCoin() {
        removeView(coinCurrentRow, coinCurrentCol, coin);

        if (coinCurrentRow < gameManager.getROWS()-1) {
            coinCurrentRow++;
        } else {
            coinCurrentRow = 0;
        }
        addView(coinCurrentRow, coinCurrentCol, coin);

    }

    private void checkCollision() {
        if (currentCarLane == coinCurrentCol && coinCurrentRow == gameManager.getROWS()-1) {
            gameManager.addCoin();
            gameManager.makeCoinSound();
            updateScoreUI();
        }
        if ((currentCarLane == obsCurrentCol && obsCurrentRow == gameManager.getROWS()-1) || (currentCarLane == obs1CurrentCol && obs1CurrentRow == gameManager.getROWS()-1)) {
            gameManager.decreaseLive();
            gameManager.vibrate();
            gameManager.makeCrashSound();
            updateLivesUI();
            if (gameManager.getLives() <= 0) {
                // Game Over
                stopGame();
                // Show game over message
                gameManager.lose();
                gameManager.saveData();
            }
        }
    }

    private void randomObs(){
        removeView(obsCurrentRow, obsCurrentCol, obstacle);
        int row = 0;
        int col = random.nextInt(gameManager.getCOLS());

        obsCurrentRow = row;
        obsCurrentCol = col;
        addView(obsCurrentRow, obsCurrentCol, obstacle);
    }
    private void randomObs1(){
        removeView(obs1CurrentRow, obs1CurrentCol, obstacle1);
        int row = 0;
        int col = random.nextInt(gameManager.getCOLS());

        // Ensure col1 is different from obsCurrentCol
        while (col == obsCurrentCol) {
            col = random.nextInt(gameManager.getCOLS());
        }

        obs1CurrentRow = row;
        obs1CurrentCol = col;
        addView(obs1CurrentRow, obs1CurrentCol, obstacle1);
    }

    private void randomCoin() {
        removeView(coinCurrentRow, coinCurrentCol, coin);

        int row = 0;
        int col = random.nextInt(gameManager.getCOLS());
        while (col == obsCurrentCol || col == obs1CurrentCol) {
            col = random.nextInt(gameManager.getCOLS());
        }

        coinCurrentRow = row;
        coinCurrentCol = col;
        addView(coinCurrentRow, coinCurrentCol, coin);
    }

    private void updateLivesUI() {
        int SZ = obstacles_IMG_hearts.length;

        for (AppCompatImageView obstaclesImgHeart : obstacles_IMG_hearts) {
            obstaclesImgHeart.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < SZ - gameManager.getLives(); i++) {
            obstacles_IMG_hearts[SZ - i - 1].setVisibility(View.INVISIBLE);

        }


    }

    private void removeView(int row, int col, AppCompatImageView view) {
        cells[row][col].removeView(view);
    }

    private void addView(int row, int col, AppCompatImageView view) {

        cells[row][col].addView(view);
    }

    private void updateScoreUI(){
        score.setText("" + gameManager.getCoins());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GameManager.REQUEST_LOCATION_PERMISSION) {
            gameManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
