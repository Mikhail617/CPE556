package com.example.mikhailefroimson.cpe556_groupproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver implements AccelerometerListener
{
    Context context;
    Uri alarmUri;
    Ringtone ringtone;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        this.context = context;
        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();
        alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();
        Accelerometer.addListener(this);
    }

    @Override
    public void listenForShake() {
        ringtone.stop();
    }
}
