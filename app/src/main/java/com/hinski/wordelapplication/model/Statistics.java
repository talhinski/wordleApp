package com.hinski.wordelapplication.model;

// מחלקה שמייצגת נתוני סטטיסטיקה של משחק אחד או משתמש
public class Statistics {

    // האחוז שבו המשתמש הצליח (למשל: 80%)
    private int percentage;

    // כמה ניסיונות לקח למשתמש לפתור את המילה
    private int numberOfAttempts;

    // בנאי – מקבל את שני הערכים ושומר אותם
    public Statistics(int percentage, int numberOfAttempts) {
        this.percentage = percentage;
        this.numberOfAttempts = numberOfAttempts;
    }

    // מחזיר את האחוזים
    public int getPercentage() {
        return percentage;
    }

    // מחזיר את מספר הניסיונות
    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }
}
