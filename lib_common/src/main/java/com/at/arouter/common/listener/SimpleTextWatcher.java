package com.at.arouter.common.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * desc:  dataHandler的监听
 * author:  yangtao
 * <p>
 * creat: 2018/8/19 19:05
 */
public abstract class SimpleTextWatcher
        implements TextWatcher {

    /**
     * 改变之前
     * 在当前文本s中，从start位置开始之后的count个字符（即将）要被after个字符替换掉
     *
     * @param s 文本改变之前的内容
     * @param start 文本开始改变时的起点位置，从0开始计算
     * @param count 要被改变的文本字数，即将要被替代的选中文本字数
     * @param after 改变后添加的文本字数，即替代选中文本后的文本字数
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * 文本被改变时
     * 在当前文本s中，从start位置开始之后的before个字符（已经）被count个字符替换掉了
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * 文本改变后
     */
    @Override
    public void afterTextChanged(Editable s) {

    }
}