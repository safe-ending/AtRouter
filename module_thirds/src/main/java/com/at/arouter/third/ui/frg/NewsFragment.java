package com.at.arouter.third.ui.frg;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.at.arouter.common.base.BaseFragment;
import com.at.arouter.common.util.Tools;
import com.at.arouter.common.widget.indicator.ViewPagerHelper;
import com.at.arouter.common.widget.indicator.buildins.UIUtil;
import com.at.arouter.common.widget.indicator.buildins.commonnavigator.CommonNavigator;
import com.at.arouter.common.widget.indicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.at.arouter.common.widget.indicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.at.arouter.common.widget.indicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.at.arouter.common.widget.indicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import com.at.arouter.common.widget.indicator.point.ScaleTransitionPagerTitleView;
import com.at.arouter.third.R;
import com.at.arouter.third.bridge.m.adapter.FrgPageAdapter;
import com.at.arouter.third.databinding.FrgNewsBinding;

import java.util.ArrayList;

import static com.at.arouter.third.utils.Constants.HOT;
import static com.at.arouter.third.utils.Constants.NEWSFLASH;
import static com.at.arouter.third.utils.Constants.TYPE;
import static com.at.arouter.third.utils.Constants.VIEWPOINT;

/**
 * 作者 : Joker
 * 创建日期 : 2018-06-18
 * 修改日期 :
 * 版权所有 :
 */

public class NewsFragment extends BaseFragment {

    private FrgNewsBinding mBind;
    private ArrayList<Fragment> mFragments;
    private int mCurrentIndex;


    public static NewsFragment newInstance() {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int bindLayout() {
        return R.layout.frg_news;
    }

    @Override
    protected boolean convertView(ViewDataBinding bind) {
        boolean error = false;
        if (bind instanceof FrgNewsBinding) {
            mBind = (FrgNewsBinding) bind;
        } else {
            error = true;
        }
        return error;
    }


    @Override
    protected void loadData() {

        if (!mFragments.isEmpty()) {
            if (mFragments.get(mCurrentIndex) instanceof ListFragment)
                ((ListFragment) mFragments.get(mCurrentIndex)).setInvisible();

//            if (getActivity() instanceof MainActivity) {
//                int index = ((MainActivity) getActivity()).getIndexFragment();
//                if (index != -1) {
//                    mBind.vpNews.setCurrentItem(index);
//                    ((MainActivity) getActivity()).clearIndexFragment();
//                }
//            }
        }
    }

    @Override
    protected void initData() {
        setIndicator();
    }

    @Override
    public void reload() {

    }

    @Override
    protected void onInvisible() {
//        super.onInvisible();
//        for (Fragment frg : mFragments) {
//            if (frg instanceof ListFragment) {
//                ((ListFragment) frg).onInvisible();
//            }
//        }
    }


    public void onViewClicked() {
//        MainActivity activity = (MainActivity) getActivity();
//        activity.menuAnimation(true);
    }

    private void setIndicator() {
        ArrayList<String> title = new ArrayList<>();
        title.add(getString(R.string.a658));
        title.add(getString(R.string.a959));
        title.add(getString(R.string.a960));

        mBind.itor.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_FFFFFF));
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return title.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(title.get(index));
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(context, R.color.fadadbd));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(context, R.color.f0b0817));
                simplePagerTitleView.setOnClickListener((v) -> mBind.vpNews.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        mBind.itor.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer();
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(getContext(), 12);
            }
        });
        mBind.itor.setNavigator(commonNavigator);

        mFragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ListFragment fragment = ListFragment.newInstance();
            fragment.setArguments(Tools.setBundleData(TYPE, i == 0 ? HOT : i == 1 ? VIEWPOINT : NEWSFLASH));
            mFragments.add(fragment);

        }
        FrgPageAdapter adapter = new FrgPageAdapter(getChildFragmentManager(), mFragments);
        mBind.vpNews.setAdapter(adapter);

        ViewPagerHelper.bind(mBind.itor, mBind.vpNews);

        mBind.vpNews.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

}
