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
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

interface ProximityListener {
    void checkProximity();
}

public class ProximitySensor extends Service implements SensorEventListener {
    private SensorManager senSensorManager;
    private Sensor senProximity;

    private static List<ProximityListener> listeners = new ArrayList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senProximity = senSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        senSensorManager.registerListener(this, senProximity , SensorManager.SENSOR_DELAY_NORMAL);
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
        if(event.sensor.getType() ==  Sensor.TYPE_PROXIMITY){
            //proximity sensor
            float distance = event.values[0];
            //Toast.makeText(this, "proximity "+distance,Toast.LENGTH_LONG).show();
            if(distance > 25){
                // do stuff
            }else{
                // do other stuff
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public static void addListener( ProximityListener listener ) {
        listeners.add(listener);
    }

    protected static void notifyProximityListeners () {
        // Notify each of the listeners in the list of registered listeners
        for(int i = 0; i < listeners.size(); i++) {
            ProximityListener listener = listeners.get(i);
            listener.checkProximity();
        }
    }
}
