package com.hinski.wordelapplication.model;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.hinski.wordelapplication.util.BackgroundColorCalculator;

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
        LetterResult currentResult = Objects.requireNonNull(result.get());
        backgroundColor.set(BackgroundColorCalculator.calculateBackgroundColor(currentResult));
    }
}
