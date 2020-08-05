package com.h3flagup.puzzlealarm.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.h3flagup.puzzlealarm.R;
import com.h3flagup.puzzlealarm.fragments.TimePicker;

public class SetAlarmActivity extends AppCompatActivity {

    // Sound variables
    private LinearLayout sound_picker;
    private TextView alarm_sound;

    // time picker variables
    private TextView timeTextView;
    private TextView timeLabel;
    private String timePickerTag = "TimePicker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        // time:
        timeTextView = findViewById(R.id.timeText);
        timeLabel = findViewById(R.id.timeLabel);
        timeLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePickerFragment = new TimePicker(getApplicationContext().getMainLooper(), timeTextView);
            }
        });

        // sound:
        sound_picker = findViewById(R.id.sound_picker);
        alarm_sound = findViewById(R.id.alarm_sound);
        sound_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
                startActivityForResult(intent, 5);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 5) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (uri != null) {
                this.alarm_sound.setText(RingtoneManager.getRingtone(this, uri).getTitle(this));
            } else {
                this.alarm_sound.setText("No sound");
            }
        }
    }
}