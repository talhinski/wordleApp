package com.hinski.wordelapplication.logic;

import android.content.res.Resources;
import android.util.Log; // Add this import

import com.hinski.wordelapplication.model.CharResult;
import com.hinski.wordelapplication.model.Guess;
import com.hinski.wordelapplication.model.LetterResult;
import com.hinski.wordelapplication.model.WordleGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class WordleLogic {
    private static final String TAG = "WordleLogic"; // Add this line
    private final WordleGame game;
    // Initialize vocabulary from resource via WordelWords.
    private final Set<String> vocabulary;

    // New empty constructor that chooses a secret word automatically.
    public WordleLogic(Resources res) {
        this.vocabulary = new WordelWords(res).getWords();
        if (vocabulary.isEmpty()) {
            throw new IllegalStateException("Vocabulary is empty!");
        }
        List<String> vocabList = new ArrayList<>(vocabulary);
        String secretWord = vocabList.get(new Random().nextInt(vocabList.size()));
        game = new WordleGame(secretWord);
        Log.i(TAG, "Secret word: " + secretWord); // Add this line
    }

    public boolean isValidWord(String word) {
        return vocabulary.contains(word);
    }

    public Guess getGuess(int index) {
        if (index < 0 || index >= game.getAttempts().size()) {
            return new Guess(); // Assuming Guess has a constructor that takes a String
        }
        return game.getAttempts().get(index);
    }

    public Guess processGuess(String guess) {

        try {
            Guess result = game.guess(guess);
            // Minimal output; merge as needed.
            System.out.println("Guess: " + result.getCharResults());
            return result;
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public boolean isGameOver() {
        return game.isGameOver();
    }

    public boolean isGameWon() {
        return game.isGameWon();
    }

    public boolean canAddCharToCurrentGuess() {
        return game.canAddCharToCurrentGuess();
    }

    public Guess addCharToCurrentGuess(char c) {
        return game.addCharToCurrentGuess(c);
    }

    public boolean canDeleteCharFromCurrentGuess() {
        return game.canDeleteCharFromCurrentGuess();
    }

    public Guess deleteCharFromCurrentGuess() {
        return game.deleteCharFromCurrentGuess();
    }

    public boolean canSubmitCurrentGuess() {
        return vocabulary.contains(game.getCurrentGuess().getWord());
    }

    public void submitCurrentGuess() {
        if (!canSubmitCurrentGuess()) {
            throw new IllegalStateException("Cannot submit current guess");
        }
        game.submitCurrentGuess();
    }

    public List<Guess> getAttempts() {
        return game.getAttempts();
    }

    public Map<Character, LetterResult> getUsedLetters() {
        Map<Character, LetterResult> usedLetters = new HashMap<>();
        for (int i = 0; i < game.getCurrentAttempt(); i++) {
            Guess guess = game.getAttempts().get(i);
            for (CharResult result : guess.getCharResults()) {
                if (result.result.get() != LetterResult.EMPTY) {
                    Character letter = result.letter.get();
                    if (!usedLetters.containsKey(letter)) {
                        usedLetters.put(letter, result.result.get());
                    } else {
                        LetterResult currentResult = usedLetters.get(letter);
                        if (currentResult == LetterResult.MISPLACED && result.result.get() == LetterResult.CORRECT) {
                            usedLetters.put(letter, result.result.get());
                        }
                    }
                }
            }
        }
        return usedLetters;
    }
}
