package com.at.arouter.third.bridge.m.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.at.arouter.common.util.Tools;
import com.at.arouter.third.R;
import com.at.arouter.third.bridge.bean.NewsBean;
import com.at.arouter.third.databinding.ItemNewsBinding;
import com.at.arouter.third.utils.DateUtil;
import com.at.arouter.third.utils.click.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private Context mContext;
    private ArrayList<NewsBean.ContentBean> mData = new ArrayList<>();

    public NewsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private ItemClickListener mListener;

    public void setOnItemClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    private boolean isScrolling = false;

    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }

    public void addData(List<NewsBean.ContentBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public int size() {
        return mData.size();
    }

    public void addNewData(List<NewsBean.ContentBean> data) {
        if (!mData.isEmpty()) mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        if (!mData.isEmpty()) mData.clear();
        notifyDataSetChanged();
    }

    public String[] getValue(int position) {
        String[] value = new String[3];
        value[0] = mData.get(position).getId() + "";
        value[1] = mData.get(position).getTitle();
        value[2] = mData.get(position).getBrief();
        return value;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_news, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        holder.getBind().tvNewsTitle.setText(mData.get(position).getTitle());
        String time = DateUtil.longToDateAs12(mData.get(position).getCreateTime(), "MM.dd yyyy hh:mm aa", 0);
        String end = time.substring(time.length() -2,time.length());
        if (end.equals("AM")){
            holder.getBind().tvTime.setText(time.substring(0,time.length() - 2)+" "+mContext.getString(R.string.am));
        }else{
            holder.getBind().tvTime.setText(time.substring(0,time.length() - 2)+" "+mContext.getString(R.string.pm));
        }

        if (!TextUtils.isEmpty(mData.get(position).getThumb())
                && !TextUtils.isEmpty(mData.get(position).getThumb().trim())) {
            holder.getBind().ivImg.setVisibility(View.VISIBLE);
            if (!isScrolling) {
                Tools.loadNewsPicture(mContext, mData.get(position).getThumb().split(",")[0], holder.getBind().ivImg);
            } else holder.getBind().ivImg.setImageResource(R.color.white);
        } else {
            holder.getBind().ivImg.setImageResource(0);
            holder.getBind().ivImg.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemNewsBinding mBind;
        private ItemClickListener mListener;

        public NewsHolder(ItemNewsBinding bind, ItemClickListener listener) {
            super(bind.getRoot());
            mBind = bind;
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        public ItemNewsBinding getBind() {
            return mBind;
        }

        public void setBind(ItemNewsBinding bind) {
            mBind = bind;
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) mListener.onItemClick(view, getPosition());
        }

    }

}
