package com.hinski.wordelapplication.model;

import java.util.ArrayList;
import java.util.List;

public class Guess {
    private List<CharResult> charResults;

    public Guess() {
        charResults = new ArrayList<CharResult>();
    }

    public Guess(List<CharResult> charResults) {
        this.charResults = charResults;
    }

    public Guess(String word, List<LetterResult> results) {
        charResults = new ArrayList<CharResult>();
        for (int i = 0; i < word.length(); i++) {
            charResults.add(new CharResult(word.charAt(i), results.get(i)));
        }
    }


    public List<CharResult> getCharResults() {
        return charResults;
    }

    public void setCharResults(List<CharResult> charResults) {
        this.charResults = charResults;
    }
}
