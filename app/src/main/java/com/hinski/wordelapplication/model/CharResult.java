package com.hinski.wordelapplication.model;

import androidx.databinding.ObservableField;

import java.util.Objects;

public class CharResult {
    public final ObservableField<Character> letter = new ObservableField<>('X');
    public final ObservableField<LetterResult> result = new ObservableField<>(LetterResult.EMPTY);


    public CharResult(char character, LetterResult result) {
        this.letter.set(character);
        this.result.set(result);
    }

    public void setLetter(char newLetter) {
        letter.set(newLetter);
    }

    public void setState(LetterResult newState) {
        result.set(newState);
    }

    public int getBackgroundColor() {
        switch (Objects.requireNonNull(result.get())) {
            case CORRECT:
                return 0xFF6AAA64; // green
            case MISPLACED:
                return 0xFFC9B458; // yellow
            case INCORRECT:
                return 0xFF787C7E; // gray
            default:
                return 0xFFDDDDDD; // default color
        }
    }
}
