package com.ahmad.ahmadhajyahya_hw2;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MenuActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton sensorMode;
    private ExtendedFloatingActionButton buttonMode;
    private ExtendedFloatingActionButton recordsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViews();

        initScreens();
    }


    private void initScreens(){
        sensorMode.setOnClickListener((view)->{
            // Create an Intent to start MenuActivity
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("KEY_MESSAGE", "sensor_mode");
            intent.putExtras(bundle);
            // Start MenuActivity
            startActivity(intent);
        });

        buttonMode.setOnClickListener((view)->{
            // Create an Intent to start MenuActivity
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("KEY_MESSAGE", "button_mode");
            intent.putExtras(bundle);
            // Start MenuActivity
            startActivity(intent);
        });
    }

    private void findViews() {
        sensorMode = findViewById(R.id.obstacles_BTN_sensor_mode);
        buttonMode = findViewById(R.id.obstacles_BTN_button_mode);
        recordsButton = findViewById(R.id.obstacles_BTN_records);
    }
}