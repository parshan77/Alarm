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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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

    public void setDays(boolean[] days) {
        this.days = days;
    }
}