package com.hinski.wordelapplication.model;

public class CharResult {
    private char character;
    private LetterResult result;

    public CharResult(char character, LetterResult result) {
        this.character = character;
        this.result = result;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public LetterResult getResult() {
        return result;
    }

    public void setResult(LetterResult result) {
        this.result = result;
    }
}
