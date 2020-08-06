package com.h3flagup.puzzlealarm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.h3flagup.puzzlealarm.Service.AlarmReceiver;
import com.h3flagup.puzzlealarm.Service.AlarmService;
import com.h3flagup.puzzlealarm.activities.MainActivity;
import com.h3flagup.puzzlealarm.activities.SetAlarmActivity;
import com.h3flagup.puzzlealarm.entities.AlarmModel;
import com.h3flagup.puzzlealarm.fragments.MainFragment;

import java.net.URI;
import java.util.List;

import static android.graphics.Color.rgb;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View alarmView = inflater.inflate(R.layout.alarm_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(alarmView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final AlarmModel alarmModel = alarmModels.get(position);

        TextView time = holder.alarmTime;
        time.setText(alarmModel.getTime());
        holder.total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SetAlarmActivity.class);
                intent.putExtra("Index", position);

                intent.putExtra(AlarmService.commandNameInIntent, AlarmService.editAlarmCommand);
                view.getContext().startActivity(intent);
            }
        });
        holder.alarmSwitch.setChecked(alarmModel.isActive());
        holder.alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                alarmModel.setActive(b);
                MainActivity.dbHelper.updateAlarm(alarmModel);

                if (b) {

                    int alarmId = (int) alarmModel.getAlarmId();
                    int pendinReqConde = alarmId;
                    int hour = alarmModel.getHour(), minute = alarmModel.getMinute();
                    int questionsNum = 3;
                    Uri soundUri = alarmModel.getDefaultUri(); // TODO: 8/6/20


                    Intent alarmIntent = new Intent(context, AlarmService.class);

                    alarmIntent.putExtra(AlarmService.alarmIdNameInIntent, alarmId);
                    alarmIntent.putExtra(AlarmService.hourNameInIntent, hour);
                    alarmIntent.putExtra(AlarmService.minuteNameInIntent, minute);
                    alarmIntent.putExtra(AlarmReceiver.questionsNumIntentName, questionsNum);
                    alarmIntent.putExtra(AlarmReceiver.uriNameInIntent, soundUri);

                    alarmIntent.putExtra(AlarmService.pendingIntentRequestCodeName, pendinReqConde);
                    alarmIntent.putExtra(AlarmService.commandNameInIntent, AlarmService.createAlarmCommand);

                    context.startService(alarmIntent);
                } else {

                }

            }
        });
        for (int i = 0; i < 7; ++i)
        {
            if (alarmModel.getDays()[i])
                holder.days[i].setTextColor(rgb(255, 0, 0));
        }
    }

    @Override
    public int getItemCount() {
        return alarmModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView alarmTime;
        public TextView[] days = new TextView[7];
        public Switch alarmSwitch;
        public LinearLayout total;
        public ViewHolder(View itemView) {
            super(itemView);
            total = (LinearLayout) itemView.findViewById(R.id.total_alarm);
            alarmSwitch = (Switch) itemView.findViewById(R.id.alarmSwitch);
            alarmTime = (TextView) itemView.findViewById(R.id.alarmTime);
            days[0] = (TextView) itemView.findViewById(R.id.sat);
            days[1] = (TextView) itemView.findViewById(R.id.sun);
            days[2] = (TextView) itemView.findViewById(R.id.mon);
            days[3] = (TextView) itemView.findViewById(R.id.tue);
            days[4] = (TextView) itemView.findViewById(R.id.wed);
            days[5] = (TextView) itemView.findViewById(R.id.thu);
            days[6] = (TextView) itemView.findViewById(R.id.fri);
        }
    }
    private List<AlarmModel> alarmModels;

    public AlarmAdapter(List<AlarmModel> alarmModels) {
        this.alarmModels = alarmModels;
    }
}
