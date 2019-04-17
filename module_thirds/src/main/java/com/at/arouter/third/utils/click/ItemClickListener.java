package com.at.arouter.third.utils.click;

import android.view.View;

import java.util.ArrayList;

/**
 * 作者：Joker
 * 创建日期：2017-4-4
 * 修改时间：
 * 版权所有：
 */

public interface ItemClickListener {

    void onItemClick(View view, int position);

    void onItemClick(View view, int position, ArrayList<Object> lists);

}
