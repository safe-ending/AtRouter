package com.at.arouter.common.callback;

import android.app.Dialog;
import android.view.View;

/**
 * desc:  Dialog输入完回调方法接口
 * author:  yangtao
 * <p>
 * creat: 2018/8/28 16:05
 */

public interface DialogTextViewCallBack {

    void getText(View v, String str, Dialog dialog);
}
