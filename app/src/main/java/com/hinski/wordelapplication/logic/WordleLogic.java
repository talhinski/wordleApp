package com.hinski.wordelapplication.logic;

import android.content.res.Resources;


import com.hinski.wordelapplication.model.Guess;
import com.hinski.wordelapplication.model.WordleGame;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class WordleLogic {
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
    public WordleGame getGame() {
        return game;
    }

}
