package com.h3flagup.puzzlealarm.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.h3flagup.puzzlealarm.activities.AlarmActivity;

public class AlarmReceiver extends BroadcastReceiver {

    private static String TAG = "AlarmReceiver";


    // constants:
    public static final String uriNameInIntent = "songUriString";
    public static final String questionsNumIntentName = "questionsNum";
    public static final int questionsNumDefault = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive called in broadcast receiver");

        Intent alarmActivityIntent = new Intent(context, AlarmActivity.class);
        alarmActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String songUri = intent.getStringExtra(uriNameInIntent);
        int questionsNum = intent.getIntExtra(questionsNumIntentName, questionsNumDefault);

        alarmActivityIntent.putExtra(uriNameInIntent, songUri);
        alarmActivityIntent.putExtra(questionsNumIntentName, questionsNum);
        context.startActivity(alarmActivityIntent);

        // TODO: 8/6/20 debug!
    }
}
