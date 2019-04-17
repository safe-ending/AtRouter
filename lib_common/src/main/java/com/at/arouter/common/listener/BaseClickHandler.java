package com.at.arouter.common.listener;

import android.support.v7.app.AppCompatActivity;

/**
 * desc:  Base
 * author:  yangtao
 * <p>
 * creat: 2018/8/19 19:05
 */
public class BaseClickHandler {
    protected final AppCompatActivity activity;

    public BaseClickHandler(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void onClickLeft(){
        this.activity.finish();
    }

}