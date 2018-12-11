package com.example.mikhailefroimson.cpe556_groupproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.StrictMode;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class AlarmReceiver extends BroadcastReceiver implements AccelerometerListener, RecognitionListener
{
    private static final String WAKEWORD_SEARCH = "WAKEWORD_SEARCH";

    private Vibrator mVibrator;

    Context context;
    Uri alarmUri;
    Ringtone ringtone;
    SpeechRecognizer mRecognizer;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        this.context = context;
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();
        alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();
        Accelerometer.addListener(this);
        setup_voide_recognition();

        StrictMode.allowThreadDiskReads();
    }

    @Override
    public void listenForShake() {
        ringtone.stop();
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d("AlarmReceiver: ", "onBeginningOfSpeech: ");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d("AlarmReceiver: ", "onEndOfSpeech: ");
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        Log.d("AlarmReceiver: ", "onPartialResult: ");
        if (hypothesis != null) {
            final String text = hypothesis.getHypstr();
            Log.d("AlarmReceiver: ", "onPartialResult: text = " + text);
            if (text.contains(context.getString(R.string.wake_word))) {
                mVibrator.vibrate(100);
                ringtone.stop();
                mRecognizer.removeListener(this);
                mRecognizer.cancel();
                mRecognizer.shutdown();
            }
        }
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        Log.d("AlarmReceiver: ", "onResult: ");
    }

    @Override
    public void onError(Exception e) {
        Log.d("AlarmReceiver: ", "onError: ");
    }

    @Override
    public void onTimeout() {
        Log.d("AlarmReceiver: ", "onTimeout: ");
    }

    private void setup_voide_recognition() {
        try {
            final Assets assets = new Assets(context);
            final File assetDir = assets.syncAssets();
            mRecognizer = SpeechRecognizerSetup.defaultSetup()
                    .setAcousticModel(new File(assetDir, "models/en-us-ptm"))
                    .setDictionary(new File(assetDir, "models/lm/words.dic"))
                    .setKeywordThreshold(Float.valueOf("1.e-" + 20))
                    .getRecognizer();
            mRecognizer.addKeyphraseSearch(WAKEWORD_SEARCH, context.getString(R.string.wake_word));
            mRecognizer.addListener(this);
            mRecognizer.startListening(WAKEWORD_SEARCH);
            Log.d("AlarmReceiver: ", "... listening");
        } catch (IOException e) {
            Log.e("AlarmReceiver: ", e.toString());
        }
    }
}
