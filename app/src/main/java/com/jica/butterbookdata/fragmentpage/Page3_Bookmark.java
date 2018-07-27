package com.jica.butterbookdata.fragmentpage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jica.butterbookdata.R;
import com.jica.butterbookdata.adapter.NomenAdapter;
import com.jica.butterbookdata.database.entity.Nomen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Page3_Bookmark extends Fragment {
    private List<Nomen> nomenList = new ArrayList<>();
    private NomenAdapter nomenAdapter = new NomenAdapter(getActivity(), nomenList);
    private Unbinder unbinder;

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tvHideWord)
    TextView tvHiedWord;
    @BindView(R.id.tvHideMean)
    TextView tvHideMean;

    @OnClick(R.id.tvHideWord)
    public void onHideWord(View view){
        if(tvHiedWord.getText()=="단어 숨기기"){
            nomenAdapter.showhideWord(1);
            tvHiedWord.setText("단어 보이기");
        } else {
            nomenAdapter.showhideWord(0);
            tvHiedWord.setText("단어 숨기기");
        }
    }
    @OnClick(R.id.tvHideMean)
    public void onHideMean(View view){
        if(tvHideMean.getText()=="뜻 숨기기"){
            nomenAdapter.showhideMean(1);
            tvHideMean.setText("뜻 보이기");
        } else {
            nomenAdapter.showhideMean(0);
            tvHideMean.setText("뜻 숨기기");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_3_bookmark,container,false);
        ButterKnife.bind(this,view);

        setNomenDataAdapter();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null)
            unbinder.unbind();
    }
    private void setNomenDataAdapter() {
        try {
            InputStreamReader is = new InputStreamReader(getActivity().getAssets().open("Nomen.tsv"));

            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            String[] st;
            while ((line = reader.readLine()) != null) {
                st = line.split("\t");
                Nomen nomen = new Nomen();
                nomen.setArtikel(st[0]);
                nomen.setNomen(st[1]);
                nomen.setPlural(st[2]);
                nomen.setMean_ko(st[3]);
                nomen.setMean_en(st[4]);
                nomen.setExample(st[5]);
                nomen.setExample_mean(st[6]);
                nomenList.add(nomen);
            }
        } catch (IOException e) {

        }
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(nomenAdapter);
    }

}
