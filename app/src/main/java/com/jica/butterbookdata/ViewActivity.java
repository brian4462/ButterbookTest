package com.jica.butterbookdata;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jica.butterbookdata.adapter.FragmentPageAdapter;
import com.jica.butterbookdata.database.entity.Nomen;
import com.jica.butterbookdata.fragmentpage.Page1_Home;
import com.jica.butterbookdata.fragmentpage.Page2_Word;
import com.jica.butterbookdata.fragmentpage.Page3_Bookmark;
import com.jica.butterbookdata.fragmentpage.Page4_Settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;

public class ViewActivity extends AppCompatActivity {
    FragmentPageAdapter pageAdapter;
    @BindView(R.id.vp_horizontal_ntb)
    ViewPager viewPager;
    @BindView(R.id.ntb_horizontal)
    NavigationTabBar navigationTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_view);
        ButterKnife.bind(this);

        initUI();
        setDB();
    }

    private void setDB() {
        List<Nomen> nomenList = new ArrayList<>();
        try {
            InputStreamReader is = new InputStreamReader(getAssets().open("Nomen.tsv"));
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
        } catch (IOException e) { }
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
                        .title("HOME")
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
            public void onPageSelected(final int position) {
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
}
