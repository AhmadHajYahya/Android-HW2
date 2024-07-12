package com.ahmad.ahmadhajyahya_hw2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.ahmad.ahmadhajyahya_hw2.Utilities.MoveDetector;
import com.ahmad.ahmadhajyahya_hw2.interfaces.MoveCallBack;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private AppCompatImageView[] obstacles_IMG_hearts;
    private AppCompatImageView obstacles_IMG_car;
    private AppCompatImageView obstacle;
    private AppCompatImageView obstacle1;
    private AppCompatImageView coin;
    private ExtendedFloatingActionButton main_BTN_exit;
    private MaterialButton obstacles_BTN_right;
    private MaterialButton obstacles_BTN_left;
    private TextView score;
    private RelativeLayout[][] cells;

    private Game game;

    private MoveDetector moveDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cells = new RelativeLayout[5][5];

        findViews();
        initViews();

        game = new Game(this,cells,obstacles_IMG_hearts,obstacles_IMG_car,obstacle,obstacle1,coin,score);

        initMoveDetector();
        initScreens();
        playMode();
        game.startGame();

    }


    private void initMoveDetector() {
        moveDetector = new MoveDetector(this,
                new MoveCallBack() {
                    @Override
                    public void moveX() {
                        // tilt left
                       if(moveDetector.getDirection().equalsIgnoreCase("left")){
                           MainActivity.this.game.moveLeft();
                       }
                       // tilt right
                       if(moveDetector.getDirection().equalsIgnoreCase("right")){
                           MainActivity.this.game.moveRight();
                       }

                    }

                });
    }

    private void initScreens(){
        main_BTN_exit.setOnClickListener((view)->{
            // Create an Intent to start MenuActivity
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            // Start MenuActivity
            startActivity(intent);
            finish();
        });
    }

    private void playMode(){
        Intent prev = getIntent();
        String mode = (prev.getExtras().getString("KEY_MESSAGE"));

        if(mode.equalsIgnoreCase("sensor_mode")){
            moveDetector.start();
            obstacles_BTN_right.setVisibility(View.GONE);
            obstacles_BTN_left.setVisibility(View.GONE);
        }
        else{
            obstacles_BTN_right.setVisibility(View.VISIBLE);
            obstacles_BTN_left.setVisibility(View.VISIBLE);
            moveDetector.stop();
            obstacles_BTN_right.setOnClickListener(v -> game.moveRight());
            obstacles_BTN_left.setOnClickListener(v -> game.moveLeft());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        moveDetector.stop();
        game.stopGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent prev = getIntent();
        String mode = prev.getStringExtra("KEY_MESSAGE");
        if (mode != null && mode.equalsIgnoreCase("sensor_mode")) {
            moveDetector.start();
        }
    }
    private void findViews() {
        obstacles_IMG_hearts = new AppCompatImageView[] {
                findViewById(R.id.obstacles_IMG_heart1),
                findViewById(R.id.obstacles_IMG_heart2),
                findViewById(R.id.obstacles_IMG_heart3),
        };

        score = findViewById(R.id.obstacles_TXT_score);
        main_BTN_exit = findViewById(R.id.main_BTN_exit);

        obstacles_BTN_right = findViewById(R.id.obstacles_BTN_right);
        obstacles_BTN_left = findViewById(R.id.obstacles_BTN_left);
        obstacles_BTN_right.setVisibility(View.GONE);
        obstacles_BTN_left.setVisibility(View.GONE);

        cells[0][0] = findViewById(R.id.cell_0_0);
        cells[0][1] = findViewById(R.id.cell_0_1);
        cells[0][2] = findViewById(R.id.cell_0_2);
        cells[0][3] = findViewById(R.id.cell_0_3);
        cells[0][4] = findViewById(R.id.cell_0_4);
        cells[1][0] = findViewById(R.id.cell_1_0);
        cells[1][1] = findViewById(R.id.cell_1_1);
        cells[1][2] = findViewById(R.id.cell_1_2);
        cells[1][3] = findViewById(R.id.cell_1_3);
        cells[1][4] = findViewById(R.id.cell_1_4);
        cells[2][0] = findViewById(R.id.cell_2_0);
        cells[2][1] = findViewById(R.id.cell_2_1);
        cells[2][2] = findViewById(R.id.cell_2_2);
        cells[2][3] = findViewById(R.id.cell_2_3);
        cells[2][4] = findViewById(R.id.cell_2_4);
        cells[3][0] = findViewById(R.id.cell_3_0);
        cells[3][1] = findViewById(R.id.cell_3_1);
        cells[3][2] = findViewById(R.id.cell_3_2);
        cells[3][3] = findViewById(R.id.cell_3_3);
        cells[3][4] = findViewById(R.id.cell_3_4);
        cells[4][0] = findViewById(R.id.cell_4_0);
        cells[4][1] = findViewById(R.id.cell_4_1);
        cells[4][2] = findViewById(R.id.cell_4_2);
        cells[4][3] = findViewById(R.id.cell_4_3);
        cells[4][4] = findViewById(R.id.cell_4_4);
    }

    private void initViews(){

        obstacles_IMG_car = new AppCompatImageView(this);
        obstacles_IMG_car.setImageResource(R.drawable.car);
        obstacles_IMG_car.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        cells[4][1].addView(obstacles_IMG_car);

        obstacle = new AppCompatImageView(this);
        obstacle.setImageResource(R.drawable.obstacle);
        obstacle.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        obstacle1 = new AppCompatImageView(this);
        obstacle1.setImageResource(R.drawable.obstacle);
        obstacle1.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        coin = new AppCompatImageView(this);
        coin.setImageResource(R.drawable.coin);
        coin.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}