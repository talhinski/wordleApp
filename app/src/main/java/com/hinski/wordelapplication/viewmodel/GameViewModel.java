package com.hinski.wordelapplication.viewmodel;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.Timestamp;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.vertexai.FirebaseVertexAI;
import com.google.firebase.vertexai.GenerativeModel;
import com.google.firebase.vertexai.java.GenerativeModelFutures;
import com.google.firebase.vertexai.type.Content;
import com.google.firebase.vertexai.type.GenerateContentResponse;
import com.hinski.wordelapplication.logic.WordleLogic;
import com.hinski.wordelapplication.model.Guess;
import com.hinski.wordelapplication.util.BackgroundColorCalculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.time.Duration;
import java.time.Instant;

public class GameViewModel extends AndroidViewModel {

    private final WordleLogic logic;
    public MutableLiveData<List<Guess>> attempts = new MutableLiveData<>();
    public final MutableLiveData<String> invalidWordEvent = new MutableLiveData<>();
    public final MutableLiveData<String> gameOverEvent = new MutableLiveData<>();
    public final MutableLiveData<String> gameWonEvent = new MutableLiveData<>();
    private final MutableLiveData<Map<Character, Integer>> usedLetters = new MutableLiveData<>(new HashMap<>());
    private final FirebaseFirestore db;

    private final String userId;

    private final Executor executor;

    public GameViewModel(@NonNull Application application) {
        super(application);
        logic = new WordleLogic(application.getResources());
        attempts.setValue(logic.getAttempts());
        db = FirebaseFirestore.getInstance();
        SharedPreferences sharedPreferences = application.getSharedPreferences("user_prefs", MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);
        executor = Executors.newSingleThreadExecutor();

    }

    public boolean isGameOver() {
        return logic.isGameOver();
    }

    public boolean isGameWon() {
        return logic.isGameWon();
    }

    public List<Guess> getGuesses() {
        return attempts.getValue();
    }

    public void setGuesses(List<Guess> guesses) {
        attempts.setValue(guesses);
    }

    public LiveData<Map<Character, Integer>> getUsedLetters() {
        return usedLetters;
    }

    public void submitCurrentGuess() {
        if (logic.canSubmitCurrentGuess()) {
            logic.submitCurrentGuess();
            updateUsedLetters();
            if (!checkGameWon())
                checkGameOver();
        } else {
            invalidWordEvent.postValue("המילה אינה קיימת");
        }
    }

    private void checkGameOver() {
        if (logic.isGameOver()) {
            gameOverEvent.postValue("המשחק נגמר");
            saveUserStatistics();
        }
    }

    private boolean checkGameWon() {
        if (logic.isGameWon()) {
            gameWonEvent.postValue("ניצחת!");
            saveUserStatistics();
            return true;
        } else {
            return false;
        }
    }

    public void deleteChar() {
        if (logic.canDeleteCharFromCurrentGuess())
            logic.deleteCharFromCurrentGuess();
    }

    public void enterChar(char c) {
        if (logic.canAddCharToCurrentGuess())
            logic.addCharToCurrentGuess(c);
    }

    private void updateUsedLetters() {
        // Logic to update the usedLetters map with the appropriate colors
        Map<Character, Integer> colors = usedLetters.getValue();
        logic.getUsedLetters().forEach((letter, result) -> {
            Integer color = BackgroundColorCalculator.calculateBackgroundColor(result);
            colors.put(letter, color);
        });
        usedLetters.postValue(colors);
    }

    public void getHint() {
        // Initialize the Vertex AI service and create a `GenerativeModel` instance
        // Specify a model that supports your use case
        GenerativeModel gm = FirebaseVertexAI.getInstance()
                .generativeModel("gemini-2.0-flash");
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);


        String secretWord = logic.getSecretWord();
        String promptText = String.format("צור רמז במישפט אחד יחסית קצר" +
                " שיהייה אפשר לנחש במשחק וורדל המילה היא: %s", secretWord);
        // Provide a prompt that contains text
        Content prompt = new Content.Builder()
                .addText(promptText)
                .build();

        // To generate text output, call generateContent with the text input
        ListenableFuture<GenerateContentResponse> response = model.generateContent(prompt);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                // raise Toast with the hint
                invalidWordEvent.postValue(resultText);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, executor);
    }
    public void saveUserStatistics() {
        Map<String, Object> userStats = new HashMap<>();
        userStats.put("timeStamp", Timestamp.now());
        userStats.put("word", logic.getSecretWord());
        userStats.put("attempts", logic.getCurrentAttempt());
        userStats.put("gameWon", logic.isGameWon());

        // Save each game result as a new document in the statistics collection
        db.collection("users")
                .document(userId)
                .collection("statistics")
                .add(userStats)
                .addOnSuccessListener(documentReference -> {
                    // Update streak days
                    updateStreakDays();
                });
    }

    private void resetSteak() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("streak", 1);
        editor.apply();
    }

    private void updateStreakDays() {
        db.collection("users")
                .document(userId)
                .collection("statistics")
                .orderBy("timeStamp", Query.Direction.DESCENDING)
                .limit(2)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        if (documents.size() == 2) {
                            Timestamp lastGameTime = documents.get(1).getTimestamp("timeStamp");
                            Timestamp currentGameTime = documents.get(0).getTimestamp("timeStamp");

                            if (lastGameTime != null && currentGameTime != null) {

                                long diffDays = Duration.between(
                                        lastGameTime.toDate().toInstant(),
                                        currentGameTime.toDate().toInstant()).toDays();

                                if (diffDays == 1) {
                                    // Increment streak
                                    SharedPreferences sharedPreferences = getApplication().getSharedPreferences("user_prefs", MODE_PRIVATE);
                                    int streak = sharedPreferences.getInt("streak", 0);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putInt("streak", streak + 1);
                                    editor.apply();
                                } else if (diffDays > 1) {
                                    resetSteak();
                                }
                            }
                        } else {
                            // First game or only one game played
                            resetSteak();
                        }
                    }
                });
    }


}