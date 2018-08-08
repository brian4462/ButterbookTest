package com.jica.butterbookdata.fragmentpage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jica.butterbookdata.QuizActivity;
import com.jica.butterbookdata.R;
import com.jica.butterbookdata.WordCreateActivity;
import com.jica.butterbookdata.adapter.TodayWordAdapter;
import com.jica.butterbookdata.adapter.WordAdapter;
import com.jica.butterbookdata.database.AppDB;
import com.jica.butterbookdata.database.dao.AdjektivDAO;
import com.jica.butterbookdata.database.dao.NomenDAO;
import com.jica.butterbookdata.database.dao.VerbenDAO;
import com.jica.butterbookdata.database.dao.WordDAO;
import com.jica.butterbookdata.database.entity.Verben;
import com.jica.butterbookdata.database.entity.Word;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Page2_Word extends Fragment {
    private SharedPreferences settings;
    private int NOMEN_COUNT;
    private int VERBEN_COUNT;
    private int ADJEKTIV_COUNT;
    private Unbinder unbinder;
    private List<Word> wordList = new ArrayList<>();
    private TodayWordAdapter wordAdapter;
    private NomenDAO nomenDAO;
    private VerbenDAO verbenDAO;
    private AdjektivDAO adjektivDAO;
    private WordDAO wordDAO;

    @BindView(R.id.rvToday)
    RecyclerView rvToday;
    @BindView(R.id.btnCreateQuiz)
    Button btnCreateQuiz;
    @BindView(R.id.btnAddWord)
    Button btnAddWord;

    @OnClick(R.id.btnAddWord)
    public void onCreateWord(){
        Intent intent = new Intent(getActivity(), WordCreateActivity.class);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_not_move_activity);
    }
    @OnClick(R.id.btnCreateQuiz)
    public void onCreateQuiz(){
        Intent intent = new Intent(getActivity(), QuizActivity.class);
        getActivity().startActivity(intent);
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(new Date());
        if(wordDAO.getbyStudy(0,today)==null||wordDAO.getbyStudy(0,today).size()==0){
            for(Word value:wordList){
                value.setStudy(0);
                wordDAO.update(value);
            }
        }
        getActivity().overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_not_move_activity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_2_word,container,false);
        ButterKnife.bind(this,view);
        settings = getActivity().getSharedPreferences("myPref",0);
        NOMEN_COUNT = settings.getInt("NomenCount",5);
        VERBEN_COUNT = settings.getInt("VerbenCount",3);
        ADJEKTIV_COUNT = settings.getInt("AdjektivCount",2);

        getDB();
        setRecyclerview();

        return view;
    }

    private void setRecyclerview() {
        //오늘날짜로 저장된 단어리스트가 있는지 검색 하고 없으면 새로 만든다.
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(new Date());
        wordList = wordDAO.getbyDate(today);

        if(wordList==null || wordList.size()==0){
            List<Word> nomenList = wordDAO.getAllbyCategory(1,1,NOMEN_COUNT);
            List<Word> verbenList = wordDAO.getAllbyCategory(2,1,VERBEN_COUNT);
            List<Word> adjektivList = wordDAO.getAllbyCategory(3,1,ADJEKTIV_COUNT);

            for(Word value : nomenList){
                value.setDate(today);
                wordDAO.update(value);
                wordList.add(value);
            }
            for(Word value : verbenList){
                value.setDate(today);
                wordDAO.update(value);
                wordList.add(value);
            }
            for(Word value : adjektivList){
                value.setDate(today);
                wordDAO.update(value);
                wordList.add(value);
            }
        }
        wordAdapter = new TodayWordAdapter(getActivity(),wordList);
        rvToday.setHasFixedSize(true);
        rvToday.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvToday.setAdapter(wordAdapter);
    }

    private void getDB() {
        nomenDAO = AppDB.getInstance(getActivity()).nomenDAO();
        verbenDAO = AppDB.getInstance(getActivity()).verbenDAO();
        adjektivDAO = AppDB.getInstance(getActivity()).adjektivDAO();
        wordDAO = AppDB.getInstance(getActivity()).wordDAO();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null)
            unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        for(Word word:wordList){
            Log.d("TAG1",word.toString());
        }
    }
}
