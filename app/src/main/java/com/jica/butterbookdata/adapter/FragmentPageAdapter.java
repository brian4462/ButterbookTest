package com.jica.butterbookdata.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();

    public FragmentPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
        notifyDataSetChanged();
    }
    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
