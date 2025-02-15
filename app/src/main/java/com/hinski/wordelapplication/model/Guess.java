package com.hinski.wordelapplication.model;

import java.util.List;

public class Guess {
    String word;
    List<LetterResult> results;

    // Constructor
    public Guess(String word, List<LetterResult> results) {
        this.word = word;
        this.results = results;
    }

    public String getWord() {
        return word;
    }
    public List<LetterResult> getResults() {
        return results;
    }
}
