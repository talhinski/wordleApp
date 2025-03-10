package com.hinski.wordelapplication.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class WordleGame {
    private final String secretWord;
    private final int maxAttempts;
    private final List<Guess> attempts = new ArrayList<>();

    public WordleGame(String secretWord, int maxAttempts) {
        this.secretWord = secretWord; // removed toLowerCase()
        this.maxAttempts = maxAttempts;
    }

    public WordleGame(String secretWord) {
        this(secretWord, 6);
    }

    public Guess guess(String word) {
        if (word.length() != secretWord.length()) {
            throw new IllegalArgumentException("Guess must be " + secretWord.length() + " letters long");
        }
        String guessText = word;
        List<LetterResult> result = new ArrayList<>();
        for (int i = 0; i < secretWord.length(); i++) {
            result.add(LetterResult.INCORRECT);
        }
        char[] secretChars = secretWord.toCharArray();

        // First pass: mark correct letters at correct positions.
        for (int i = 0; i < secretWord.length(); i++) {
            if (guessText.charAt(i) == secretChars[i]) {
                result.set(i, LetterResult.CORRECT);
                secretChars[i] = '*'; // mark as used
            }
        }

        // Second pass: mark present letters in wrong positions.
        for (int i = 0; i < secretWord.length(); i++) {
            if (result.get(i) == LetterResult.CORRECT)
                continue;
            char letter = guessText.charAt(i);
            int index = indexOfChar(secretChars, letter);
            if (index != -1) {
                result.set(i, LetterResult.MISPLACED);
                secretChars[index] = '*'; // mark as used
            }
        }

        Guess guessResult = new Guess(guessText, result);
        attempts.add(guessResult);
        return guessResult;
    }

    private int indexOfChar(char[] array, char letter) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == letter) {
                return i;
            }
        }
        return -1;
    }

    public boolean isGameWon() {
        for (Guess guess : attempts) {
            boolean allCorrect = true;
            for (CharResult letterResult : guess.getCharResults()) {
                if (letterResult.result.get() != LetterResult.CORRECT) {
                    allCorrect = false;
                    break;
                }
            }
            if (allCorrect) {
                return true;
            }
        }
        return false;
    }

    public boolean isGameOver() {
        return isGameWon() || attempts.size() >= maxAttempts;
    }

    public List<Guess> getAttempts() {
        return attempts;
    }
}
