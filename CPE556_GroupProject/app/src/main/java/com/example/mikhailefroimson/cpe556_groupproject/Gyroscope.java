package com.example.mikhailefroimson.cpe556_groupproject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

interface GyroscopeListener {
    void checkOrientation();
}

public class Gyroscope extends Service implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senGyroscope;

    private static List<GyroscopeListener> listeners = new ArrayList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senGyroscope = senSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        senSensorManager.registerListener(this, senGyroscope , SensorManager.SENSOR_DELAY_NORMAL);
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
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public static void addListener( GyroscopeListener listener ) {
        listeners.add(listener);
    }

    protected static void notifyOrientationListeners () {
        // Notify each of the listeners in the list of registered listeners
        for(int i = 0; i < listeners.size(); i++) {
            GyroscopeListener listener = listeners.get(i);
            listener.checkOrientation();
        }
    }
}
