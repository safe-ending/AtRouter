package com.at.arouter.third.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * 作者 : Joker
 * 创建日期 : 2018/7/6
 * 修改日期 :
 * 版权所有 : 深圳艾特科技
 */

@SuppressLint("AppCompatCustomView")
public class EditTextMonitor extends EditText {

    public EditTextMonitor(Context context) {
        super(context);
    }

    public EditTextMonitor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextMonitor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private BackListener mListener;

    public void setBackListener(BackListener listener) {
        this.mListener = listener;
    }

    public interface BackListener {
        void onBack();
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mListener != null) {
            mListener.onBack();
        }
        return false;
    }

}
