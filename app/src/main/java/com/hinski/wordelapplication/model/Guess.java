package com.hinski.wordelapplication.model;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import java.util.List;

public class Guess {
    public final ObservableList<CharResult> charResults = new ObservableArrayList<>();

    public Guess() {
        for (int i = 0; i < 5; i++) {
            charResults.add(new CharResult());
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

    public String getWord() {
        StringBuilder word = new StringBuilder();
        for (CharResult charResult : charResults) {
            word.append(charResult.letter.get());
        }
        return word.toString();
    }

    public void updateGuess(Guess guess) {
        for (int i = 0; i < charResults.size(); i++) {
            CharResult current = charResults.get(i);
            CharResult updated = guess.charResults.get(i);
            current.letter.set(updated.letter.get());
            current.result.set(updated.result.get());
        }
    }
}
