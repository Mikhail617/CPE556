package com.example.mikhailefroimson.cpe556_groupproject;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class Accelerometer extends Service implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //add this line only
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        Log.w("Accelerometer","sensor changed!");
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            Log.w("Accelerometer", "x = " + x + ", y = " + y + ", z = " + z + ".");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
