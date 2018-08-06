package com.jica.butterbookdata.thread;

import android.app.Activity;
import android.content.SharedPreferences;

import com.jica.butterbookdata.database.AppDB;
import com.jica.butterbookdata.database.dao.AdjektivDAO;
import com.jica.butterbookdata.database.dao.NomenDAO;
import com.jica.butterbookdata.database.dao.VerbenDAO;
import com.jica.butterbookdata.database.dao.WordDAO;
import com.jica.butterbookdata.database.entity.Adjektiv;
import com.jica.butterbookdata.database.entity.Nomen;
import com.jica.butterbookdata.database.entity.Verben;
import com.jica.butterbookdata.database.entity.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadFileToDB extends Thread {
    private NomenDAO nomenDAO;
    private VerbenDAO verbenDAO;
    private AdjektivDAO adjektivDAO;
    private WordDAO wordDAO;
    private Activity mActivity;
    public ReadFileToDB(Activity activity){
        mActivity = activity;
    }
    public void run(){
        setDB();
        SharedPreferences myPref = mActivity.getSharedPreferences("myPref",0);
        SharedPreferences.Editor editor = myPref.edit();
        editor.putInt("CreateDB",1);
        editor.commit();
    }

    private void setDB() {
        //nomen
        nomenDAO = AppDB.getInstance(mActivity).nomenDAO();
        try {
            InputStreamReader is = new InputStreamReader(mActivity.getAssets().open("Nomen.tsv"));
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            String[] st;
            int nid = 1;

            while ((line = reader.readLine()) != null) {
                st = line.split("\t");
                Nomen nomen = new Nomen();
                nomen.setNid(nid);
                nomen.setArtikel(st[0]);
                nomen.setNomen(st[1]);
                nomen.setPlural(st[2]);
                nomen.setMean_ko(st[3]);
                nomen.setMean_en(st[4]);
                nomen.setExample(st[5]);
                nomen.setExample_mean(st[6]);
                nomenDAO.insert(nomen);
                nid++;
            }
        } catch (IOException e) { }
        //verben
        verbenDAO = AppDB.getInstance(mActivity).verbenDAO();
        try {
            InputStreamReader is = new InputStreamReader(mActivity.getAssets().open("Verben.tsv"));
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            String[] st;
            int vid = 1;

            while ((line = reader.readLine()) != null) {
                st = line.split("\t");
                Verben verben = new Verben();
                verben.setVid(vid);
                verben.setVerb_wir(st[0]);
                verben.setVerb_ich(st[1]);
                verben.setVerb_du(st[2]);
                verben.setVerb_er_sie_es(st[3]);
                verben.setVerb_ihr(st[4]);
                verben.setObjectform(st[5]);
                verben.setPrateritum_ich(st[6]);
                verben.setPartizip2_hilfsverb(st[7]);
                verben.setPartizip2(st[8]);
                verben.setMean_ko(st[9]);
                verben.setMean_en(st[10]);
                verben.setExample(st[11]);
                verben.setExample_mean(st[12]);
                verbenDAO.insert(verben);
                vid++;
            }
        } catch (IOException e) { }
        //adjektiv
        adjektivDAO = AppDB.getInstance(mActivity).adjektivDAO();
        try {
            InputStreamReader is = new InputStreamReader(mActivity.getAssets().open("Adjektiv.tsv"));
            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            String[] st;
            int aid = 1;

            while ((line = reader.readLine()) != null) {
                st = line.split("\t");
                Adjektiv adjektiv = new Adjektiv();
                adjektiv.setAid(aid);
                adjektiv.setWord_adjektiv(st[0]);
                adjektiv.setMean_ko(st[1]);
                adjektiv.setMean_en(st[2]);
                adjektiv.setExample(st[3]);
                adjektiv.setExample_mean(st[4]);
                adjektivDAO.insert(adjektiv);
                aid++;
            }
        } catch (IOException e) { }
        //insert word
        wordDAO = AppDB.getInstance(mActivity).wordDAO();
        List<Word> wordList = new ArrayList<>();
        List<Nomen> nomenList = nomenDAO.getAll();
        for(Nomen value : nomenList){
            Word word = new Word();
            word.setWord(value.getArtikel()+ " " +value.getNomen());
            word.setMean(value.getMean_ko());
            word.setCategory(1);
            word.setCategory_id(value.getNid());
            wordList.add(word);
        }
        List<Verben> verbenList = verbenDAO.getAll();
        for(Verben value : verbenList){
            Word word = new Word();
            word.setWord(value.getVerb_wir());
            word.setMean(value.getMean_ko());
            word.setCategory(2);
            word.setCategory_id(value.getVid());
            wordList.add(word);
        }
        List<Adjektiv> adjektivList = adjektivDAO.getAll();
        for(Adjektiv value : adjektivList){
            Word word = new Word();
            word.setWord(value.getWord_adjektiv());
            word.setMean(value.getMean_ko());
            word.setCategory(3);
            word.setCategory_id(value.getAid());
            wordList.add(word);
        }
        wordDAO.insert(wordList);
    }
}
