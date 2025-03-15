package com.hinski.wordelapplication.model;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import java.util.Objects;

public class CharResult {
    public final ObservableField<Character> letter = new ObservableField<>('\0');
    public final ObservableField<LetterResult> result = new ObservableField<>(LetterResult.EMPTY);
    public final ObservableInt backgroundColor = new ObservableInt(0xFFFFFFFF); // default color white

    public CharResult() {
        result.addOnPropertyChangedCallback(new androidx.databinding.Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(androidx.databinding.Observable sender, int propertyId) {
                updateBackgroundColor();
            }
        });
    }

    public CharResult(char character, LetterResult result) {
        this();
        this.letter.set(character);
        this.result.set(result);
    }

    public void setLetter(char newLetter) {
        letter.set(newLetter);
    }

    public void setState(LetterResult newState) {
        result.set(newState);
    }

    private void updateBackgroundColor() {
        switch (Objects.requireNonNull(result.get())) {
            case CORRECT:
                backgroundColor.set(0xFF6AAA64); // green
                break;
            case MISPLACED:
                backgroundColor.set(0xFFC9B458); // yellow
                break;
            case INCORRECT:
                backgroundColor.set(0xFF787C7E); // gray
                break;
            default:
                backgroundColor.set(0xFFFFFFFF); // default color white
                break;
        }
    }
}
