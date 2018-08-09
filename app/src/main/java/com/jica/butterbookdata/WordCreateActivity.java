package com.jica.butterbookdata;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.jica.butterbookdata.adapter.SearchAdapter;
import com.jica.butterbookdata.database.AppDB;
import com.jica.butterbookdata.database.dao.AdjektivDAO;
import com.jica.butterbookdata.database.dao.NomenDAO;
import com.jica.butterbookdata.database.dao.VerbenDAO;
import com.jica.butterbookdata.database.dao.WordDAO;
import com.jica.butterbookdata.database.entity.Adjektiv;
import com.jica.butterbookdata.database.entity.Nomen;
import com.jica.butterbookdata.database.entity.Verben;
import com.jica.butterbookdata.database.entity.Word;
import com.jica.butterbookdata.handler.BackPressCloseHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WordCreateActivity extends AppCompatActivity {
    NomenDAO nomenDAO;
    VerbenDAO verbenDAO;
    AdjektivDAO adjektivDAO;
    WordDAO wordDAO;
    SearchAdapter adapter;
    BackPressCloseHandler backPressCloseHandler;

    @BindView(R.id.autotext_Title)
    AutoCompleteTextView autotext_Title;
    //layout
    @BindView(R.id.buttonLayout)
    LinearLayout buttonLayout;
    @BindView(R.id.addLayout)
    LinearLayout addLayout;
    @BindView(R.id.nomenLayout)
    LinearLayout nomenLayout;
    @BindView(R.id.verbenLayout)
    LinearLayout verbenLayout;
    //edittext
    @BindView(R.id.etWordMean_ko)
    EditText etWordMean_ko;
    @BindView(R.id.etWordMean_en)
    EditText etWordMean_en;
    @BindView(R.id.etExample)
    EditText etExample;
    @BindView(R.id.etExample_Mean)
    EditText etExample_Mean;
    @BindView(R.id.etPlural)
    EditText etPlural;
    @BindView(R.id.etIch)
    EditText etIch;
    @BindView(R.id.etDu)
    EditText etDu;
    @BindView(R.id.etEr)
    EditText etEr;
    @BindView(R.id.etIhr)
    EditText etIhr;
    @BindView(R.id.etPrateritum)
    EditText etPrateritum;
    @BindView(R.id.etPartizip)
    EditText etPartizip;
    //radiobutton, checkbox
    @BindView(R.id.radiogroupArtikel)
    RadioGroup radiogroupArtikel;
    @BindView(R.id.rbder)
    RadioButton rbder;
    @BindView(R.id.rbdie)
    RadioButton rbdie;
    @BindView(R.id.rbdas)
    RadioButton rbdas;
    @BindView(R.id.cbHaben)
    CheckBox cbHaben;
    @BindView(R.id.cbSein)
    CheckBox cbSein;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_word_create);
        ButterKnife.bind(this);
        backPressCloseHandler = new BackPressCloseHandler(this);

        wordDAO = AppDB.getInstance(getApplicationContext()).wordDAO();
        nomenDAO = AppDB.getInstance(getApplicationContext()).nomenDAO();
        verbenDAO = AppDB.getInstance(getApplicationContext()).verbenDAO();
        adjektivDAO = AppDB.getInstance(getApplicationContext()).adjektivDAO();
        List<Word> items = wordDAO.getAll();
        List<String> searchItems = new ArrayList<>();
        for(Word word:items){
            searchItems.add(word.getWord());
        }

        adapter = new SearchAdapter(this,R.layout.recyclerview_search_row,searchItems);
        autotext_Title.setAdapter(adapter);
        autotext_Title.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hideKeyboard(WordCreateActivity.this);
                addLayout.setVisibility(View.VISIBLE);
                buttonLayout.setVisibility(View.GONE);
                Word word = wordDAO.get(adapterView.getItemAtPosition(i).toString());
                setWord(word);
            }
        });
        autotext_Title.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    String strWord = autotext_Title.getText().toString();
                    Word word = wordDAO.get(strWord);
                    if(word!=null){
                        setWord(word);
                        buttonLayout.setVisibility(View.GONE);
                        addLayout.setVisibility(View.VISIBLE);
                        hideKeyboard(WordCreateActivity.this);
                    }else {
                        etWordMean_ko.requestFocus();
                    }
                    return true;
                }
                return false;
            }
        });
    }
    public void hideKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),0);
    }

    private void setWord(Word word){
        switch (word.getCategory()){
            case 1:{
                nomenLayout.setVisibility(View.VISIBLE);
                verbenLayout.setVisibility(View.GONE);
                Nomen nomen = nomenDAO.get(word.getCategory_id());
                etWordMean_ko.setText(nomen.getMean_ko());
                etWordMean_en.setText(nomen.getMean_en());
                etExample.setText(nomen.getExample());
                etExample_Mean.setText(nomen.getExample_mean());
                switch (nomen.getArtikel()){
                    case "der":{
                        rbder.setChecked(true);
                        break;
                    }
                    case "die":{
                        rbdie.setChecked(true);
                        break;
                    }
                    case "das":{
                        rbdas.setChecked(true);
                        break;
                    }
                }
                etPlural.setText(nomen.getPlural());
                break;
            }
            case 2:{
                nomenLayout.setVisibility(View.GONE);
                verbenLayout.setVisibility(View.VISIBLE);
                Verben verben = verbenDAO.get(word.getCategory_id());
                etWordMean_ko.setText(verben.getMean_ko());
                etWordMean_en.setText(verben.getMean_en());
                etExample.setText(verben.getExample());
                etExample_Mean.setText(verben.getExample_mean());
                etIch.setText(verben.getVerb_ich());
                etDu.setText(verben.getVerb_du());
                etEr.setText(verben.getVerb_er_sie_es());
                etIhr.setText(verben.getVerb_ihr());
                etPrateritum.setText(verben.getPrateritum_ich());
                if(verben.getPartizip2_hilfsverb().equals("haben")){
                    cbHaben.setChecked(true);
                    cbSein.setChecked(false);
                }else if(verben.getPartizip2_hilfsverb().equals("sein")){
                    cbHaben.setChecked(false);
                    cbSein.setChecked(true);
                }else {
                    cbHaben.setChecked(true);
                    cbSein.setChecked(true);
                }
                etPartizip.setText(verben.getPartizip2());
                break;
            }
            case 3:{
                nomenLayout.setVisibility(View.GONE);
                verbenLayout.setVisibility(View.GONE);
                Adjektiv adjektiv = adjektivDAO.get(word.getCategory_id());
                etWordMean_ko.setText(adjektiv.getMean_ko());
                etWordMean_en.setText(adjektiv.getMean_en());
                etExample.setText(adjektiv.getExample());
                etExample_Mean.setText(adjektiv.getExample_mean());
                break;
            }
        }
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_not_move_activity,R.anim.anim_slide_out_right);
    }
    @OnClick(R.id.ibClose)
    public void finishAct(){
        autotext_Title.setText("");
        etWordMean_ko.setText("");
        etWordMean_en.setText("");
        etExample.setText("");
        etExample_Mean.setText("");
        radiogroupArtikel.clearCheck();
        etPlural.setText("");
        etIch.setText("");
        etDu.setText("");
        etEr.setText("");
        etIhr.setText("");
        etPrateritum.setText("");
        cbHaben.setChecked(false);
        cbSein.setChecked(false);
        etPartizip.setText("");
        nomenLayout.setVisibility(View.GONE);
        verbenLayout.setVisibility(View.GONE);
        buttonLayout.setVisibility(View.VISIBLE);
        addLayout.setVisibility(View.GONE);
        hideKeyboard(WordCreateActivity.this);
        autotext_Title.requestFocus();
        onBackPressed();
    }
    @OnClick(R.id.btnNomen)
    public void onShowNomenLayout(){
        nomenLayout.setVisibility(View.VISIBLE);
        verbenLayout.setVisibility(View.GONE);
        addLayout.setVisibility(View.VISIBLE);
        buttonLayout.setVisibility(View.GONE);
    }
    @OnClick(R.id.btnVerben)
    public void onShowVerbenLayout(){
        nomenLayout.setVisibility(View.GONE);
        verbenLayout.setVisibility(View.VISIBLE);
        addLayout.setVisibility(View.VISIBLE);
        buttonLayout.setVisibility(View.GONE);
    }
    @OnClick(R.id.btnAdjektiv)
    public void onShowAdjektivLayout(){
        nomenLayout.setVisibility(View.GONE);
        verbenLayout.setVisibility(View.GONE);
        addLayout.setVisibility(View.VISIBLE);
        buttonLayout.setVisibility(View.GONE);
    }
    @OnClick(R.id.btnAdd)
    public void insertword(){
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(new Date());
        String strWord = autotext_Title.getText().toString();
        if(nomenLayout.getVisibility()==View.VISIBLE){
            if(autotext_Title.getText().toString().equals("")||etWordMean_ko.getText().toString().equals("")||etPlural.getText().toString().equals("")){
                Toast.makeText(this, "필수 입력란이 비어있습니다.", Toast.LENGTH_SHORT).show();
            }else{
                String artikel = strWord.substring(0,4);
                if(artikel.equals("der ")||artikel.equals("die ")||artikel.equals("das ")){
                    String strNomen = strWord.substring(4,strWord.length());
                    Word word = wordDAO.get(strWord);
                    Nomen nomen = nomenDAO.get(strNomen);
                    if(nomen!=null){
                        //기존 데이터 추가
                        word.setDate(today);
                        word.setBookmark(0);
                        wordDAO.update(word);
                        Toast.makeText(this, word.getWord()+" 오늘의 단어와 단어장에 추가되었습니다", Toast.LENGTH_SHORT).show();
                    }else{
                        //단어 새로 생성
                        nomen = new Nomen();
                        nomen.setNomen(strNomen);
                        nomen.setMean_ko(etWordMean_ko.getText().toString());
                        nomen.setMean_en(etWordMean_en.getText().toString());
                        nomen.setExample(etExample.getText().toString());
                        nomen.setExample_mean(etExample_Mean.getText().toString());
                        nomen.setArtikel(artikel.substring(0,3));
                        nomen.setPlural(etPlural.getText().toString());
                        nomenDAO.insert(nomen);
                        nomen = nomenDAO.get(nomen.getNomen());
                        word = new Word();
                        word.setWord(nomen.getNomen());
                        word.setMean(nomen.getMean_ko());
                        word.setCategory(1);
                        word.setCategory_id(nomen.getNid());
                        word.setBookmark(2);
                        word.setDate(today);
                        wordDAO.insert(word);
                        Toast.makeText(this, word.getWord()+" 오늘의 단어와 단어장에 추가되었습니다", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }else{
                    Toast.makeText(this, "Artikel의 입력이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    autotext_Title.requestFocus();
                }
            }
        }else if(verbenLayout.getVisibility()==View.VISIBLE){
            if(autotext_Title.getText().toString().equals("")||etWordMean_ko.getText().toString().equals("")||etPrateritum.getText().toString().equals("")
                    ||(!cbHaben.isChecked()&&!cbSein.isChecked())||etPartizip.getText().toString().equals("")){
                Toast.makeText(this, "필수 입력란이 비어있습니다.(haben, sein 체크 포함)", Toast.LENGTH_SHORT).show();
            }else{
                Word word = wordDAO.get(strWord);
                Verben verben = verbenDAO.get(strWord);
                if(verben!=null){
                    word.setDate(today);
                    word.setBookmark(0);
                    wordDAO.update(word);
                    Toast.makeText(this, word.getWord()+" 오늘의 단어와 단어장에 추가되었습니다", Toast.LENGTH_SHORT).show();
                }else {
                    //단어 새로 생성
                    verben = new Verben();
                    verben.setVerb_wir(strWord);
                    verben.setMean_ko(etWordMean_ko.getText().toString());
                    verben.setMean_en(etWordMean_en.getText().toString());
                    verben.setExample(etExample.getText().toString());
                    verben.setExample_mean(etExample_Mean.getText().toString());
                    verben.setVerb_ich(etIch.getText().toString());
                    verben.setVerb_du(etDu.getText().toString());
                    verben.setVerb_er_sie_es(etEr.getText().toString());
                    verben.setVerb_ihr(etIhr.getText().toString());
                    verben.setPrateritum_ich(etPrateritum.getText().toString());
                    if(cbHaben.isChecked()&&!cbSein.isChecked()){
                        verben.setPartizip2_hilfsverb(cbHaben.getText().toString());
                    }else if(!cbHaben.isChecked()&&cbSein.isChecked()){
                        verben.setPartizip2_hilfsverb(cbSein.getText().toString());
                    }else {
                        verben.setPartizip2_hilfsverb("sein, haben");
                    }
                    verben.setPartizip2(etPartizip.getText().toString());

                    verbenDAO.insert(verben);
                    verben = verbenDAO.get(verben.getVerb_wir());
                    word = new Word();
                    word.setWord(verben.getVerb_wir());
                    word.setMean(verben.getMean_ko());
                    word.setCategory(2);
                    word.setCategory_id(verben.getVid());
                    word.setBookmark(2);
                    word.setDate(today);
                    wordDAO.insert(word);
                    Toast.makeText(this, word.getWord()+" 오늘의 단어와 단어장에 추가되었습니다", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }else {
            if(autotext_Title.getText().toString().equals("")||etWordMean_ko.getText().toString().equals("")){
                Toast.makeText(this, "필수 입력란이 비어있습니다.", Toast.LENGTH_SHORT).show();
            }else{
                Word word = wordDAO.get(strWord);
                Adjektiv adjektiv = adjektivDAO.get(strWord);
                if(adjektiv!=null){
                    word.setDate(today);
                    word.setBookmark(0);
                    wordDAO.update(word);
                    Toast.makeText(this, word.getWord()+" 오늘의 단어와 단어장에 추가되었습니다", Toast.LENGTH_SHORT).show();
                }else {
                    //단어 새로 생성
                    adjektiv = new Adjektiv();
                    adjektiv.setWord_adjektiv(strWord);
                    adjektiv.setMean_ko(etWordMean_ko.getText().toString());
                    adjektiv.setMean_en(etWordMean_en.getText().toString());
                    adjektiv.setExample(etExample.getText().toString());
                    adjektiv.setExample_mean(etExample_Mean.getText().toString());

                    adjektivDAO.insert(adjektiv);
                    adjektiv = adjektivDAO.get(adjektiv.getWord_adjektiv());
                    word = new Word();
                    word.setWord(adjektiv.getWord_adjektiv());
                    word.setMean(adjektiv.getMean_ko());
                    word.setCategory(3);
                    word.setCategory_id(adjektiv.getAid());
                    word.setBookmark(2);
                    word.setDate(today);
                    wordDAO.insert(word);
                    Toast.makeText(this, word.getWord()+" 오늘의 단어와 단어장에 추가되었습니다", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
    }
    public void onBookmarkDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("단어장 추가");
        builder.setIcon(R.drawable.ic_book_24dp);
        builder.setMessage("이 단어를 단어장에도 추가할까요?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}