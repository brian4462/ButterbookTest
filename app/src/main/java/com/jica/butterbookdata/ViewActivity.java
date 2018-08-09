package com.jica.butterbookdata;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jica.butterbookdata.adapter.FragmentPageAdapter;
import com.jica.butterbookdata.database.dao.AdjektivDAO;
import com.jica.butterbookdata.database.dao.NomenDAO;
import com.jica.butterbookdata.database.dao.PrapositionDAO;
import com.jica.butterbookdata.database.dao.VerbenDAO;
import com.jica.butterbookdata.fragmentpage.Page1_Home;
import com.jica.butterbookdata.fragmentpage.Page2_Word;
import com.jica.butterbookdata.fragmentpage.Page3_Bookmark;
import com.jica.butterbookdata.fragmentpage.Page4_Settings;
import com.jica.butterbookdata.thread.ReadFileToDB;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;

public class ViewActivity extends AppCompatActivity {
    private NomenDAO nomenDAO;
    private VerbenDAO verbenDAO;
    private AdjektivDAO adjektivDAO;
    private FragmentPageAdapter pageAdapter;
    private ReadFileToDB readFileToDB;
    private int ispageChange;

    @BindView(R.id.vp_horizontal_ntb)
    ViewPager viewPager;
    @BindView(R.id.ntb_horizontal)
    NavigationTabBar navigationTabBar;

    @Override
    protected void onResume() {
        super.onResume();
        pageAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_view);
        ButterKnife.bind(this);
        ispageChange = 1;
        initUI();

        //파일을 db에 저장(한번만 실행)
        SharedPreferences preferences = getSharedPreferences("myPref",0);
        int tutorialviewshow = preferences.getInt("CreateDB",0);
        if(tutorialviewshow != 1){
            readFileToDB = new ReadFileToDB(this);
            readFileToDB.start();
            //moveFragment(3);
            //Toast.makeText(this, "데이터를 내려받기 위해 첫실행에만 설정화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,TutorialActivity.class);
            startActivity(intent);
        }

    }

    private void initUI() {
        pageAdapter = new FragmentPageAdapter(getSupportFragmentManager());
        pageAdapter.addFragment(new Page1_Home());
        pageAdapter.addFragment(new Page2_Word());
        pageAdapter.addFragment(new Page3_Bookmark());
        pageAdapter.addFragment(new Page4_Settings());
        viewPager.setAdapter(pageAdapter);
        //네비게이션 탭바 배경색 배열
        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_home_black_24dp),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_home_click_24dp))
                        .title("Home")
                        //.badgeTitle("")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_title_black_24dp),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_title_click_24dp))
                        .title("Word")
                        //.badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_bookmark_border_black_24dp),
                        Color.parseColor(colors[2]))
                        //.selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title("Bookmark")
                        //.badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_settings_black_24dp),
                        Color.parseColor(colors[3]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Settings")
                        //.badgeTitle("icon")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {
            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }
    public void moveFragment(int i){
        viewPager.setCurrentItem(i);
    }
}
