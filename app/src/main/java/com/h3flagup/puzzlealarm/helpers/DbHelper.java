package com.h3flagup.puzzlealarm.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.core.app.AppLaunchChecker;

import com.h3flagup.puzzlealarm.entities.AlarmModel;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "alarms.db";
    public static final String ALARM_TABLE_NAME = "alarms";
    public static final String ALARM_COLUMN_ID = "id";
    public static final String ALARM_COLUMN_HOUR = "hour";
    public static final String ALARM_COLUMN_MINUTE = "minute";
    public static final String ALARM_COLUMN_IS_ACTIVE = "isActive";
    public static final String ALARM_COLUMN_IS_SNOOZED = "isSnoozed";
    public static final String ALARM_COLUMN_SAT = "sat";
    public static final String ALARM_COLUMN_SUN = "sun";
    public static final String ALARM_COLUMN_MON = "mon";
    public static final String ALARM_COLUMN_TUE = "tue";
    public static final String ALARM_COLUMN_WED = "wed";
    public static final String ALARM_COLUMN_THU = "thu";
    public static final String ALARM_COLUMN_FRI = "fri";
    public static final String ALARM_COLUMN_DEFAULT_URI = "uri";
    public static final String ALARM_COLUMN_REQUEST_CODE = "requestCode";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        getReadableDatabase(); // <-- add this, which triggers onCreate/onUpdate
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + ALARM_TABLE_NAME + "(" + ALARM_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ALARM_COLUMN_HOUR + " INT, " + ALARM_COLUMN_MINUTE + " INT, "
                + ALARM_COLUMN_IS_ACTIVE + " INT, "+ ALARM_COLUMN_IS_SNOOZED + " INT, " + ALARM_COLUMN_SAT + " INT, "+ ALARM_COLUMN_SUN + " INT, "
                + ALARM_COLUMN_MON + " INT, " + ALARM_COLUMN_TUE + " INT, " + ALARM_COLUMN_WED + " INT, " + ALARM_COLUMN_THU + " INT, "
                + ALARM_COLUMN_FRI + " INT, " + ALARM_COLUMN_DEFAULT_URI + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ALARM_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addAlarm(AlarmModel alarm)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ALARM_COLUMN_HOUR, alarm.getHour());
        cv.put(ALARM_COLUMN_MINUTE, alarm.getMinute());
        cv.put(ALARM_COLUMN_IS_ACTIVE, alarm.isActive());
        cv.put(ALARM_COLUMN_IS_SNOOZED, alarm.isSnoozed());
        cv.put(ALARM_COLUMN_SAT, alarm.getDays()[0]);
        cv.put(ALARM_COLUMN_SUN, alarm.getDays()[1]);
        cv.put(ALARM_COLUMN_MON, alarm.getDays()[2]);
        cv.put(ALARM_COLUMN_TUE, alarm.getDays()[3]);
        cv.put(ALARM_COLUMN_WED, alarm.getDays()[4]);
        cv.put(ALARM_COLUMN_THU, alarm.getDays()[5]);
        cv.put(ALARM_COLUMN_FRI, alarm.getDays()[6]);
        cv.put(ALARM_COLUMN_DEFAULT_URI, alarm.getDefaultUri().toString());
        cv.put(ALARM_COLUMN_REQUEST_CODE, alarm.getRequestCode());

        long id = db.insert(ALARM_TABLE_NAME, null, cv);
        alarm.setAlarmId(id);

        return true;
    }

    public boolean updateAlarm(AlarmModel alarm)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ALARM_COLUMN_HOUR, alarm.getHour());
        cv.put(ALARM_COLUMN_MINUTE, alarm.getMinute());
        cv.put(ALARM_COLUMN_IS_ACTIVE, alarm.isActive());
        cv.put(ALARM_COLUMN_IS_SNOOZED, alarm.isSnoozed());
        cv.put(ALARM_COLUMN_SAT, alarm.getDays()[0]);
        cv.put(ALARM_COLUMN_SUN, alarm.getDays()[1]);
        cv.put(ALARM_COLUMN_MON, alarm.getDays()[2]);
        cv.put(ALARM_COLUMN_TUE, alarm.getDays()[3]);
        cv.put(ALARM_COLUMN_WED, alarm.getDays()[4]);
        cv.put(ALARM_COLUMN_THU, alarm.getDays()[5]);
        cv.put(ALARM_COLUMN_FRI, alarm.getDays()[6]);
        cv.put(ALARM_COLUMN_DEFAULT_URI, alarm.getDefaultUri().toString());
        cv.put(ALARM_COLUMN_REQUEST_CODE, alarm.getRequestCode());

        db.update(ALARM_TABLE_NAME, cv, "id = ? ", new String[] { Long.toString(alarm.getAlarmId()) } );
        return true;
    }

    public int removeAlarm(AlarmModel alarm)
    {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(ALARM_TABLE_NAME, "id = ? ", new String[] { String.valueOf(alarm.getAlarmId()) });
    }

    public ArrayList<AlarmModel> getAllAlarms() {
        ArrayList<AlarmModel> ret = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * from " + ALARM_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            long id = res.getLong(res.getColumnIndex(ALARM_COLUMN_ID));
            int hour = res.getInt(res.getColumnIndex(ALARM_COLUMN_HOUR));
            int minute = res.getInt(res.getColumnIndex(ALARM_COLUMN_MINUTE));
            boolean isActive = res.getInt(res.getColumnIndex(ALARM_COLUMN_IS_ACTIVE)) == 1;
            boolean isSnoozed = res.getInt(res.getColumnIndex(ALARM_COLUMN_IS_SNOOZED)) == 1;
            boolean sat = res.getInt(res.getColumnIndex(ALARM_COLUMN_SAT)) == 1;
            boolean sun = res.getInt(res.getColumnIndex(ALARM_COLUMN_SUN)) == 1;
            boolean mon = res.getInt(res.getColumnIndex(ALARM_COLUMN_MON)) == 1;
            boolean tue = res.getInt(res.getColumnIndex(ALARM_COLUMN_TUE)) == 1;
            boolean wed = res.getInt(res.getColumnIndex(ALARM_COLUMN_WED)) == 1;
            boolean thu = res.getInt(res.getColumnIndex(ALARM_COLUMN_THU)) == 1;
            boolean fri = res.getInt(res.getColumnIndex(ALARM_COLUMN_FRI)) == 1;
            Uri uri = Uri.parse(res.getString(res.getColumnIndex(ALARM_COLUMN_DEFAULT_URI)));
            int requestCode = res.getInt(res.getColumnIndex(ALARM_COLUMN_REQUEST_CODE));
            boolean[] days = {sat, sun, mon, tue, wed, thu, fri};
            ret.add(new AlarmModel(hour, minute, requestCode, id, isActive, isSnoozed, uri, days));
            res.moveToNext();
        }
        return ret;
    }
}
