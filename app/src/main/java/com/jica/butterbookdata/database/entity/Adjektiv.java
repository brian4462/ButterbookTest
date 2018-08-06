package com.jica.butterbookdata.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Adjektiv {
    @PrimaryKey(autoGenerate = true)
    private int aid;

    private String word_adjektiv;
    private String mean_ko;
    private String mean_en;
    private String example;
    private String example_mean;

    //getters setters

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getWord_adjektiv() {
        return word_adjektiv;
    }

    public void setWord_adjektiv(String word_adjektiv) {
        this.word_adjektiv = word_adjektiv;
    }

    public String getMean_ko() {
        return mean_ko;
    }

    public void setMean_ko(String mean_ko) {
        this.mean_ko = mean_ko;
    }

    public String getMean_en() {
        return mean_en;
    }

    public void setMean_en(String mean_en) {
        this.mean_en = mean_en;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExample_mean() {
        return example_mean;
    }

    public void setExample_mean(String example_mean) {
        this.example_mean = example_mean;
    }
}
