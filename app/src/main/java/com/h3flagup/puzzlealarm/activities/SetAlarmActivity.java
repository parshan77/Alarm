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
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.h3flagup.puzzlealarm.R;
import com.h3flagup.puzzlealarm.Service.AlarmReceiver;
import com.h3flagup.puzzlealarm.Service.AlarmService;
import com.h3flagup.puzzlealarm.entities.AlarmModel;
import com.h3flagup.puzzlealarm.fragments.MainFragment;
import com.h3flagup.puzzlealarm.fragments.TimePicker;

public class SetAlarmActivity extends AppCompatActivity {

    private String TAG = "SetAlarmActivity";

    // Sound variables
    private LinearLayout sound_picker;
    private TextView alarm_sound;

    // time picker variables
    private TextView timeTextView;
    private TextView timeLabel;
    private LinearLayout timePick;
    private String timePickerTag = "TimePicker";
    private Button setAlarmButton;
    private Button removeAlarmButton;
    private Uri selectedUri;
    private CheckBox sat, sun, mon, tue, wed, thu, fri;

    public static final String isEditedNameInIntent = "isEdited";
    public static final boolean IS_EDITED_DEFAULT_VALUE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);


        // TODO: 8/6/20 inaro bayad bedim be intent
        final int alarmIndex = getIntent().getIntExtra("Index", 0);
        final int alarmId = getIntent().getIntExtra(AlarmService.alarmIdNameInIntent, AlarmService.ALARMID_DEFAULT_VALUE);
        final boolean isEdited = getIntent().getBooleanExtra(isEditedNameInIntent, IS_EDITED_DEFAULT_VALUE);
        final int pendingIntentRequestCode = getIntent().getIntExtra(AlarmService.pendingIntentRequestCodeName, AlarmService.PENDING_INTENT_REQUEST_CODE_DEFAULT_VALUE);
        final int questionsNum = 3;     // TODO: 8/6/20
        final String soundUri = "sa";      // TODO: 8/6/20

        final AlarmModel myAlarm = MainFragment.myDataset.get(alarmIndex);
        sat = (CheckBox)findViewById(R.id.satCheck);
        sun = (CheckBox)findViewById(R.id.sunCheck);
        mon = (CheckBox)findViewById(R.id.monCheck);
        tue = (CheckBox)findViewById(R.id.tueCheck);
        wed = (CheckBox)findViewById(R.id.wedCheck);
        thu = (CheckBox)findViewById(R.id.thuCheck);
        fri = (CheckBox)findViewById(R.id.friCheck);

        sat.setChecked(myAlarm.getDays()[0]);
        sun.setChecked(myAlarm.getDays()[1]);
        mon.setChecked(myAlarm.getDays()[2]);
        tue.setChecked(myAlarm.getDays()[3]);
        wed.setChecked(myAlarm.getDays()[4]);
        thu.setChecked(myAlarm.getDays()[5]);
        fri.setChecked(myAlarm.getDays()[6]);
        setAlarmButton = findViewById(R.id.setAlarmButton);
        removeAlarmButton = findViewById(R.id.removeAlarmButton);

        removeAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainFragment.myDataset.remove(alarmIndex);
                MainActivity.dbHelper.removeAlarm(myAlarm);
                MainFragment.mAdapter.notifyItemRemoved(alarmIndex);
                finish();
            }
        });
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] time = timeTextView.getText().toString().split(":");
                int hour = Integer.parseInt(time[0]), minute = Integer.parseInt(time[1]);
                boolean[] days = {
                        sat.isChecked(),
                        sun.isChecked(),
                        mon.isChecked(),
                        tue.isChecked(),
                        wed.isChecked(),
                        thu.isChecked(),
                        fri.isChecked()
                };
                myAlarm.setHour(hour);
                myAlarm.setMinute(minute);
                myAlarm.setDays(days);
                if (selectedUri != null)
                    myAlarm.setDefaultUri(selectedUri);
                MainActivity.dbHelper.updateAlarm(myAlarm);
                MainFragment.myDataset.set(alarmIndex, myAlarm);
                MainFragment.mAdapter.notifyItemChanged(alarmIndex);
                finish();
                /*Intent alarmIntent = new Intent(getApplicationContext(), AlarmService.class);

                alarmIntent.putExtra(AlarmService.hourNameInIntent, hour);
                alarmIntent.putExtra(AlarmService.minuteNameInIntent, minute);
                alarmIntent.putExtra(AlarmService.alarmIdNameInIntent, alarmId);
                alarmIntent.putExtra(AlarmReceiver.questionsNumIntentName, questionsNum);
                alarmIntent.putExtra(AlarmReceiver.uriNameInIntent, soundUri);

                if (!isEdited) {
                    Log.i(TAG, "setUpAlarm: getting information from alarmTimeTextView");

                    alarmIntent.putExtra(AlarmService.commandNameInIntent, AlarmService.createAlarmCommand);

                    getApplicationContext().startService(alarmIntent);
                } else {
                    Log.i(TAG, "onClick: editing alarm");

                    alarmIntent.putExtra(AlarmService.commandNameInIntent, AlarmService.editAlarmCommand);

                    getApplicationContext().startService(alarmIntent);
                }*/
            }
        });

        // viewing selected time:
        timeTextView = findViewById(R.id.timeText);
        timePick = findViewById(R.id.timePick);
        timeTextView.setText(myAlarm.getTime());
        timeLabel = findViewById(R.id.timeLabel);
        timePick.setOnClickListener(new View.OnClickListener() {
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
        alarm_sound.setText(RingtoneManager.getRingtone(this, myAlarm.getDefaultUri()).getTitle(this));
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
            selectedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (selectedUri != null) {
                this.alarm_sound.setText(RingtoneManager.getRingtone(this, selectedUri).getTitle(this));
            } else {
                selectedUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

                this.alarm_sound.setText("No sound");
            }
        }
    }
}