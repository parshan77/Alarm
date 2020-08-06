package com.h3flagup.puzzlealarm.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.h3flagup.puzzlealarm.R;
import com.h3flagup.puzzlealarm.fragments.MainFragment;
import com.h3flagup.puzzlealarm.helpers.DbHelper;

public class MainActivity extends AppCompatActivity {

    public static DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        dbHelper = new DbHelper(this);

        // TODO: 8/6/20 inja bayad listeneresho bezarim dg?

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }

    }
}