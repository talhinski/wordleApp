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

    public Guess() {
        this.word = " ".repeat(5);
        this.results = List.of(LetterResult.ABSENT, LetterResult.ABSENT, LetterResult.ABSENT, LetterResult.ABSENT, LetterResult.ABSENT);
    }

    public String getWord() {
        return word;
    }

    public List<LetterResult> getResults() {
        return results;
    }
}
