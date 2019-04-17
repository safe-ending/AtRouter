package com.at.arouter.third.bridge.m.holder;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.at.arouter.third.utils.click.ItemClickListener;

import java.util.ArrayList;

/**
 * 作者 : Joker
 * 创建日期 : 2017/7/6
 * 修改日期 :
 * 版权所有 :
 */

public class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected Context mContext;
    protected ItemClickListener mListener;
    protected ArrayList<Object> mData;

    public BaseHolder(ViewDataBinding bind, ViewGroup parent, int viewType) {
        //super(((LayoutInflater) parent.getContext().getSystemService(parent.getContext().LAYOUT_INFLATER_SERVICE)).inflate(viewId, parent, false));
        super(bind.getRoot());
    }

    public void onBindViewHolder(T data, int position) {
        mData = (ArrayList<Object>) data;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(v, getPosition(), mData);
        }
    }
}
