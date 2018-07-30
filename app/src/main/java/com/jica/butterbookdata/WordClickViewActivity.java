package com.jica.butterbookdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WordClickViewActivity extends AppCompatActivity {
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

    @OnClick(R.id.ibClose)
    public void finishAct(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.recyclerview_click_view);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String[] itemValue = intent.getStringArrayExtra("ItemValue");
        tvTitle.setText(itemValue[1]+" "+itemValue[2]);
        tvWordMean_ko.setText(itemValue[4]);
        tvWordMean_en.setText(itemValue[5]);
        tvExample.setText(itemValue[6]);
        tvExampleMean.setText(itemValue[7]);
        tvArtikel.setText(itemValue[1]);
        tvPlural.setText(itemValue[3]);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_not_move_activity,R.anim.anim_slide_out_right);
    }
}
