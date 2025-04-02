package com.hinski.wordelapplication.model;


import com.google.firebase.Timestamp;

public class StatisticRecord {

    private Timestamp timeStamp;
    private String word;
    private int attempts;
    private boolean gameWon;

    // Default constructor required for calls to DataSnapshot.getValue(Statistic.class)
    public StatisticRecord() {}

    public StatisticRecord(Timestamp timeStamp, String word, int attempts, boolean gameWon) {
        this.timeStamp = timeStamp;
        this.word = word;
        this.attempts = attempts;
        this.gameWon = gameWon;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public String getWord() {
        return word;
    }

    public int getAttempts() {
        return attempts;
    }

    public boolean isGameWon() {
        return gameWon;
    }


}