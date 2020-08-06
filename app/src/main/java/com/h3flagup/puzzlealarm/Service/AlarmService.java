package com.h3flagup.puzzlealarm.Service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.h3flagup.puzzlealarm.R;

import java.util.Calendar;

public class AlarmService extends Service {
    private static final String TAG = "AlarmService";

    private int hour, minute, alarmId, lastAlarmsHour, lastAlarmsMinute;
    private boolean[] days;
    private String songUri;
    private int questionsNum;

    private int pendingIntentRequestCode;
    private int notificationId = 0;         //unique notification ids used for notifying the alarm is set

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;


    // constants:
    public static final String commandNameInIntent = "Command";
    public static final String createAlarmCommand = "CreateAlarm";
    public static final String editAlarmCommand = "EditAlarm";
    public static final String deleteAlarmCommand = "DeleteAlarm";


    public static final int SNOOZING_INTERVAL_IN_MINUTES = 10;
    public static final String alarmSetNotificationChannelId = "SetNotification";
    public static final String alarmSetNotificationTitle = "Alarm Service";

    public static final String daysNameInIntent = "daysOfTheWeek";
    public static final String hourNameInIntent = "hour";
    public static final String minuteNameInIntent = "minute";
    public static final String alarmIdNameInIntent = "alarmId";
    public static final String pendingIntentRequestCodeName = "PendingIntentRequestCode";


    public static int PENDING_INTENT_REQUEST_CODE_DEFAULT_VALUE = 0;
    public static int HOUR_DEFAULT_VALUE = 8;
    public static int MINUTE_DEFAULT_VALUE = 30;
    public static int ALARMID_DEFAULT_VALUE = 0;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: service started, fetching data from intent");

        if (intent == null) {
            Log.w(TAG, "onStartCommand: Null Intent!");
            return super.onStartCommand(intent, flags, startId);
        }

        String cmd = intent.getStringExtra(commandNameInIntent);


        hour = intent.getIntExtra(hourNameInIntent, HOUR_DEFAULT_VALUE);
        minute = intent.getIntExtra(minuteNameInIntent, MINUTE_DEFAULT_VALUE);
        alarmId = intent.getIntExtra(alarmIdNameInIntent, ALARMID_DEFAULT_VALUE);
        questionsNum = intent.getIntExtra(AlarmReceiver.questionsNumIntentName, AlarmReceiver.questionsNumDefault);
        pendingIntentRequestCode = intent.getIntExtra(pendingIntentRequestCodeName, PENDING_INTENT_REQUEST_CODE_DEFAULT_VALUE);
        days = intent.getBooleanArrayExtra(daysNameInIntent);

        songUri = intent.getStringExtra(AlarmReceiver.uriNameInIntent);

        switch (cmd) {
            case createAlarmCommand:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setupAlarm();
                        createNotificationChannel();
                        showAlarmNotification();
                    }
                }).start();
                break;

            case editAlarmCommand:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        editAlarm();
                    }
                }).start();

                break;

            case deleteAlarmCommand:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        cancelAlarm();
                    }
                }).start();
                break;

            default:
                Log.i(TAG, "onStartCommand: unsupported command!");
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void editAlarm() {
        cancelAlarm();
        setupAlarm();
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.uriNameInIntent, songUri);
        intent.putExtra(AlarmReceiver.questionsNumIntentName, questionsNum);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, pendingIntentRequestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    private void setupAlarm() {
        Log.i(TAG, "setupAlarm called");

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.uriNameInIntent, songUri);
        intent.putExtra(AlarmReceiver.questionsNumIntentName, questionsNum);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, pendingIntentRequestCode, intent, 0);

        Calendar calendar = Calendar.getInstance();

        int today = calendar.get(Calendar.DAY_OF_WEEK);
        int convertedDay = today % 7;
        if (!days[convertedDay]){
            return;
        }

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        if (calendar.before(Calendar.getInstance()))
            calendar.add(Calendar.DATE, 1);


        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


    private void showAlarmNotification() {

        Log.i(TAG, "showAlarmSetNotification: notification is pushed");

        String msg;
        if (minute < 10)
            msg = "An alarm is set for " + hour + ":" + "0" + minute;
        else
            msg = "An alarm is set for " + hour + ":" + minute;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), alarmSetNotificationChannelId)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(alarmSetNotificationTitle)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        Notification notification = builder.build();
        notificationManagerCompat.notify(notificationId, notification);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence channelName = getString(R.string.alarmNotificationChannelName);
            String channelDescription = getString(R.string.alarmNotificationChannelDescription);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(alarmSetNotificationChannelId, channelName, importance);
            channel.setDescription(channelDescription);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
