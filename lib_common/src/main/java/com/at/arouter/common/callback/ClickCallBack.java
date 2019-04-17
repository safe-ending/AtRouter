package com.at.arouter.common.callback;

import android.app.Dialog;

/**
 * desc:  dialog切换资产 ，点击完回调接口
 * author:  yangtao
 * <p>
 * creat: 2018/9/19 16:05
 */

public interface ClickCallBack {
    void onSuccess(String coinName, Dialog dialog);
}
