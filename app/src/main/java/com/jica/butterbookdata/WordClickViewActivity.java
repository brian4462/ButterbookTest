package com.jica.butterbookdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jica.butterbookdata.adapter.WordAdapter;
import com.jica.butterbookdata.database.AppDB;
import com.jica.butterbookdata.database.dao.WordDAO;
import com.jica.butterbookdata.database.entity.Word;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WordClickViewActivity extends AppCompatActivity {
    private long backKeyPressedTime = 0;
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
    @BindView(R.id.ibClose)
    ImageButton ibClose;
    @BindView(R.id.tvclick)
    TextView tvClick;
    @BindView(R.id.ibBookmark_click)
    ImageButton ibBookmark;
    @BindView(R.id.tvRecord)
    TextView tvRecord;
    @BindView(R.id.ibDelete)
    ImageButton ibDelete;

    //nomen
    @BindView(R.id.nomenLayout)
    LinearLayout nomenLayout;
    @BindView(R.id.tvArtikel)
    TextView tvArtikel;
    @BindView(R.id.tvPlural)
    TextView tvPlural;

    //verben
    @BindView(R.id.verbenLayout)
    LinearLayout verbenLayout;
    @BindView(R.id.tvIch)
    TextView tvIch;
    @BindView(R.id.tvDu)
    TextView tvDu;
    @BindView(R.id.tvEr)
    TextView tvEr;
    @BindView(R.id.tvIhr)
    TextView tvIhr;
    @BindView(R.id.tvPrateritum)
    TextView tvPrateritum;
    @BindView(R.id.tvHilfsverb)
    TextView tvHilfsverb;
    @BindView(R.id.tvPartizip)
    TextView tvPartizip;

    WordDAO wordDao;
    int wordID;

    @OnClick(R.id.ibDelete)
    public void onDeleteWord(View view){
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "한번더 클릭하면 삭제됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            Word word = wordDao.get(wordID);
            wordDao.delete(word);
            finish();
            Toast.makeText(this, word.getWord()+" 가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.ibBookmark_click)
    public void onChangeBookmark(View view){
        Word word = wordDao.get(wordID);
        int bookmarkState = word.getBookmark();
        switch (bookmarkState){
            case 0:
                word.setBookmark(1);
                ibBookmark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                break;
            case 1:
                word.setBookmark(0);
                ibBookmark.setImageResource(R.drawable.ic_bookmark_white_24dp);
                break;
            case 2:
                Toast.makeText(this, "내가 추가한 단어는 북마크설정이 불가능합니다.", Toast.LENGTH_SHORT).show();
                break;
        }
        wordDao.update(word);
    }
    @OnClick(R.id.ibClose)
    public void finishAct(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.recyclerview_click_view);
        ButterKnife.bind(this);
        wordDao = AppDB.getInstance(getApplicationContext()).wordDAO();

        Intent intent = getIntent();
        String[] itemValue = intent.getStringArrayExtra("ItemValue");
        int category = Integer.parseInt(itemValue[itemValue.length-1]);
        wordID = Integer.parseInt(itemValue[0]);
        switch(category){
            case 1:{
                setNomenLayout(itemValue);
                break;
            }
            case 2:{
                setVerbenLayout(itemValue);
                break;
            }
            case 3:{
                setAdjektivLayout(itemValue);
                break;
            }
        }
    }

    private void getBookmark(int isBookmarked) {
        switch (isBookmarked){
            case 0: //yes
                ibBookmark.setImageResource(R.drawable.ic_bookmark_white_24dp);
                break;
            case 1: //no
                ibBookmark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                break;
            case 2: //my
                ibBookmark.setImageResource(R.drawable.ic_bookmark_white_24dp);
                ibBookmark.setVisibility(View.GONE);
                ibDelete.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setAdjektivLayout(String[] itemValue) {
        nomenLayout.setVisibility(View.GONE);
        verbenLayout.setVisibility(View.GONE);

        tvTitle.setText(itemValue[1]);
        tvWordMean_ko.setText(itemValue[2]);
        tvWordMean_en.setText(itemValue[3]);
        tvExample.setText(itemValue[4]);
        tvExampleMean.setText(itemValue[5]);
        tvClick.setText("이 단어를 " + itemValue[9] + "번 클릭하셨습니다.");
        int quizState = Integer.parseInt(itemValue[10]);
        if(quizState==0){
            tvRecord.setVisibility(View.VISIBLE);
            tvRecord.setText(itemValue[8]+"에 퀴즈를 풀었습니다.");
        }
        int isBookmarked = Integer.parseInt(itemValue[6]);
        getBookmark(isBookmarked);
    }

    private void setVerbenLayout(String[] itemValue) {
        nomenLayout.setVisibility(View.GONE);
        verbenLayout.setVisibility(View.VISIBLE);

        tvTitle.setText(itemValue[1]);
        tvWordMean_ko.setText(itemValue[10]);
        tvWordMean_en.setText(itemValue[11]);
        tvExample.setText(itemValue[12]);
        tvExampleMean.setText(itemValue[13]);

        tvIch.setText(itemValue[2]);
        tvDu.setText(itemValue[3]);
        tvEr.setText(itemValue[4]);
        tvIhr.setText(itemValue[5]);
        tvPrateritum.setText(itemValue[7]);
        tvHilfsverb.setText(itemValue[8]);
        tvPartizip.setText(itemValue[9]);
        tvClick.setText("이 단어를 " + itemValue[17] + "번 클릭하셨습니다.");
        int quizState = Integer.parseInt(itemValue[18]);
        if(quizState==0){
            tvRecord.setVisibility(View.VISIBLE);
            tvRecord.setText(itemValue[16]+"에 퀴즈를 풀었습니다.");
        }
        int isBookmarked = Integer.parseInt(itemValue[14]);
        getBookmark(isBookmarked);
    }

    private void setNomenLayout(String[] itemValue) {
        nomenLayout.setVisibility(View.VISIBLE);
        verbenLayout.setVisibility(View.GONE);

        tvTitle.setText(itemValue[1]+" "+itemValue[2]);
        tvWordMean_ko.setText(itemValue[4]);
        tvWordMean_en.setText(itemValue[5]);
        tvExample.setText(itemValue[6]);
        tvExampleMean.setText(itemValue[7]);
        tvArtikel.setText(itemValue[1]);
        tvPlural.setText(itemValue[3]);
        tvClick.setText("이 단어를 " + itemValue[11] + "번 클릭하셨습니다.");
        int quizState = Integer.parseInt(itemValue[12]);
        if(quizState==0){
            tvRecord.setVisibility(View.VISIBLE);
            tvRecord.setText(itemValue[10]+"에 퀴즈를 풀었습니다.");
        }
        int isBookmarked = Integer.parseInt(itemValue[8]);
        getBookmark(isBookmarked);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_not_move_activity,R.anim.anim_slide_out_right);
    }
}
