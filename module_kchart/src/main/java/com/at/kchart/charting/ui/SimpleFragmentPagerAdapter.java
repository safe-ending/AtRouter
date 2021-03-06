package com.at.kchart.charting.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * 装载Fragment的通用适配器
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mFragments;
    private List<String> mTitles;

    public SimpleFragmentPagerAdapter(FragmentManager fm, BaseFragment[] fragments) {
        super(fm);
        mFragments = Arrays.asList(fragments);
    }

    public SimpleFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    public SimpleFragmentPagerAdapter(FragmentManager fm, BaseFragment[] fragments, String[] titles) {
        this(fm, fragments);
        mTitles = Arrays.asList(titles);
    }

    public SimpleFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> titles) {
        this(fm, fragments);
        mTitles = titles;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public BaseFragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null && mTitles.size() > 0) {
            return mTitles.get(position).toLowerCase();
        }
        return super.getPageTitle(position);
    }

}