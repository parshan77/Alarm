package com.h3flagup.puzzlealarm.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class AlarmService extends Service {


    private static final String TAG = "AlarmService";

    private int hour, minute;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private int pendingIntentRequestCode = 0;
    private int notificationId = 0;

    // TODO: 4/29/20 move these to res package
    private static final int SNOOZING_INTERVAL_IN_MINUTES = 10;
    private static final String alarmSetNotificationChannelId = "SetNotification";
    private static final String alarmSetNotificationTitle = "Alarm Service";


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: service started, fetching data from intent");
        if (intent == null)
        {
            Log.w(TAG, "onStartCommand: Null Intent!");
            return super.onStartCommand(intent, flags, startId);
        }
        hour = intent.getIntExtra("hour", 8);
        minute = intent.getIntExtra("minute", 30);

        new Thread(new Runnable() {
            @Override
            public void run() {
//                createNotificationChannel();
//                setupAlarm();
//                showAlarmNotification();
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
