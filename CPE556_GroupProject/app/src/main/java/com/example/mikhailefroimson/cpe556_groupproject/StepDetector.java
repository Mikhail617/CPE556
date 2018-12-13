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

interface StepDetectorListener {
    void checkStepcount();
}

public class StepDetector extends Service implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senStepDetector;

    private static List<StepDetectorListener> listeners = new ArrayList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senStepDetector = senSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        senSensorManager.registerListener(this, senStepDetector , SensorManager.SENSOR_DELAY_NORMAL);
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
        if(event.sensor.getType() ==  Sensor.TYPE_STEP_DETECTOR){
            //step counter
            float steps = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public static void addListener( StepDetectorListener listener ) {
        listeners.add(listener);
    }

    protected static void notifyProximityListeners () {
        // Notify each of the listeners in the list of registered listeners
        for(int i = 0; i < listeners.size(); i++) {
            StepDetectorListener listener = listeners.get(i);
            listener.checkStepcount();
        }
    }
}
