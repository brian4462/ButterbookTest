package com.jica.butterbookdata.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class VerbenMitPraposition {
    @PrimaryKey(autoGenerate = true)
    private int pid;

    private String verben;
    private String mit_praposition;
    private String mean_ko;
    private String mean_en;

    //getters, setters

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getVerben() {
        return verben;
    }

    public void setVerben(String verben) {
        this.verben = verben;
    }

    public String getMit_praposition() {
        return mit_praposition;
    }

    public void setMit_praposition(String mit_praposition) {
        this.mit_praposition = mit_praposition;
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
}
