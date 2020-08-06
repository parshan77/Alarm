package com.h3flagup.puzzlealarm.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.h3flagup.puzzlealarm.AlarmAdapter;
import com.h3flagup.puzzlealarm.R;
import com.h3flagup.puzzlealarm.entities.AlarmModel;

import java.util.Arrays;
import java.util.List;

public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static List<AlarmModel> myDataset = Arrays.asList(new AlarmModel(2, 4),
            new AlarmModel(22, 43),
            new AlarmModel(21, 41),
            new AlarmModel(4, 55));

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
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new AlarmAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
    }
}