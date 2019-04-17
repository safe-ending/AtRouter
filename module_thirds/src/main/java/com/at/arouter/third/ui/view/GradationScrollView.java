package com.at.arouter.third.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 作者 : Joker
 * 创建日期 : 2018/7/9
 * 修改日期 :
 * 版权所有 : 深圳艾特科技
 * <p>
 * 滚动监听Scrollview
 */

public class GradationScrollView extends ScrollView {

    public GradationScrollView(Context context) {
        super(context);
    }

    public GradationScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public GradationScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public interface ScrollViewListener {
        void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy);
    }

    private ScrollViewListener mScrollViewListener = null;

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.mScrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (mScrollViewListener != null) {
            mScrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

}