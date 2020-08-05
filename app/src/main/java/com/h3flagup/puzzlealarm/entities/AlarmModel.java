package com.h3flagup.puzzlealarm.entities;

import java.util.Arrays;

public class AlarmModel {
    private int hour, minute, day, month;
    private boolean isActive, isSnoozed;
    private boolean[] days = {false, true, true, true, false, false, true};

    public AlarmModel(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public AlarmModel(int hour, int minute, int day, int month, boolean isActive, boolean isSnoozed, boolean[] days) {
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.month = month;
        this.isActive = isActive;
        this.isSnoozed = isSnoozed;
        this.days = days;
    }

    @Override
    public String toString() {
        return "AlarmModel{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", day=" + day +
                ", month=" + month +
                ", isActive=" + isActive +
                ", isSnoozed=" + isSnoozed +
                ", days=" + Arrays.toString(days) +
                '}';
    }

}