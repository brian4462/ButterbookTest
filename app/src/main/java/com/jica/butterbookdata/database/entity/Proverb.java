package com.jica.butterbookdata.database.entity;

public class Proverb {
    private String proverb;
    private String mean;

    public Proverb() {
    }

    public Proverb(String proverb, String mean) {
        this.proverb = proverb;
        this.mean = mean;
    }

    public String getProverb() {
        return proverb;
    }

    public void setProverb(String proverb) {
        this.proverb = proverb;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
}
