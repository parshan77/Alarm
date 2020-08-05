package com.h3flagup.puzzlealarm.fragments;



import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.Preference;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.h3flagup.puzzlealarm.R;

import java.util.Calendar;

public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private Handler handler;
    TextView timeText;
    public TimePicker(Looper looper, TextView textView) {
        this.handler = new Handler(looper);
        timeText = textView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //setting a default time for the time picker
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, final int hourOfDay, final int minute) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (minute < 10) {
                    timeText.setText(hourOfDay + ":0" + minute);
//                    timeText.setSummary(getString(R.string.alarmTimeTextFormatWithLeadingZero, hourOfDay, minute));
                } else {
                    timeText.setText(hourOfDay + ":" + minute);
//                    timeText.setSummary(getString(R.string.alarmTimeTextFormat, hourOfDay, minute));
                }
            }
        });
    }
}
