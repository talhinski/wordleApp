package com.hinski.wordelapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.hinski.wordelapplication.logic.WordleLogic;
import com.hinski.wordelapplication.model.Guess;

import java.util.List;

public class GameViewModel extends AndroidViewModel {

    private final WordleLogic logic;
    public MutableLiveData<List<Guess>> attempts = new MutableLiveData<>();

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

    public void submitCurrentGuess() {
        if(logic.canSubmitCurrentGuess())
            logic.submitCurrentGuess();
    }

    public void deleteChar() {
        if(logic.canDeleteCharFromCurrentGuess())
            logic.deleteCharFromCurrentGuess();
    }

    public void enterChar(char c) {
        if (logic.canAddCharToCurrentGuess())
            logic.addCharToCurrentGuess(c);
    }
}