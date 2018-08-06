package com.jica.butterbookdata.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Verben {
    @PrimaryKey(autoGenerate = true)
    private int vid;

    private String verb_wir;
    private String verb_ich;
    private String verb_du;
    private String verb_er_sie_es;
    private String verb_ihr;
    private String objectform;
    private String prateritum_ich;
    private String partizip2_hilfsverb;
    private String partizip2;
    private String mean_ko;
    private String mean_en;
    private String example;
    private String example_mean;

    //getters setters

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getVerb_wir() {
        return verb_wir;
    }

    public void setVerb_wir(String verb_wir) {
        this.verb_wir = verb_wir;
    }

    public String getVerb_ich() {
        return verb_ich;
    }

    public void setVerb_ich(String verb_ich) {
        this.verb_ich = verb_ich;
    }

    public String getVerb_du() {
        return verb_du;
    }

    public void setVerb_du(String verb_du) {
        this.verb_du = verb_du;
    }

    public String getVerb_er_sie_es() {
        return verb_er_sie_es;
    }

    public void setVerb_er_sie_es(String verb_er_sie_es) {
        this.verb_er_sie_es = verb_er_sie_es;
    }

    public String getVerb_ihr() {
        return verb_ihr;
    }

    public void setVerb_ihr(String verb_ihr) {
        this.verb_ihr = verb_ihr;
    }

    public String getObjectform() {
        return objectform;
    }

    public void setObjectform(String objectform) {
        this.objectform = objectform;
    }

    public String getPrateritum_ich() {
        return prateritum_ich;
    }

    public void setPrateritum_ich(String prateritum_ich) {
        this.prateritum_ich = prateritum_ich;
    }

    public String getPartizip2_hilfsverb() {
        return partizip2_hilfsverb;
    }

    public void setPartizip2_hilfsverb(String partizip2_hilfsverb) {
        this.partizip2_hilfsverb = partizip2_hilfsverb;
    }

    public String getPartizip2() {
        return partizip2;
    }

    public void setPartizip2(String partizip2) {
        this.partizip2 = partizip2;
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
