package com.hinski.wordelapplication.model;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import java.util.List;

public class Guess {
    public final ObservableList<CharResult> charResults = new ObservableArrayList<>();

    public Guess() {
        for (int i = 0; i < 4; i++) {
            charResults.add(new CharResult('x', LetterResult.EMPTY));
        }
    }

    public Guess(ObservableList<CharResult> charResults) {
        this.charResults.addAll(charResults);
    }

    public Guess(String word, List<LetterResult> results) {
        for (int i = 0; i < word.length(); i++) {
            charResults.add(new CharResult(word.charAt(i), results.get(i)));
        }
    }

    public ObservableList<CharResult> getCharResults() {
        return charResults;
    }
}
