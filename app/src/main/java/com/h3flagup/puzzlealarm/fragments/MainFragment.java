package com.h3flagup.puzzlealarm.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.h3flagup.puzzlealarm.AlarmAdapter;
import com.h3flagup.puzzlealarm.R;
import com.h3flagup.puzzlealarm.Service.AlarmService;
import com.h3flagup.puzzlealarm.activities.MainActivity;
import com.h3flagup.puzzlealarm.activities.SetAlarmActivity;
import com.h3flagup.puzzlealarm.entities.AlarmModel;
import com.h3flagup.puzzlealarm.helpers.DbHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainFragment extends Fragment {
    private String TAG = "MainFragment";

    private RecyclerView recyclerView;
    public static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button button;
    DbHelper dbHelper;
    public static ArrayList<AlarmModel> myDataset = new ArrayList<>(Arrays.asList(new AlarmModel(2, 4),
            new AlarmModel(22, 43),
            new AlarmModel(21, 41),
            new AlarmModel(4, 55)));

    private Button newAlarmButton;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) getView().findViewById(R.id.alarm_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        dbHelper = MainActivity.dbHelper;
        myDataset = dbHelper.getAllAlarms();
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AlarmAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);

        button = (Button) getView().findViewById(R.id.newAlarmButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(TAG, "onClick: opening second page activity");
                AlarmModel newAlarm = new AlarmModel(0, 0);
                myDataset.add(newAlarm);
                dbHelper.addAlarm(newAlarm);
                mAdapter.notifyItemInserted(myDataset.size() - 1);
                recyclerView.scrollToPosition(myDataset.size() - 1);

              /*  Intent intent = new Intent(getView().getContext(), SetAlarmActivity.class);

                Long tsLong = System.currentTimeMillis()/1000;
                int alarmId = (int)(tsLong % 2000000000);
                int pendingReqCode = alarmId;
                intent.putExtra("Index", myDataset.size() - 1);
                intent.putExtra(AlarmService.alarmIdNameInIntent, alarmId);
                intent.putExtra(SetAlarmActivity.isEditedNameInIntent, false);
                intent.putExtra(AlarmService.pendingIntentRequestCodeName, pendingReqCode);

                getView().getContext().startActivity(intent);*/
                // TODO: 8/6/20 debug
            }
        });

    }
}