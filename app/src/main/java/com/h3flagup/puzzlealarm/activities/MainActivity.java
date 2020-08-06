package com.h3flagup.puzzlealarm.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.h3flagup.puzzlealarm.R;
import com.h3flagup.puzzlealarm.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private Button newAlarmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // TODO: 8/6/20 inja bayad listeneresho bezarim dg?
        newAlarmButton = findViewById(R.id.newAlarmButton);
        newAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(TAG, "onClick: opening second page activity");

                Intent intent = new Intent(getApplicationContext(), SetAlarmActivity.class);
                intent.putExtra("SetAlarm", true);
                getApplicationContext().startActivity(intent);

                // TODO: 8/6/20 debug
            }
        });


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }

    }
}