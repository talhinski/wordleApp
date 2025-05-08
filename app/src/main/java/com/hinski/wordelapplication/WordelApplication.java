package com.hinski.wordelapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.hinski.wordelapplication.receiver.MotivationReceiver;
import com.hinski.wordelapplication.util.NotificationHelper;

public class WordelApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);

        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                PlayIntegrityAppCheckProviderFactory.getInstance());

        // Create notification channel
        NotificationHelper.createNotificationChannel(this);

        // Schedule motivational notifications
        scheduleMotivationalNotifications();
    }

    public static void requestNotificationPermission(Activity activity) {
        if (activity.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }

    private void scheduleMotivationalNotifications() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MotivationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0,
                intent, PendingIntent.FLAG_MUTABLE);

        long interval = 5 * 60 * 1000; // Trigger every 5 minutes for testing
        // long interval = AlarmManager.INTERVAL_HALF_DAY; // Trigger 12hours
        long triggerAt = System.currentTimeMillis() + interval;

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    triggerAt,
                    interval,
                    pendingIntent
            );
        }
    }
}