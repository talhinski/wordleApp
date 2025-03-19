package com.hinski.wordelapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hinski.wordelapplication.logic.WordleLogic;
import com.hinski.wordelapplication.model.Guess;
import com.hinski.wordelapplication.util.BackgroundColorCalculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameViewModel extends AndroidViewModel {

    private final WordleLogic logic;
    public MutableLiveData<List<Guess>> attempts = new MutableLiveData<>();
    public final MutableLiveData<String> invalidWordEvent = new MutableLiveData<>();
    public final MutableLiveData<String> gameOverEvent = new MutableLiveData<>();
    public final MutableLiveData<String> gameWonEvent = new MutableLiveData<>();
    private final MutableLiveData<Map<Character, Integer>> usedLetters = new MutableLiveData<>(new HashMap<>());


    public GameViewModel(@NonNull Application application) {
        super(application);
        logic = new WordleLogic(application.getResources());
        attempts.setValue(logic.getAttempts());
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
        if (logic.isGameOver() ) {
            gameOverEvent.postValue("המשחק נגמר");
        }
    }

    private boolean checkGameWon() {
        if (logic.isGameWon()) {
            gameWonEvent.postValue("ניצחת!");
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
}