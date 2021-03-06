package com.jica.butterbookdata.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Nomen {
    @PrimaryKey(autoGenerate = true)
    private int nid;

    private String artikel;
    private String nomen;
    private String plural;
    private String mean_ko;
    private String mean_en;
    private String example;
    private String example_mean;


    //getters setters
    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String getArtikel() {
        return artikel;
    }

    public void setArtikel(String artikel) {
        this.artikel = artikel;
    }

    public String getNomen() {
        return nomen;
    }

    public void setNomen(String nomen) {
        this.nomen = nomen;
    }

    public String getPlural() {
        return plural;
    }

    public void setPlural(String plural) {
        this.plural = plural;
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

    @Override
    public String toString() {
        return "Nomen{" +
                "nid=" + nid +
                ", artikel='" + artikel + '\'' +
                ", nomen='" + nomen + '\'' +
                ", plural='" + plural + '\'' +
                ", mean_ko='" + mean_ko + '\'' +
                ", mean_en='" + mean_en + '\'' +
                ", example='" + example + '\'' +
                ", example_mean='" + example_mean + '\'' +
                '}';
    }
}
