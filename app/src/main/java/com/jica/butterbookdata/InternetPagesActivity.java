package com.jica.butterbookdata;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class InternetPagesActivity extends AppCompatActivity {
    @OnClick(R.id.deutschNetworkPageLayout)
    public void callWeb_network(View view) {
        // 다른 액티비티 호출
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/507228512628183/"));
        startActivity(intent);
    }
    @OnClick(R.id.berlinReport_layout)
    public void callWeb_berlin(View view) {
        // 다른 액티비티 호출
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.berlinreport.com/"));
        startActivity(intent);
    }
    @OnClick(R.id.de_ko_layout)
    public void callWeb_de_ko(View view) {
        // 다른 액티비티 호출
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://overseas.mofa.go.kr/de-ko/index.do"));
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_internet_pages);
        ButterKnife.bind(this);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_not_move_activity,R.anim.anim_slide_out_right);
    }

}
