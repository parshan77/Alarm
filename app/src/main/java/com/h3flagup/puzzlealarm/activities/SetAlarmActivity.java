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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.h3flagup.puzzlealarm.R;
import com.h3flagup.puzzlealarm.Service.AlarmReceiver;
import com.h3flagup.puzzlealarm.Service.AlarmService;
import com.h3flagup.puzzlealarm.fragments.TimePicker;

public class SetAlarmActivity extends AppCompatActivity {

    private String TAG = "SetAlarmActivity";

    // Sound variables
    private LinearLayout sound_picker;
    private TextView alarm_sound;

    // time picker variables
    private TextView timeTextView;
    private TextView timeLabel;
    private String timePickerTag = "TimePicker";
    private Button setAlarmButton;

    public static final String isEditedNameInIntent = "isEdited";
    public static final boolean IS_EDITED_DEFAULT_VALUE = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);


        // TODO: 8/6/20 inaro bayad bedim be intent
        final int alarmId = getIntent().getIntExtra(AlarmService.alarmIdNameInIntent, AlarmService.ALARMID_DEFAULT_VALUE);
        final boolean isEdited = getIntent().getBooleanExtra(isEditedNameInIntent, IS_EDITED_DEFAULT_VALUE);
        final int pendingIntentRequestCode = getIntent().getIntExtra(AlarmService.pendingIntentRequestCodeName, AlarmService.PENDING_INTENT_REQUEST_CODE_DEFAULT_VALUE);
        final int questionsNum = 3;     // TODO: 8/6/20
        final String soundUri = "sa";      // TODO: 8/6/20


        setAlarmButton = findViewById(R.id.setAlarmButton);
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] time = timeTextView.getText().toString().split(":");
                int hour = Integer.parseInt(time[0]), minute = Integer.parseInt(time[1]);


                Intent alarmIntent = new Intent(getApplicationContext(), AlarmService.class);

                alarmIntent.putExtra(AlarmService.hourNameInIntent, hour);
                alarmIntent.putExtra(AlarmService.minuteNameInIntent, minute);
                alarmIntent.putExtra(AlarmService.alarmIdNameInIntent, alarmId);
                alarmIntent.putExtra(AlarmReceiver.questionsNumIntentName, questionsNum);
                alarmIntent.putExtra(AlarmReceiver.uriNameInIntent, soundUri);

                if (!isEdited) {
                    Log.i(TAG, "setUpAlarm: getting information from alarmTimeTextView");

                    alarmIntent.putExtra(AlarmService.commandNameInIntent, AlarmService.createAlarmCommand);

                    getApplicationContext().startService(alarmIntent);
                }else{
                    Log.i(TAG, "onClick: editing alarm");

                    alarmIntent.putExtra(AlarmService.commandNameInIntent, AlarmService.editAlarmCommand);

                    getApplicationContext().startService(alarmIntent);
                }
            }
        });

        // viewing selected time:
        timeTextView = findViewById(R.id.timeText);
        timeLabel = findViewById(R.id.timeLabel);
        timeLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: opening time picker");

                DialogFragment timePickerFragment = new TimePicker(getApplicationContext().getMainLooper(), timeTextView);
                timePickerFragment.show(getSupportFragmentManager(), timePickerTag);
                // TODO: 8/6/20 debug!
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