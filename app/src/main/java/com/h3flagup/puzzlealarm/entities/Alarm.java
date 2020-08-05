package com.h3flagup.puzzlealarm.entities;

public class Alarm {
    private int hour, minute;
    private boolean[] days = {false, true, true, true, false, false, true};

    public boolean[] getDays() {
        return days;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public Alarm(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }
}
