package com.hinski.wordelapplication.viewmodel;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hinski.wordelapplication.model.StatisticRecord;
import com.hinski.wordelapplication.model.Statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsViewModal extends AndroidViewModel {

    private final MutableLiveData<List<Statistics>> statistics = new MutableLiveData<>();
    public final MutableLiveData<Integer> totalGames = new MutableLiveData<>(0);
    public final MutableLiveData<Integer> totalWins = new MutableLiveData<>(0);
    public final MutableLiveData<Integer> successRate = new MutableLiveData<>(0);
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String userId;

    public StatisticsViewModal(@NonNull Application application) {
        super(application);
        userId = application
                .getSharedPreferences("user_prefs", MODE_PRIVATE)
                .getString("user_id", null);
        fetchStatistics();
    }

    private void fetchStatistics() {
        db.collection("users")
                .document(userId)
                .collection("statistics")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<StatisticRecord> stats = task.getResult().toObjects(StatisticRecord.class);
                        List<Statistics> attemptStatistics = calculateStatistics(stats);
                        statistics.setValue(attemptStatistics);

                        int totalGamesCount = stats.size();
                        int totalWinsCount = 0;
                        for (StatisticRecord record : stats) {
                            if (record.isGameWon()) {
                                totalWinsCount++;
                            }
                        }
                        int successRateValue = (int) ((totalWinsCount / (double) totalGamesCount) * 100);

                        totalGames.setValue(totalGamesCount);
                        totalWins.setValue(totalWinsCount);
                        successRate.setValue(successRateValue);
                    }
                });
    }

    private List<Statistics> calculateStatistics(List<StatisticRecord> stats) {
        int[] attemptsCount = new int[7];
        int totalGames = stats.size();

        for (StatisticRecord record : stats) {
            if (record.isGameWon()) {
                int attempts = record.getAttempts();
                if (attempts >= 1 && attempts <= 6) {
                    attemptsCount[attempts - 1]++;
                }
            } else {
                attemptsCount[6]++;
            }
        }

        List<Statistics> statisticsList = new ArrayList<>();
        for (int i = 0; i < attemptsCount.length; i++) {
            int percentage = (int) ((attemptsCount[i] / (double) totalGames) * 100);
            statisticsList.add(new Statistics( percentage,i + 1));
        }

        return statisticsList;
    }
    public LiveData<List<Statistics>> getStatistics() {
        return statistics;
    }


}