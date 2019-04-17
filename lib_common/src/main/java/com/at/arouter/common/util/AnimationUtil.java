package com.at.arouter.common.util;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

import com.at.arouter.common.listener.SimpleAnimationListener;


/**
 *
 * 类说明： 动画
 */
public class AnimationUtil {

    public static final int DURATION = 500;
    public static final int TRANSLATE_DURATION = 200;
    public static final int ALPHA_DURATION = 300;

    public static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    public static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    public static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);


    /**
     * y方向平移动画
     *
     * @param view
     * @param duration
     * @param translateY
     * @param simpleAnimationListener
     */
    public static void translateY(ViewGroup view, int duration, int translateY, SimpleAnimationListener simpleAnimationListener) {
        ObjectAnimator valueAnimator = ObjectAnimator.ofFloat(view, "translationY", translateY);
        valueAnimator.setDuration(duration);
        valueAnimator.start();
        if (simpleAnimationListener != null) {
            view.setLayoutAnimationListener(simpleAnimationListener);
        }
    }

    /**
     * 设置明暗透明度动画
     *
     * @return
     */
    public static Animation createAlphaAnimation(float fromAlpha,
                                                 float toAlpha, boolean fillAfter) {
        AlphaAnimation translateAnimation = new AlphaAnimation(fromAlpha,
                toAlpha);
        translateAnimation.setDuration(DURATION);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }


    /**
     * 设置平移动画 y方向的
     *
     * @return
     */
    public static Animation createTranslationAnimation(float fromYValue,
                                                       float toYValue, boolean fillAfter) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, fromYValue,
                TranslateAnimation.RELATIVE_TO_SELF, toYValue);
        translateAnimation.setDuration(TRANSLATE_DURATION);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    /**
     *  放大缩小、透明度、动画
     *
     * @param view
     * @param initValue
     * @param endValue
     * @param duration
     */
    public static void startScaleAlpaAnimation(View view, int initValue, int endValue, int duration) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", initValue,
                endValue);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", initValue,
                endValue);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", initValue,
                endValue);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(duration).start();
    }

    /**
     * @param v
     * @param changeHeight 展开的高度
     * @param ordialHeight 原始的高度
     */
    public static void expand(final View v, final int changeHeight, final int ordialHeight, int duration) {
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
//                v.getLayoutParams().height = interpolatedTime == 1
//                        ? LinearLayout.LayoutParams.WRAP_CONTENT
//                        : ordialHeight + (int) (changeHeight * interpolatedTime);

                v.getLayoutParams().height=ordialHeight + (int) (changeHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration(duration);
        v.startAnimation(a);
    }


    /**
     * @param v
     * @param changeHeight 折叠的高度
     * @param ordialHeight 原始的高度
     */
    public static void collapse(final View v, final int changeHeight, final int ordialHeight, int duration) {
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = (int) (ordialHeight - (changeHeight * interpolatedTime));
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms   300 300
//        a.setDuration((int) (changeHeight / v.getContext().getResources().getDisplayMetrics().density));
        a.setDuration(duration);
        v.startAnimation(a);
    }
}
