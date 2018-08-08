package com.jica.butterbookdata.fragmentpage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jica.butterbookdata.R;
import com.jica.butterbookdata.adapter.FragmentPageAdapter;
import com.jica.butterbookdata.adapter.SimpleSectionedRecyclerViewAdapter;
import com.jica.butterbookdata.adapter.WordAdapter;
import com.jica.butterbookdata.database.AppDB;
import com.jica.butterbookdata.database.dao.AdjektivDAO;
import com.jica.butterbookdata.database.dao.NomenDAO;
import com.jica.butterbookdata.database.dao.VerbenDAO;
import com.jica.butterbookdata.database.dao.WordDAO;
import com.jica.butterbookdata.database.entity.Word;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Page3_Bookmark extends Fragment {
    private List<Word> wordList = new ArrayList<>();
    private WordAdapter wordAdapter;
    private NomenDAO nomenDAO;
    private VerbenDAO verbenDAO;
    private AdjektivDAO adjektivDAO;
    private WordDAO wordDAO;
    private Unbinder unbinder;

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tvHideWord)
    TextView tvHiedWord;
    @BindView(R.id.tvHideMean)
    TextView tvHideMean;

    @OnClick(R.id.tvHideWord)
    public void onHideWord(View view){
        if(tvHiedWord.getText()=="단어 보이기"){
            wordAdapter.showhideWord(0);
            tvHiedWord.setText("단어 숨기기");
        } else {
            wordAdapter.showhideWord(1);
            tvHiedWord.setText("단어 보이기");
        }
    }
    @OnClick(R.id.tvHideMean)
    public void onHideMean(View view){
        if(tvHideMean.getText()=="뜻 보이기"){
            wordAdapter.showhideMean(0);
            tvHideMean.setText("뜻 숨기기");
        } else {
            wordAdapter.showhideMean(1);
            tvHideMean.setText("뜻 보이기");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_3_bookmark,container,false);
        ButterKnife.bind(this,view);

        getDB();
        setRecyclerView();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null)
            unbinder.unbind();
    }

    private void getDB(){
        nomenDAO = AppDB.getInstance(getActivity()).nomenDAO();
        verbenDAO = AppDB.getInstance(getActivity()).verbenDAO();
        adjektivDAO = AppDB.getInstance(getActivity()).adjektivDAO();
        wordDAO = AppDB.getInstance(getActivity()).wordDAO();
    }
    private void setRecyclerView() {
        /*
        List<Word> myList = wordDAO.getbyBookmark(2);
        List<Word> wordList = wordDAO.getbyBookmark(0);
        for(Word value:myList){
            wordList.add(value);
        }
        */
        wordAdapter = new WordAdapter(getActivity());
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //rv.setAdapter(wordAdapter);
        updateList();
    }

    private void updateList() {
        //This is the code to provide a sectioned list
        List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<>();
        List<Word> words = new ArrayList<>();
        int totalSize = 0;
        //그룹이 있는 프로필들
        List<String> group = new ArrayList<>();
        group.add(new String("Nomen"));
        group.add(new String("Verben"));
        group.add(new String("Adjektiv"));
        group.add(new String("My Word"));
        for (int i=0;i<group.size();i++) {

            List<Word> wordfiles = null;

            switch (i){
                case 0:{//Nomen
                    wordfiles = wordDAO.getAllbyCategory_Bookmark(1,0);
                    break;
                }
                case 1:{//Verben
                    wordfiles = wordDAO.getAllbyCategory_Bookmark(2,0);
                    break;
                }
                case 2:{//Adjektiv
                    wordfiles = wordDAO.getAllbyCategory_Bookmark(3,0);
                    break;
                }
                case 3:{//MyWords
                    wordfiles = wordDAO.getbyBookmark(2);
                    break;
                }
            }

            if (!wordfiles.isEmpty()) {
                sections.add(new SimpleSectionedRecyclerViewAdapter.Section(totalSize, group.get(i)));
                totalSize += wordfiles.size();
                words.addAll(wordfiles);
            }
        }
        if (!words.isEmpty()) wordAdapter.setWords(words);

        //Add your adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter =
                new SimpleSectionedRecyclerViewAdapter(getActivity(), R.layout.section,R.id.section_text, wordAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        rv.setAdapter(mSectionedAdapter);
    }
}
