package com.at.arouter.third.bridge.m.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.at.arouter.third.R;
import com.at.arouter.third.bridge.bean.NewsFlashBean;
import com.at.arouter.third.databinding.ItemNewsFlashBinding;
import com.at.arouter.third.utils.click.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class NewsFlashAdapter extends RecyclerView.Adapter<NewsFlashAdapter.NewsFlashHolder> {

    private Context mContext;
    private ArrayList<NewsFlashBean.ContentBean> mData = new ArrayList<>();
    private int mIndex = -1;

    public NewsFlashAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private ItemClickListener mListener;

    public void setOnItemClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    public void addNewData(List<NewsFlashBean.ContentBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<NewsFlashBean.ContentBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setIndex(int mIndex) {
        this.mIndex = mIndex;
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public NewsFlashHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsFlashHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_news_flash, parent, false), mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(NewsFlashHolder holder, int position) {
        holder.getBind().vTopLine.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        holder.getBind().tvTime.setText(mData.get(position).getCreateAt() + " " );
        /*if (position == 0) {
            long time = new DateUtil().getLongDate() - mData.get(position).getTimestamp();
            if (time <= 0) {
                holder.getBind().tvTime.setText(mData.get(position).getCreateAt() + " " + mContext.getString(R.string.a980));
            } else {
                String result;
                if (time / 86400000 >= 1) {
                    result = new DateUtil().longToString(time, "dd-HH-mm");
                    String[] spilt = result.split("-");
                    holder.getBind().tvTime.setText(Integer.parseInt(spilt[0]) + "天" + Integer.parseInt(spilt[1]) + "小时" + Integer.parseInt(spilt[2]) + "分钟前" + " " + mContext.getString(R.string.a980));
                } else if (time / 3600000 >= 1) {
                    result = new DateUtil().longToString(time, "HH-mm");
                    String[] spilt = result.split("-");
                    holder.getBind().tvTime.setText(Integer.parseInt(spilt[0]) + "小时" + Integer.parseInt(spilt[1]) + "分钟前" + " " + mContext.getString(R.string.a980));
                } else if (time / 60000 >= 1) {
                    result = new DateUtil().longToString(time, "mm");
                    holder.getBind().tvTime.setText(Integer.parseInt(result) + "分钟前" + " " + mContext.getString(R.string.a980));
                }
            }
        }*/
        holder.getBind().tvTitle.setText(mData.get(position).getSort());
        holder.getBind().etvContent.setText(mData.get(position).getContent(), mData.get(position).isCollapsed());
        holder.getBind().etvContent.setListener((isExpanded) -> mData.get(position).setCollapsed(isExpanded));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class NewsFlashHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemNewsFlashBinding mBind;
        private ItemClickListener mListener;

        public NewsFlashHolder(ItemNewsFlashBinding bind, ItemClickListener listener) {
            super(bind.getRoot());
            mBind = bind;
            mListener = listener;
            mBind.ivShare.setOnClickListener(this);
            mBind.ivComment.setOnClickListener(this);
            mBind.ivFabulous.setOnClickListener(this);
        }

        public ItemNewsFlashBinding getBind() {
            return mBind;
        }

        public void setBind(ItemNewsFlashBinding bind) {
            mBind = bind;
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) mListener.onItemClick(view, getPosition());
        }

    }

}
