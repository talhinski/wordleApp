package com.hinski.wordelapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.common.util.concurrent.ListenableFuture;
import com.hinski.wordelapplication.util.NotificationHelper;
import com.google.firebase.vertexai.FirebaseVertexAI;
import com.google.firebase.vertexai.GenerativeModel;
import com.google.firebase.vertexai.java.GenerativeModelFutures;
import com.google.firebase.vertexai.type.Content;
import com.google.firebase.vertexai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MotivationReceiver extends BroadcastReceiver {


    final String prompt = "צור הודעה הומוריסטית למוטיבציה בעברית למשתמש " +
            "עם רצף של %d ימים במשחק וורדל. ההודעה צריכה לכלול כותרת וגוף הודעה, " +
            "מופרדים על ידי שורה חדשה. ההודעה צריכה להיות קצרה ותוצג כהתראת מערכת באפליקצית אנדרוייד";
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int streak = sharedPreferences.getInt("streak", 0);

        GenerativeModel gm = FirebaseVertexAI.getInstance()
                .generativeModel("gemini-2.0-flash");
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        String promptText = String.format(prompt, streak);

        Content prompt = new Content.Builder()
                .addText(promptText)
                .build();

        ListenableFuture<GenerateContentResponse> response = model.generateContent(prompt);
        Futures.addCallback(response, new MotivationResponseCallback(context), executor);
    }

    private static class MotivationResponseCallback implements FutureCallback<GenerateContentResponse> {
        private final Context context;
        private static final String TAG = "MotivationReceiver";
        final String header = "**כותרת:**";
        final String body = "**גוף:**";

        final String fallbackMessage = "שמור על הרצף שלך! אל תוותר על המשחק!";
        final String fallbackTitle = "אל תוותר על הרצף!";

        public MotivationResponseCallback(Context context) {
            this.context = context;
        }


        @Override
        public void onSuccess(GenerateContentResponse result) {
            String[] parts = Objects.requireNonNull(result.getText()).lines().toArray(String[]::new);
            String title = parts.length > 0 ? parts[0].trim().replace(header, "") : fallbackTitle;
            String message = parts.length > 2 ? parts[2].trim().replace(body, "") : fallbackMessage;
            NotificationHelper.showNotification(context, title, message);
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e(TAG, "Failed to generate motivational message", t);
            NotificationHelper.showNotification(context, fallbackTitle, fallbackMessage);
        }
    }
}
