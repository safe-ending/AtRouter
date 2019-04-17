package com.at.arouter.third.bridge.m.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Joker on 2017/5/28.
 */

public class FrgPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private int mType;

    public FrgPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        switch (mType) {
            case 3:
                //如果注释这行，那么不管怎么切换，page都不会被销毁
                //super.destroyItem(container, position, object);
                break;
            default:
                super.destroyItem(container, position, object);
                break;
        }
    }

    /**
     * 设置该页内容所占屏幕的宽度
     */
    /*@Override
    public float getPageWidth(int position) {
        //return 1.f; 默认返回1,代表该position占据了ViewPager的一整页,范围(0,1]
        return 0.8f;
    }*/

}
