package com.hinski.wordelapplication.model;

import java.util.ArrayList;
import java.util.List;

public class WordleGame {

    // המילה שצריך לנחש
    private final String secretWord;

    // מספר הניסיונות המקסימלי למשחק
    private final int maxAttempts;

    // רשימת כל הניסיונות של המשתמש (Guess = ניחוש)
    private final List<Guess> attempts = new ArrayList<>();

    // הניסיון הנוכחי שהשחקן נמצא בו
    private int currentAttempt = 0;

    // מספר האות הנוכחית בתוך הניחוש
    private int currentChar = 0;

    public int getCurrentAttempt() { return currentAttempt; }

    public int getCurrentChar() { return currentChar; }

    public int getMaxAttempts() { return maxAttempts; }

    // בנאי עם מספר ניסיונות מותאם
    public WordleGame(String secretWord, int maxAttempts) {
        this.secretWord = secretWord;
        this.maxAttempts = maxAttempts;

        // מכין מראש את כל הניסיונות הריקים (Guess ריק לכל ניסיון)
        for (int i = 0; i < maxAttempts; i++) {
            attempts.add(new Guess());
        }
    }

    // בנאי ברירת מחדל – 6 ניסיונות
    public WordleGame(String secretWord) {
        this(secretWord, 6);
    }

    // בודק ניחוש שהוזן מול המילה הסודית ומחזיר תוצאה
    public Guess guess(String word) {
        if (word.length() != secretWord.length()) {
            throw new IllegalArgumentException("Guess must be " + secretWord.length() + " letters long");
        }

        List<LetterResult> result = new ArrayList<>();
        for (int i = 0; i < secretWord.length(); i++) {
            result.add(LetterResult.INCORRECT); // כל אות מתחילה כ"שגויה"
        }

        char[] secretChars = secretWord.toCharArray();

        // שלב ראשון – אות במקום הנכון
        for (int i = 0; i < secretWord.length(); i++) {
            if (word.charAt(i) == secretChars[i]) {
                result.set(i, LetterResult.CORRECT);
                secretChars[i] = '*'; // מסמן את האות כ"משומשת"
            }
        }

        // שלב שני – אות נכונה אבל במקום לא נכון
        for (int i = 0; i < secretWord.length(); i++) {
            if (result.get(i) == LetterResult.CORRECT)
                continue;
            char letter = word.charAt(i);
            int index = indexOfChar(secretChars, letter);
            if (index != -1) {
                result.set(i, LetterResult.MISPLACED);
                secretChars[index] = '*';
            }
        }

        return new Guess(word, result); // מחזיר את תוצאת הניחוש
    }

    // מחפש מיקום של אות במערך (מחפש רק אותות שעדיין לא סומנו כ"משומשות")
    private int indexOfChar(char[] array, char letter) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == letter) return i;
        }
        return -1;
    }

    // האם כל האותיות של ניחוש כלשהו נכונות?
    public boolean isGameWon() {
        for (Guess guess : attempts) {
            boolean allCorrect = true;
            for (CharResult letterResult : guess.getCharResults()) {
                if (letterResult.result.get() != LetterResult.CORRECT) {
                    allCorrect = false;
                    break;
                }
            }
            if (allCorrect) return true;
        }
        return false;
    }

    // האם המשחק הסתיים? (או שניצחנו או שנגמרו הניסיונות)
    public boolean isGameOver() {
        return isGameWon() || currentAttempt >= maxAttempts;
    }

    public List<Guess> getAttempts() {
        return attempts;
    }

    // האם מותר להוסיף עוד אות לניחוש הנוכחי?
    public boolean canAddCharToCurrentGuess() {
        return currentAttempt < maxAttempts &&
                currentChar < secretWord.length();
    }

    // מוסיף אות לניחוש הנוכחי
    public Guess addCharToCurrentGuess(char c) {
        if (!canAddCharToCurrentGuess()) {
            throw new IllegalStateException("Cannot add more characters to current guess");
        }

        // שינוי של אות סופית בהתאם לאות רגילה (לשפה העברית)
        if (currentChar + 1 == secretWord.length()) {
            switch (c) {
                case 'פ': c = 'ף'; break;
                case 'צ': c = 'ץ'; break;
                case 'כ': c = 'ך'; break;
                case 'מ': c = 'ם'; break;
                case 'נ': c = 'ן'; break;
            }
        }

        // מוסיף את האות במקום המתאים
        Guess currentGuess = attempts.get(currentAttempt);
        currentGuess.getCharResults().get(currentChar).letter.set(c);
        currentChar++;
        return currentGuess;
    }

    // האם אפשר למחוק אות מניחוש?
    public boolean canDeleteCharFromCurrentGuess() {
        return currentChar > 0;
    }

    // מוחק אות אחת מהניחוש
    public Guess deleteCharFromCurrentGuess() {
        if (!canDeleteCharFromCurrentGuess()) {
            throw new IllegalStateException("Cannot delete more characters from current guess");
        }

        Guess currentGuess = attempts.get(currentAttempt);
        currentGuess.getCharResults().get(--currentChar).letter.set('\0'); // מוחק את האות האחרונה
        return currentGuess;
    }

    // מחזיר את הניחוש הנוכחי של המשתמש
    public Guess getCurrentGuess() {
        return attempts.get(currentAttempt);
    }

    // שולח את הניחוש לבדיקה ומעדכן את התוצאה
    public void submitCurrentGuess() {
        Guess currentGuess = attempts.get(currentAttempt);
        Guess result = guess(currentGuess.getWord());
        currentGuess.updateGuess(result); // מעדכן את הצבעים והתוצאה
        currentAttempt++;
        currentChar = 0; // מתחילים שורה חדשה
    }

    // מחזיר את המילה הסודית (לבדיקות או לחשיפת פתרון)
    public String getSecretWord() {
        return secretWord;
    }
}
