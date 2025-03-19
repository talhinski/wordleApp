package com.hinski.wordelapplication.model;

import java.util.ArrayList;
import java.util.List;


public class WordleGame {
    private final String secretWord;
    private final int maxAttempts;
    private final List<Guess> attempts = new ArrayList<>();
    private int currentAttempt = 0;

    private int currentChar = 0;

    public int getCurrentAttempt() {
        return currentAttempt;
    }

    public int getCurrentChar() {
        return currentChar;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public WordleGame(String secretWord, int maxAttempts) {
        this.secretWord = secretWord; // removed toLowerCase()
        this.maxAttempts = maxAttempts;
        for (int i = 0; i < maxAttempts; i++) {
            attempts.add(new Guess());
        }
    }

    public WordleGame(String secretWord) {
        this(secretWord, 6);
    }

    public Guess guess(String word) {
        if (word.length() != secretWord.length()) {
            throw new IllegalArgumentException("Guess must be " + secretWord.length() + " letters long");
        }
        List<LetterResult> result = new ArrayList<>();
        for (int i = 0; i < secretWord.length(); i++) {
            result.add(LetterResult.INCORRECT);
        }
        char[] secretChars = secretWord.toCharArray();


        // First pass: mark correct letters at correct positions.
        for (int i = 0; i < secretWord.length(); i++) {
            if (word.charAt(i) == secretChars[i]) {
                result.set(i, LetterResult.CORRECT);
                secretChars[i] = '*'; // mark as used
            }
        }

        // Second pass: mark present letters in wrong positions.
        for (int i = 0; i < secretWord.length(); i++) {
            if (result.get(i) == LetterResult.CORRECT)
                continue;
            char letter = word.charAt(i);
            int index = indexOfChar(secretChars, letter);
            if (index != -1) {
                result.set(i, LetterResult.MISPLACED);
                secretChars[index] = '*'; // mark as used
            }
        }

        return new Guess(word, result);
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
        return isGameWon() || currentAttempt >= maxAttempts;
    }

    public List<Guess> getAttempts() {
        return attempts;
    }

    public boolean canAddCharToCurrentGuess() {
        return currentAttempt < maxAttempts &&
                currentChar < secretWord.length();
    }

    public Guess addCharToCurrentGuess(char c) {
        if (!canAddCharToCurrentGuess()) {
            throw new IllegalStateException("Cannot add more characters to current guess");
        }
        Guess currentGuess = attempts.get(currentAttempt);
        if(currentChar + 1 == secretWord.length()) {
            switch (c) {
                case 'פ':
                    c = 'ף';
                    break;
                case 'צ':
                    c = 'ץ';
                    break;
                case 'כ':
                    c = 'ך';
                    break;
                case 'מ':
                    c = 'ם';
                    break;
                case 'נ':
                    c = 'ן';
                    break;
                default:
                    break;
            }
        }
        currentGuess.getCharResults().get(currentChar).letter.set(c);
        currentChar++;
        return currentGuess;
    }

    public boolean canDeleteCharFromCurrentGuess() {
        return currentChar > 0;
    }

    public Guess deleteCharFromCurrentGuess() {
        if (!canDeleteCharFromCurrentGuess()) {
            throw new IllegalStateException("Cannot delete more characters from current guess");
        }
        Guess currentGuess = attempts.get(currentAttempt);
        currentGuess.getCharResults().get(--currentChar).letter.set('\0');
        return currentGuess;
    }

    public Guess getCurrentGuess() {
        return attempts.get(currentAttempt);
    }

    public void submitCurrentGuess() {
        Guess currentGuess = attempts.get(currentAttempt);
        Guess result = guess(currentGuess.getWord());
        currentGuess.updateGuess(result);
        currentAttempt++;
        currentChar = 0;
    }


}
