package com.h3flagup.puzzlealarm.entities;

import android.media.RingtoneManager;
import android.net.Uri;

import java.net.URI;
import java.util.Arrays;

public class AlarmModel {
    private int hour;
    private int minute;
    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    private int requestCode;
    long alarmId;
    private boolean isActive, isSnoozed;
    private Uri defaultUri;
    private boolean[] days = {false, false, false, false, false, false, false};

    public AlarmModel(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
        isActive = false;
        isSnoozed = false;
        requestCode = 0;
        defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    }

    @Override
    public String toString() {
        return "AlarmModel{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", isActive=" + isActive +
                ", isSnoozed=" + isSnoozed +
                ", days=" + Arrays.toString(days) +
                '}';
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public AlarmModel(int hour, int minute, int requestCode, long alarmId, boolean isActive, boolean isSnoozed, Uri defaultUri, boolean[] days) {
        this.hour = hour;
        this.minute = minute;
        this.requestCode = requestCode;
        this.alarmId = alarmId;
        this.isActive = isActive;
        this.isSnoozed = isSnoozed;
        this.defaultUri = defaultUri;
        this.days = days;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isSnoozed() {
        return isSnoozed;
    }

    public void setSnoozed(boolean snoozed) {
        isSnoozed = snoozed;
    }

    public boolean[] getDays() {
        return days;
    }

    public void setDefaultUri(Uri defaultUri) {
        this.defaultUri = defaultUri;
    }

    public void setAlarmId(long alarmId) {
        this.alarmId = alarmId;
    }

    public long getAlarmId() {
        return alarmId;
    }

    public Uri getDefaultUri() {
        return defaultUri;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }

    public String getTime()
    {
        return String.format("%02d:%02d", hour, minute);
    }
}