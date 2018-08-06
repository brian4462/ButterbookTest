package com.jica.butterbookdata.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Word {
    @PrimaryKey(autoGenerate = true)
    private int wid;
    private String word;
    private String mean;
    private int bookmark = 1;// 0:yes 1:no
    private int study = 1;// 0:yes 1:no
    private String date = null;
    private int category; //1:nomen 2:verben 3:adjektiv
    private int category_id;
    private int clickcnt=0;
    private int quizfinish=1;// 0:finish 1:not finish 2:correct 3:wrong

    //getters setters
    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public int getBookmark() {
        return bookmark;
    }

    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }

    public int getStudy() {
        return study;
    }

    public void setStudy(int study) {
        this.study = study;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getClickcnt() {
        return clickcnt;
    }

    public void setClickcnt(int clickcnt) {
        this.clickcnt = clickcnt;
    }

    public int getQuizfinish() {
        return quizfinish;
    }

    public void setQuizfinish(int quizfinish) {
        this.quizfinish = quizfinish;
    }
}
