package com.jica.butterbookdata.fragmentpage;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jica.butterbookdata.R;
import com.jica.butterbookdata.ViewActivity;
import com.jica.butterbookdata.adapter.FragmentPageAdapter;
import com.jica.butterbookdata.database.AppDB;
import com.jica.butterbookdata.database.dao.WordDAO;
import com.jica.butterbookdata.database.entity.Proverb;
import com.jica.butterbookdata.database.entity.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Page1_Home extends Fragment {
    private WordDAO wordDAO;
    private List<Proverb> proverbs;
    private Unbinder unbinder;

    @BindView(R.id.tvStudyWord)
    TextView tvStudyWord;
    @BindView(R.id.tvMyWord)
    TextView tvMyWord;
    @BindView(R.id.tvAllWord)
    TextView tvAllWord;
    @BindView(R.id.tvProverb)
    TextView tvProverb;
    @BindView(R.id.tvProverbMean)
    TextView tvProverbMean;

    @OnClick(R.id.btnStartButterbook)
    public void onGotoToday(View view){
        ((ViewActivity)getActivity()).moveFragment(1);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_1_home,container,false);
        ButterKnife.bind(this,view);
        wordDAO = AppDB.getInstance(getActivity()).wordDAO();

        createProverb();
        Collections.shuffle(proverbs);

        List<Word> studyList = wordDAO.getByQuizState(0);
        List<Word> myList = wordDAO.getbyBookmark(2);
        List<Word> allList = wordDAO.getAll();

        tvStudyWord.setText(studyList.size()+"");
        tvMyWord.setText(myList.size()+"");
        tvAllWord.setText(allList.size()+"");

        Proverb proverb = proverbs.get(0);
        tvProverb.setText(proverb.getProverb());
        tvProverbMean.setText(proverb.getMean());

        return view;
    }

    private void createProverb() {
        proverbs = new ArrayList<>();
        proverbs.add(new Proverb("Der den Augenblick ergreift, Das ist der rechte Mann"
                ,"순간을 포작하는자, 그 사람이 옳은 사람이다."));
        proverbs.add(new Proverb("Die Tat ist alles, nichts ist der Ruhm."
                ,"행동이 모든 것이고, 명성은 아무 것도 아니다."));
        proverbs.add(new Proverb("Es bildet ein Talent sich in der Stille, Sich ein Charakter in dem Strom der Welt."
                ,"한 개의 재능은 정적 속에서 형성되고, 한 개의 성격은 세계의 흐름 속에서 형성된다."));
        proverbs.add(new Proverb("Nicht allein das Angeborene, auch das Erworbene ist der Mensch."
                ,"타고난 것만이 아니라, 습득된 것도 또한 인간이다."));
        proverbs.add(new Proverb("Der Zweck des Lebens ist das Leben selbst."
                ,"인생의 목적은 인생 그 자체이다."));
        proverbs.add(new Proverb("Ich vergesse, was dahinten ist, und strecke mich aus nach dem, was da vorne ist."
                ,"나는 지나간 일은 잊어버리고, 앞으로 다가올 일을 대비한다."));
        proverbs.add(new Proverb("Geduld ist ein Pflaster fuer viele Wunden."
                ,"인내는 많은 상처들을 위한 한 개의 반창고이다."));
        proverbs.add(new Proverb("Es gibt keine Grenzen, nur Moeglichkeiten."
                ,"한계란 없고, 단지 가능성만 있을 뿐이다."));
        proverbs.add(new Proverb("Es gibt Berge, ueber die man hinueber muss, sonst geht der Weg nicht weiter."
                ,"반드시 넘어야 할 산들이 있다. 그렇지 않으면 길은 더 이상 앞으로 나아가지 않는다."));
        proverbs.add(new Proverb("Wer die Zukunft fuerchtet, verdirbt sich die Gegenwart."
                ,"미래를 두려워하는 자는 자신의 현재를 망친다."));
        proverbs.add(new Proverb("Heute leben! Heute laechen! Heute gluecklich sein! Dein Herz mach frei."
                ,"오늘 살것! 오늘 울것! 오늘 행복할 것! 너의 심장을 자유롭게 만들어라."));
        proverbs.add(new Proverb("Deine Lebensfreude und dein Glueck brauchen nicht abhaengig zu sein von tausendundeiner Nichtigkeit."
                ,"너의 인생의 기쁨과 행복은 천 가지의 사호한 일에 좌우될 필요가 없다."));
        proverbs.add(new Proverb("Jeder Tag wird dir gereicht wie eine Ewigkeit, um gluecklich zu sein."
                ,"매일은 행복하게 되기 위해서, 한개의 영원처럼 너에게주어진다."));
        proverbs.add(new Proverb("Gluecklich, wer sich an nichts auf dieser Erde haengt."
                ,"지구 위의 그 어떤 것에서 집착하지 않는 자가 행복하다."));
        proverbs.add(new Proverb("Die wichtigste Stunde ist immer die Gegenwart. Der bedeutendste Mensch ist immer der, der gerade vor dir steht."
                ,"가장 중요한 시간은 항상 현재다. 가장 중요한 사람은 항상, 지금 바로 앞에 서 있는 그 사람이다."));
        proverbs.add(new Proverb("Die Mlide ist unsere Staerke. Sie loest alle Schwierigkeiten und beseitigt jedes Hindernis."
                ,"부드러움이 우리의 장점이다. 그것이 모든 어려운 문제들을 풀고, 모든 장애를 제거한다."));
        proverbs.add(new Proverb("Lebe gern und arbeite gern. Es gibt so viel Gutes zu tun. Liebe das Leben! Es ist der Muehe wert."
                ,"기꺼이 살고, 기거이 일하라. 해야 좋은 일들이 많이 있다. 인생을 사랑하라! 그것은 수고할 만한 가치가 있다."));
        proverbs.add(new Proverb("Das Harte und Starke wird fallen. Das Weiche und Schwache wird ueberdauern."
                ,"폭풍은 아침 한 나절도 지속되지 않고, 나쁜 날씨도 하루 종일 계속되는 법은 없다."));
        proverbs.add(new Proverb("Wenn die Nacht am dunkelsten, ist die Daemmerung am naechsten."
                ,"밤이 가장 어두우면, 여명이 가장 가깝다."));
        proverbs.add(new Proverb("Ich glaube, dass die Menschen einander immer noetig haben."
                ,"나는 사람들이 서로를 항상 필요로 한다고 믿는다."));
        proverbs.add(new Proverb("Wo ein Wille ist, ist auch ein Weg."
                ,"뜻이 있는 곳에 길이 있다."));
        proverbs.add(new Proverb("Soll sich unsere Gesellschaft aendern, so muss sich der einzelne aendern."
                ,"우리 사회가 바뀌자면, 각자가 변해야 한다."));
        proverbs.add(new Proverb("Jede Zeit hat ihre Aufgabe, und durch die Loesung derselben rueckt die Menschheit weiter."
                ,"모든 시간은 그 시간의 과제를 갖고 있다. 그리고 그 과제의 해결을 통해 인류는 앞으로 전진한다."));
        proverbs.add(new Proverb("Jeder neue Tag ist ein Versprechen. dass alles moeglich ist."
                ,"모든 새로운 날은 모든 것이 가능하다는 한 개의 약속이다."));
        proverbs.add(new Proverb("Ich bin mit dir, ich behuete dich, wohin du auch gehst."
                ,"나는 너와 함께 있으며 네가 어디로 가든지 너를 보호하겠다."));
        proverbs.add(new Proverb("Siege ueber dich, und die Welt liegt dir zu Fuessen."
                ,"너 자신을 이겨라, 그러면 세계는 너의 발치에 놓여 있을 것이다."));
        proverbs.add(new Proverb("Die Welt wird nicht gut, aber sie koennte besser werden."
                ,"세계는 완전히 좋아지지 않는다. 그러나 개선될 여지는 있을 것이다."));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(unbinder!=null)
            unbinder.unbind();
    }
}