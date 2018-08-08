package com.jica.butterbookdata;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hanks.library.AnimateCheckBox;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class QuizActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;
    private WordDAO wordDAO;
    private NomenDAO nomenDAO;
    private VerbenDAO verbenDAO;
    private AdjektivDAO adjektivDAO;
    private List<Nomen> nomenList;
    private List<Verben> verbenList;
    private List<Adjektiv> adjektivList;
    private List<Word> wordList;
    private int[] correctList;
    private int cntnomen;
    private int cntverben;
    private int cntadjektiv;
    private int quizNum;
    private int cnt;
    private int finish;

    @BindView(R.id.tvPage)
    TextView tvPage;
    @BindView(R.id.tvQuizWord)
    TextView tvQuizWord;
    @BindView(R.id.radiogroupArtikel)
    RadioGroup radiogroupArtikel;
    @BindView(R.id.rgMean)
    RadioGroup rgMean;
    @BindView(R.id.rbMean1)
    RadioButton rbMean1;
    @BindView(R.id.rbMean2)
    RadioButton rbMean2;
    @BindView(R.id.rbMean3)
    RadioButton rbMean3;
    @BindView(R.id.btnQuizConfirm)
    ImageButton btnQuizConfirm;
    @BindView(R.id.etPlural)
    EditText etPlural;
    @BindView(R.id.correct_Artikel)
    TextView correct_Artikel;
    @BindView(R.id.correct_Mean)
    TextView correct_Mean;
    @BindView(R.id.correct_Plural)
    TextView correct_Plural;
    @BindView(R.id.tvNotice)
    TextView tvNotice;
    @BindView(R.id.btnNext)
    ImageButton btnNext;
    @BindView(R.id.tvFirst)
    TextView tvFirst;
    @BindView(R.id.tvSecond)
    TextView tvSecond;
    @BindView(R.id.tvThird)
    TextView tvThird;
    @BindView(R.id.nomenLayout)
    LinearLayout nomenLayout;
    @BindView(R.id.verbenLayout)
    LinearLayout verbenLayout;
    @BindView(R.id.adjektivLayout)
    LinearLayout adjektivLayout;
    //verben
    @BindView(R.id.tvQuiz_first_verben)
    TextView tvQuiz_first_verben;
    @BindView(R.id.tvQuiz_first_verben_correct)
    TextView tvQuiz_first_verben_correct;
    @BindView(R.id.rgVerben_Mean)
    RadioGroup rgVerben_Mean;
    @BindView(R.id.rbVerben_Mean1)
    RadioButton rbVerben_Mean1;
    @BindView(R.id.rbVerben_Mean2)
    RadioButton rbVerben_Mean2;
    @BindView(R.id.rbVerben_Mean3)
    RadioButton rbVerben_Mean3;
    @BindView(R.id.tvQuiz_second_verben)
    TextView tvQuiz_second_verben;
    @BindView(R.id.tvQuiz_second_verben_correct)
    TextView tvQuiz_second_verben_correct;
    @BindView(R.id.cbHaben)
    CheckBox cbHaben;
    @BindView(R.id.cbSein)
    CheckBox cbSein;
    @BindView(R.id.etPartizip)
    EditText etPartizip;
    @BindView(R.id.tvQuiz_third_verben)
    TextView tvQuiz_third_verben;
    @BindView(R.id.tvQuiz_third_verben_correct)
    TextView tvQuiz_third_verben_correct;
    @BindView(R.id.etPrateritum)
    EditText etPrateritum;
    //adjekitv
    @BindView(R.id.tvQuiz_first_adjektiv)
    TextView tvQuiz_first_adjektiv;
    @BindView(R.id.tvQuiz_first_adjektiv_correct)
    TextView tvQuiz_first_adjektiv_correct;
    @BindView(R.id.rgAdjektiv_Mean)
    RadioGroup rgAdjektiv_Mean;
    @BindView(R.id.rbAdjektiv_Mean1)
    RadioButton rbAdjektiv_Mean1;
    @BindView(R.id.rbAdjektiv_Mean2)
    RadioButton rbAdjektiv_Mean2;
    @BindView(R.id.rbAdjektiv_Mean3)
    RadioButton rbAdjektiv_Mean3;
    //layout, result
    @BindView(R.id.quizLayout)
    LinearLayout quizLayout;
    @BindView(R.id.infoLayout)
    LinearLayout infoLayout;
    @BindView(R.id.resultLayout)
    LinearLayout resultLayout;
    @BindView(R.id.correctLayout)
    LinearLayout correctLayout;
    @BindView(R.id.incorrectLayout)
    LinearLayout incorrectLayout;
    @BindView(R.id.tvResult)
    TextView tvResult;

    @OnClick(R.id.btnFinish)
    public void onFinishQuiz(View view){
        finish = 0;
        finish();
    }
    @OnClick(R.id.btnQuizClose)
    public void onCloseQuiz(View view){
        onBackPressed();
    }
    //다음 버튼 클릭
    @OnClick(R.id.btnNext)
    public void onNextQuiz(View view){
        if(correctList.length==cnt) {
            showResult();
        }
        if(cntnomen>=nomenList.size()&&cntverben>=verbenList.size()&&cntadjektiv<adjektivList.size()){
            cntadjektiv++;
            cnt++;
            makeQuiz();
        }
        if(cntnomen>=nomenList.size()&&cntverben<verbenList.size()){
            cntverben++;
            cnt++;
            makeQuiz();
        }
        if(cntnomen<nomenList.size()){
            cntnomen++;
            cnt++;
            makeQuiz();
        }

    }
    //정답 확인 버튼
    @OnClick(R.id.btnQuizConfirm)
    public void onConfirm(View view){
        /**
         * Nomen 확인
         */
        if(cntnomen<nomenList.size()){
            //Artikel
            int rbid = radiogroupArtikel.getCheckedRadioButtonId();
            RadioButton rb = null;
            String answer_Artikel ="";
            if(rbid!=-1){
                rb = (RadioButton)findViewById(rbid);
                answer_Artikel = rb.getText().toString();
            }
            correct_Artikel.setVisibility(View.VISIBLE);
            if(!correct_Artikel.getText().equals(answer_Artikel)){
                correct_Artikel.setTextColor(Color.parseColor("#A20000"));
                correctList[cntnomen] = 1;
            }else {
                correct_Artikel.setTextColor(Color.parseColor("#ffa940"));
            }
            //Mean
            int rbid2 = rgMean.getCheckedRadioButtonId();
            RadioButton rb2 = null;
            String answer_Mean="";
            if(rbid2!=-1){
                rb2 = (RadioButton)findViewById(rbid2);
                answer_Mean = rb2.getText().toString();
            }
            correct_Mean.setVisibility(View.VISIBLE);
            if(!correct_Mean.getText().equals(answer_Mean)){
                correct_Mean.setTextColor(Color.parseColor("#A20000"));
                correctList[cntnomen] = 1;
            }else {
                correct_Mean.setTextColor(Color.parseColor("#ffa940"));
            }

            //Plural
            String answer_Plural = etPlural.getText().toString().trim();
            correct_Plural.setVisibility(View.VISIBLE);
            if(!correct_Plural.getText().equals(answer_Plural)){
                correct_Plural.setTextColor(Color.parseColor("#A20000"));
                correctList[cntnomen] = 1;
            }else {
                correct_Plural.setTextColor(Color.parseColor("#ffa940"));
            }
            //etc
            tvNotice.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnQuizConfirm.setVisibility(View.GONE);
            Word word = wordDAO.get(1,nomenList.get(cntnomen).getNid());
            if(correctList[cntnomen]==0){
                word.setQuizfinish(2);//correct
            }else {
                word.setQuizfinish(3);//wrong
            }
            wordDAO.update(word);
        }
        /**
         * Verben 확인
         */
        if(cntnomen>=nomenList.size()&&cntverben<verbenList.size()){
            //Mean
            int rbid = rgVerben_Mean.getCheckedRadioButtonId();
            RadioButton rb = null;
            String answer_Mean="";
            if(rbid!=-1){
                rb = (RadioButton)findViewById(rbid);
                answer_Mean = rb.getText().toString();
            }
            tvQuiz_first_verben_correct.setVisibility(View.VISIBLE);
            if(!tvQuiz_first_verben_correct.getText().equals(answer_Mean)){
                tvQuiz_first_verben_correct.setTextColor(Color.parseColor("#A20000"));
                correctList[cntnomen+cntverben] = 1;
            }else {
                tvQuiz_first_verben_correct.setTextColor(Color.parseColor("#ffa940"));
            }
            //Partzip
            tvQuiz_second_verben_correct.setVisibility(View.VISIBLE);
            String answer_Partizip_hilfsverb="";
            String answer_Partizip="";
            if(cbHaben.isChecked()&&!cbSein.isChecked()){
                answer_Partizip_hilfsverb=cbHaben.getText().toString();
            }else if(!cbHaben.isChecked()&&cbSein.isChecked()){
                answer_Partizip_hilfsverb=cbSein.getText().toString();
            }else if(cbHaben.isChecked()&&cbSein.isChecked()){
                answer_Partizip_hilfsverb=cbSein.getText().toString()+", "+cbHaben.getText().toString();
            }
            answer_Partizip = etPartizip.getText().toString();
            String partizip = "("+answer_Partizip_hilfsverb+") "+answer_Partizip;

            if(!tvQuiz_second_verben_correct.getText().equals(partizip)){
                tvQuiz_second_verben_correct.setTextColor(Color.parseColor("#A20000"));
                correctList[cntnomen+cntverben] = 1;
            }else {
                tvQuiz_second_verben_correct.setTextColor(Color.parseColor("#ffa940"));
            }
            //Prateritum
            tvQuiz_third_verben_correct.setVisibility(View.VISIBLE);
            String answer_Prateritum = "";
            answer_Prateritum = etPrateritum.getText().toString();
            if(!tvQuiz_third_verben_correct.getText().equals(answer_Prateritum)){
                tvQuiz_third_verben_correct.setTextColor(Color.parseColor("#A20000"));
                correctList[cntnomen+cntverben] = 1;
            }else {
                tvQuiz_third_verben_correct.setTextColor(Color.parseColor("#ffa940"));
            }
            //etc
            tvNotice.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnQuizConfirm.setVisibility(View.GONE);
            Word word = wordDAO.get(2,verbenList.get(cntverben).getVid());
            if(correctList[cntnomen+cntverben]==0){
                word.setQuizfinish(2);//correct
            }else {
                word.setQuizfinish(3);//wrong
            }
            wordDAO.update(word);
        }
        /**
         * Adjektiv 확인
         */
        if(cntnomen>=nomenList.size()&&cntverben>=verbenList.size()&&cntadjektiv<adjektivList.size()){
            //Mean
            int rbid = rgAdjektiv_Mean.getCheckedRadioButtonId();
            RadioButton rb = null;
            String answer_Mean="";
            if(rbid!=-1){
                rb = (RadioButton)findViewById(rbid);
                answer_Mean = rb.getText().toString();
            }
            tvQuiz_first_adjektiv_correct.setVisibility(View.VISIBLE);
            if(!tvQuiz_first_adjektiv_correct.getText().equals(answer_Mean)){
                tvQuiz_first_adjektiv_correct.setTextColor(Color.parseColor("#A20000"));
                correctList[cntnomen+cntverben+cntadjektiv] = 1;
            }else {
                tvQuiz_first_adjektiv_correct.setTextColor(Color.parseColor("#ffa940"));
            }
            //etc
            tvNotice.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnQuizConfirm.setVisibility(View.GONE);
            Word word = wordDAO.get(3,adjektivList.get(cntadjektiv).getAid());
            if(correctList[cntnomen+cntverben]==0){
                word.setQuizfinish(2);//correct
            }else {
                word.setQuizfinish(3);//wrong
            }
            wordDAO.update(word);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
        wordDAO = AppDB.getInstance(getApplicationContext()).wordDAO();
        nomenDAO = AppDB.getInstance(getApplicationContext()).nomenDAO();
        verbenDAO = AppDB.getInstance(getApplicationContext()).verbenDAO();
        adjektivDAO = AppDB.getInstance(getApplicationContext()).adjektivDAO();

        backPressCloseHandler = new BackPressCloseHandler(this);

        quizLayout.setVisibility(View.VISIBLE);
        infoLayout.setVisibility(View.VISIBLE);
        resultLayout.setVisibility(View.GONE);

        getWordList();
        cntnomen = 0;
        cntverben = 0;
        cntadjektiv = 0;
        quizNum = 0;
        cnt = 1;
        finish = 1;
        makeQuiz();
    }

    private void getWordList() {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(new Date());
        wordList = wordDAO.getbyStudy(0, today);
        correctList = new int[wordList.size()];
        nomenList = new ArrayList<>();
        verbenList = new ArrayList<>();
        adjektivList = new ArrayList<>();
        for(Word value:wordList){
            int category = value.getCategory();
            switch (category){
                case 1:{
                    Nomen nomen = nomenDAO.get(value.getCategory_id());
                    nomenList.add(nomen);
                    Collections.shuffle(nomenList);
                    break;
                }
                case 2:{
                    Verben verben = verbenDAO.get(value.getCategory_id());
                    verbenList.add(verben);
                    Collections.shuffle(verbenList);
                    break;
                }
                case 3:{
                    Adjektiv adjektiv = adjektivDAO.get(value.getCategory_id());
                    adjektivList.add(adjektiv);
                    Collections.shuffle(adjektivList);
                    break;
                }
            }
        }
    }
    private void showResult(){
        quizLayout.setVisibility(View.GONE);
        infoLayout.setVisibility(View.GONE);
        resultLayout.setVisibility(View.VISIBLE);
        int correct = 0;
        for(int i=0;i<correctList.length;i++){
            if(correctList[i]==0){
                correct++;
            }
        }
        tvResult.setText(correct+" / "+correctList.length);
        List<Word> correctWords = wordDAO.getByQuizState(2);
        List<Word> incorrectWords = wordDAO.getByQuizState(3);
        TextView[] correctTvs = new TextView[correctWords.size()];
        TextView[] incorrectTvs = new TextView[incorrectWords.size()];
        wordList.clear();
        for(int i = 0;i<correctWords.size();i++){
            correctTvs[i] = new TextView(getApplicationContext());
            correctTvs[i].setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            correctTvs[i].setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            correctTvs[i].setTextColor(getResources().getColor(R.color.correct));
            correctTvs[i].setText(correctWords.get(i).getWord());
            correctLayout.addView(correctTvs[i]);
            wordList.add(correctWords.get(i));
        }
        for(int i = 0;i<incorrectWords.size();i++){
            incorrectTvs[i] = new TextView(getApplicationContext());
            incorrectTvs[i].setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            incorrectTvs[i].setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
            incorrectTvs[i].setTextColor(getResources().getColor(R.color.wrong));
            incorrectTvs[i].setText(incorrectWords.get(i).getWord());
            incorrectLayout.addView(incorrectTvs[i]);
            if(incorrectWords.get(i).getBookmark()==1){
                incorrectWords.get(i).setBookmark(0);
            }
            wordList.add(incorrectWords.get(i));
        }


    }

    private void makeQuiz() {
        //nomen quiz
        if(cntnomen < nomenList.size()){
            nomenLayout.setVisibility(View.VISIBLE);
            verbenLayout.setVisibility(View.GONE);
            adjektivLayout.setVisibility(View.GONE);

            tvPage.setText((cnt)+"/"+wordList.size());
            Nomen nomen = nomenList.get(cntnomen);
            String artikel = nomen.getArtikel();
            String word = nomen.getNomen();
            String mean = nomen.getMean_ko();
            String plural = nomen.getPlural();

            correct_Artikel.setText(artikel);
            correct_Mean.setText(mean);
            correct_Plural.setText(plural);
            correct_Artikel.setVisibility(View.INVISIBLE);
            correct_Mean.setVisibility(View.INVISIBLE);
            correct_Plural.setVisibility(View.INVISIBLE);

            tvQuizWord.setText(word);
            radiogroupArtikel.clearCheck();
            rgMean.clearCheck();

            List<Nomen> nomens = nomenDAO.getAll();
            Collections.shuffle(nomens);
            if(nomen==nomens.get(0)||nomen==nomens.get(1)){
                Collections.shuffle(nomens);
            }
            List<String> means = new ArrayList<>();
            means.add(mean);
            means.add(nomens.get(0).getMean_ko());
            means.add(nomens.get(1).getMean_ko());
            Collections.shuffle(means);
            rbMean1.setText(means.get(0));
            rbMean2.setText(means.get(1));
            rbMean3.setText(means.get(2));
            etPlural.setText("");
            if(cnt==wordList.size()){
                tvNotice.setText("다음 버튼을 눌러 결과를 확인하세요");
            }
            tvNotice.setVisibility(View.INVISIBLE);

            quizNum++;
            tvFirst.setText(quizNum+".");
            quizNum++;
            tvSecond.setText(quizNum+".");
            quizNum++;
            tvThird.setText(quizNum+".");

            btnQuizConfirm.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
        }
        //verben quiz
        if(cntnomen>=nomenList.size()&&cntverben<verbenList.size()){
            nomenLayout.setVisibility(View.GONE);
            verbenLayout.setVisibility(View.VISIBLE);
            adjektivLayout.setVisibility(View.GONE);
            btnQuizConfirm.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);

            tvPage.setText((cnt)+"/"+wordList.size());
            tvNotice.setVisibility(View.INVISIBLE);
            Verben verben = verbenList.get(cntverben);

            //word
            tvQuizWord.setText(verben.getVerb_wir());
            //mean
            quizNum++;
            tvQuiz_first_verben.setText(quizNum+".");
            tvQuiz_first_verben_correct.setText(verben.getMean_ko());
            tvQuiz_first_verben_correct.setVisibility(View.INVISIBLE);
            rgVerben_Mean.clearCheck();
            List<Verben> verbens = verbenDAO.getAll();
            Collections.shuffle(verbens);
            if(verben==verbens.get(0)||verben==verbens.get(1)){
                Collections.shuffle(verbens);
            }
            List<String> means = new ArrayList<>();
            means.add(verben.getMean_ko());
            means.add(verbens.get(0).getMean_ko());
            means.add(verbens.get(1).getMean_ko());
            Collections.shuffle(means);
            rbVerben_Mean1.setText(means.get(0));
            rbVerben_Mean2.setText(means.get(1));
            rbVerben_Mean3.setText(means.get(2));
            //partizip
            quizNum++;
            tvQuiz_second_verben.setText(quizNum+".");
            tvQuiz_second_verben_correct.setText("("+verben.getPartizip2_hilfsverb()+") "+verben.getPartizip2());
            tvQuiz_second_verben_correct.setVisibility(View.INVISIBLE);
            cbHaben.setChecked(false);
            cbSein.setChecked(false);
            etPartizip.setText("");
            //prateritum
            quizNum++;
            tvQuiz_third_verben.setText(quizNum+".");
            tvQuiz_third_verben_correct.setText(verben.getPrateritum_ich());
            tvQuiz_third_verben_correct.setVisibility(View.INVISIBLE);
            etPrateritum.setText("");
            if(cnt==wordList.size()){
                tvNotice.setText("다음 버튼을 눌러 결과를 확인하세요");
            }
        }
        //adjektiv quiz
        if(cntnomen>=nomenList.size()&&cntverben>=verbenList.size()&&cntadjektiv<adjektivList.size()){
            nomenLayout.setVisibility(View.GONE);
            verbenLayout.setVisibility(View.GONE);
            adjektivLayout.setVisibility(View.VISIBLE);
            btnQuizConfirm.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);

            tvPage.setText((cnt)+"/"+wordList.size());
            if(cnt==wordList.size()){
                tvNotice.setText("다음 버튼을 눌러 결과를 확인하세요");
            }
            tvNotice.setVisibility(View.INVISIBLE);
            Adjektiv adjektiv = adjektivList.get(cntadjektiv);

            //word
            tvQuizWord.setText(adjektiv.getWord_adjektiv());
            //mean
            quizNum++;
            tvQuiz_first_adjektiv.setText(quizNum+".");
            tvQuiz_first_adjektiv_correct.setText(adjektiv.getMean_ko());
            tvQuiz_first_adjektiv_correct.setVisibility(View.INVISIBLE);
            rgAdjektiv_Mean.clearCheck();
            List<Adjektiv> adjektivs = adjektivDAO.getAll();
            Collections.shuffle(adjektivs);
            if(adjektiv==adjektivs.get(0)||adjektiv==adjektivs.get(1)){
                Collections.shuffle(adjektivs);
            }
            List<String> means = new ArrayList<>();
            means.add(adjektiv.getMean_ko());
            means.add(adjektivs.get(0).getMean_ko());
            means.add(adjektivs.get(1).getMean_ko());
            Collections.shuffle(means);
            rbAdjektiv_Mean1.setText(means.get(0));
            rbAdjektiv_Mean2.setText(means.get(1));
            rbAdjektiv_Mean3.setText(means.get(2));
        }
    }
    @Override
    public void finish() {
        super.finish();
        if(wordList!=null&&wordList.size()!=0){
            if(finish==0){
                for(Word value:wordList){
                    value.setQuizfinish(0);
                    value.setStudy(1);
                    wordDAO.update(value);
                }
            }else {
                for(Word value:wordList){
                    value.setQuizfinish(1);
                    value.setStudy(1);
                    wordDAO.update(value);
                }
            }
        }
        overridePendingTransition(R.anim.anim_not_move_activity,R.anim.anim_slide_out_right);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
