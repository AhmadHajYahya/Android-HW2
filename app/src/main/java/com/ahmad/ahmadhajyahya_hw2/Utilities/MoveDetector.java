package com.ahmad.ahmadhajyahya_hw2.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.ahmad.ahmadhajyahya_hw2.interfaces.MoveCallBack;

public class MoveDetector {
    private SensorManager sensorManager;
    private Sensor sensor;

    private SensorEventListener sensorEventListener;

    private String direction = "";
    private long timestamp = 0l;
    private MoveCallBack moveCallback;

    public MoveDetector(Context context , MoveCallBack moveCallback) {
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.moveCallback = moveCallback;
        initEventListener();
    }
    public String getDirection() {
        return direction;
    }
    private void initEventListener(){
        this.sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                calculateMove(x);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // pass
            }
        };
    }


    private void calculateMove(float x) {
        if (System.currentTimeMillis() - timestamp > 500){
            timestamp = System.currentTimeMillis();
            if (x > 0.5 ){
                direction = "right";
                if (moveCallback != null){
                    moveCallback.moveX();
                }
            }
            if (x < -0.5){
                direction = "left";
                if (moveCallback != null){
                    moveCallback.moveX();
                }
            }
        }
    }
    public void start(){
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop(){
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }
}
