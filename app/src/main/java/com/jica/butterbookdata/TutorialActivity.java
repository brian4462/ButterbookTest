package com.jica.butterbookdata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TutorialActivity extends AppCompatActivity {
    @OnClick(R.id.btnFinishTutorial)
    public void onFinishTutorial(){
        finish();
    }
    @BindView(R.id.tutorial1)
    TextView tutorial1;
    @BindView(R.id.tutorial2)
    LinearLayout tutorial2;
    @BindView(R.id.tutorial3)
    LinearLayout tutorial3;
    @BindView(R.id.tutorial4)
    LinearLayout tutorial4;
    @BindView(R.id.tutorial5)
    LinearLayout tutorial5;
    @BindView(R.id.tutorial6)
    LinearLayout tutorial6;
    @BindView(R.id.tutorial8)
    TextView tutorial8;
    @BindView(R.id.tutorial9)
    TextView tutorial9;
    @BindView(R.id.btnFinishTutorial)
    Button btnFinishTutorial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_tutorial);
        ButterKnife.bind(this);

        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.anim_show_alpha_and_trans2);
        Animation animation2 = AnimationUtils.loadAnimation(this,R.anim.anim_show_alpha);
        Animation animation3 = AnimationUtils.loadAnimation(this,R.anim.anim_show_alpha_and_trans2);
        Animation animation4 = AnimationUtils.loadAnimation(this,R.anim.anim_show_alpha_and_trans2);
        Animation animation5 = AnimationUtils.loadAnimation(this,R.anim.anim_show_alpha_and_trans2);
        Animation animation6 = AnimationUtils.loadAnimation(this,R.anim.anim_show_alpha_and_trans2);
        Animation animation8 = AnimationUtils.loadAnimation(this,R.anim.anim_show_alpha_and_trans);
        Animation animation9 = AnimationUtils.loadAnimation(this,R.anim.anim_show_alpha_and_trans);
        Animation animation10 = AnimationUtils.loadAnimation(this,R.anim.anim_show_alpha);
        animation10.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                btnFinishTutorial.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation2.setStartOffset(1000);
        animation3.setStartOffset(1500);
        animation4.setStartOffset(2000);
        animation5.setStartOffset(2500);
        animation6.setStartOffset(3000);
        animation8.setStartOffset(1500);
        animation9.setStartOffset(1500);
        animation10.setStartOffset(1500);
        tutorial1.startAnimation(animation1);
        tutorial2.startAnimation(animation2);
        tutorial3.startAnimation(animation3);
        tutorial4.startAnimation(animation4);
        tutorial5.startAnimation(animation5);
        tutorial6.startAnimation(animation6);
        tutorial8.startAnimation(animation8);
        tutorial9.startAnimation(animation9);
        btnFinishTutorial.startAnimation(animation10);
    }
}
