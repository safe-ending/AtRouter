package com.at.arouter.third.ui.view;//package com.atkj.chainplusclub.ui.view;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.widget.ScrollView;
//
//import com.atkj.chainplusclub.utils.L;
//
///**
// * 作者 : Joker
// * 创建日期 : 2018-07-28
// * 修改日期 :
// * 版权所有 : 艾特科技
// */
//
//public class SmartScrollView extends ScrollView {
//
//    private boolean isScrolledToTop = true; // 初始化的时候设置一下值
//    private boolean isScrolledToBottom = false;
//
//    public SmartScrollView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    private ISmartScrollChangedListener mSmartScrollChangedListener;
//
//    /**
//     * 定义监听接口
//     */
//    public interface ISmartScrollChangedListener {
//        void onScrolledToTop();
//
//        void onScrolledToBottom();
//    }
//
//    public void setScanScrollChangedListener(ISmartScrollChangedListener smartScrollChangedListener) {
//        mSmartScrollChangedListener = smartScrollChangedListener;
//    }
//
//    @Override
//    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
//        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
//        if (scrollY == 0) {
//            isScrolledToTop = clampedY;
//            isScrolledToBottom = false;
//            // L.d("onOverScrolled isScrolledToTop:" + isScrolledToTop);
//        } else {
//            isScrolledToTop = false;
//            isScrolledToBottom = clampedY;
//            // L.d("onOverScrolled isScrolledToBottom:" + isScrolledToBottom);
//        }
//        notifyScrollChangedListeners();
//    }
//
//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//        // 这个log可以研究ScrollView的上下padding对结果的影响
//        // L.d("onScrollChanged getScrollY():" + getScrollY() + " t: " + t + " paddingTop: " + getPaddingTop());
//        if (getScrollY() == 0) {
//            isScrolledToTop = true;
//            isScrolledToBottom = false;
//            // L.d("onScrollChanged isScrolledToTop:" + isScrolledToTop);
//        } else if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
//            isScrolledToBottom = true;
//            // L.d("onScrollChanged isScrolledToBottom:" + isScrolledToBottom);
//            isScrolledToTop = false;
//        } else {
//            isScrolledToTop = false;
//            isScrolledToBottom = false;
//        }
//        notifyScrollChangedListeners();
//    }
//
//    long mLastTime;
//
//    private void notifyScrollChangedListeners() {
//        if (isScrolledToTop) {
//            if (mSmartScrollChangedListener != null) {
//                mSmartScrollChangedListener.onScrolledToTop();
//            }
//        } else if (isScrolledToBottom) {
//            if (mSmartScrollChangedListener != null) {
//                if (mLastTime == 0) {
//                    mLastTime = System.currentTimeMillis();
//                    mSmartScrollChangedListener.onScrolledToBottom();
//                } else {
//                    if (System.currentTimeMillis() - mLastTime > 300) {
//                        mLastTime = System.currentTimeMillis();
//                        mSmartScrollChangedListener.onScrolledToBottom();
//                    } else L.i("BOTTOM - 重复");
//                }
//            }
//        } else if (mLastTime != 0) mLastTime = System.currentTimeMillis();
//    }
//
//    public boolean isScrolledToTop() {
//        return isScrolledToTop;
//    }
//
//    public boolean isScrolledToBottom() {
//        return isScrolledToBottom;
//    }
//
//}
