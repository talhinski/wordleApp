package com.hinski.wordelapplication.model;

public class Statistics {
    private int percentage;

    private int numberOfAttempts;

    public Statistics(int percentage, int numberOfAttempts) {
        this.percentage = percentage;
        this.numberOfAttempts = numberOfAttempts;
    }

    public int getPercentage() {
        return percentage;
    }

    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }
}
