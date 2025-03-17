package com.hinski.wordelapplication.util;

import com.hinski.wordelapplication.model.LetterResult;

public class BackgroundColorCalculator {

    public static Integer calculateBackgroundColor(LetterResult letterResult) {
        switch (letterResult) {
            case CORRECT:
                return 0xFF6AAA64; // Green
            case MISPLACED:
                return 0xFFC9B458; // Yellow
            case INCORRECT:
                return 0xFF3A3A3C; // gray
            default:
                return 0xFFFFFFFF; // Default to white
        }
    }
}
