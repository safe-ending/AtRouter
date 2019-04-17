package com.at.arouter.common.callback;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

/**
 * desc:  提交动画接口
 * author:  yangtao
 * <p>
 * creat: 2018/8/20 16:05
 */

public interface DialogFragmentCallback {
    void onDialogFragmentDismiss(DialogFragment dialogFragment);
    void onDialogFragmentCancel(DialogFragment dialogFragment);
    void onDialogFragmentClick(DialogFragment dialogFragment, View view);
    void onDialogFragmentResult(DialogFragment dialogFragment, Bundle bundle);
}
