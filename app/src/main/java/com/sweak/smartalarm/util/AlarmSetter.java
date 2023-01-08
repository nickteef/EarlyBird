package com.sweak.smartalarm.util;

import static com.sweak.smartalarm.SmartAlarmApplication.NOTIFICATION_ID;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.sweak.smartalarm.R;
import com.sweak.smartalarm.receiver.AlarmReceiver;
import com.sweak.smartalarm.service.AlarmService;

import java.util.Calendar;

public class AlarmSetter {

    private Calendar calendar;

    public static final int REGULAR_ALARM = 0;
    public static final int SNOOZE_ALARM = 1;

    private void setCalendarToAlarmTime(int alarmHour, int alarmMinute) {
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, alarmHour);
        calendar.set(Calendar.MINUTE, alarmMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }
    }

    public void schedule(Context context, int mode, View snackbarView) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(
                context, NOTIFICATION_ID, intent, 0);

        Preferences preferences = new Preferences(context);
        preferences.setAlarmPending(true);

        if (mode == REGULAR_ALARM) {
            setCalendarToAlarmTime(preferences.getAlarmHour(), preferences.getAlarmMinute());
            showSnackbarAlarmSet(context, snackbarView);
        } else if (mode == SNOOZE_ALARM) {
            setCalendarToAlarmTime(preferences.getSnoozeAlarmHour(), preferences.getSnoozeAlarmMinute());
            showToastSnoozeSet(context);
        }

        setAlarm(alarmManager, alarmPendingIntent);
    }

    public void schedule(Context context, int mode) {
        schedule(context, mode, null);
    }

    private void setAlarm(AlarmManager alarmManager, PendingIntent alarmPendingIntent) {
        alarmManager.setAlarmClock(
                new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), alarmPendingIntent),
                alarmPendingIntent
        );
    }

    private void showSnackbarAlarmSet(Context context, View snackbarView) {
        Preferences preferences = new Preferences(context);
        String snackbarText = context.getString(R.string.alarm_at, preferences.getAlarmTimeString());

        Snackbar alarmSetSnackbar = Snackbar.make(snackbarView, snackbarText, Snackbar.LENGTH_LONG);

        alarmSetSnackbar.setAction(context.getString(R.string.unset), v ->
                cancelAlarm(context, REGULAR_ALARM));

        alarmSetSnackbar.show();
    }

    private void showToastSnoozeSet(Context context) {
        Preferences preferences = new Preferences(context);
        String toastText = context.getString(R.string.alarm_at, preferences.getSnoozeAlarmTimeString());

        Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();
    }

    public static void cancelAlarm(Context context, int mode) {
        Intent intentService = new Intent(context, AlarmService.class);
        context.stopService(intentService);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(
                context, NOTIFICATION_ID, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
        alarmPendingIntent.cancel();

        Preferences preferences = new Preferences(context);
        if (mode == SNOOZE_ALARM)
            preferences.setSnoozeAlarmPending(false);
        if (mode == REGULAR_ALARM) {
            preferences.setAlarmPending(false);
            preferences.setSnoozeAlarmPending(false);
        }
    }
}
