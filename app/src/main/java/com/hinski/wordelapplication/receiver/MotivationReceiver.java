package com.hinski.wordelapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hinski.wordelapplication.util.NotificationHelper;

public class MotivationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = "Keep Your Streak Alive!";
        String message = "Come back and play Wordel to continue your streak!";
        NotificationHelper.showNotification(context, title, message);
    }
}
