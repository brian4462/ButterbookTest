package com.jica.butterbookdata.fragmentpage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jica.butterbookdata.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WordView extends Fragment {
    private Unbinder unbinder;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvWordMean_ko)
    TextView tvWordMean_ko;
    @BindView(R.id.tvWordMean_en)
    TextView tvWordMean_en;
    @BindView(R.id.tvExample)
    TextView tvExample;
    @BindView(R.id.tvExampleMean)
    TextView tvExampleMean;
    @BindView(R.id.tvArtikel)
    TextView tvArtikel;
    @BindView(R.id.tvPlural)
    TextView tvPlural;
    @BindView(R.id.ibClose)
    ImageButton ibClose;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_click_view,container,false);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder!=null)
            unbinder.unbind();
    }
    public static WordView newInstance() {
        WordView wv = new WordView();
        return wv;
    }
}
