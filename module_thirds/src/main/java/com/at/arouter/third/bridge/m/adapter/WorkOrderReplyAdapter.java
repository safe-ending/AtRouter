package com.at.arouter.third.bridge.m.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.at.arouter.coremodel.viewmodel.entities.third.WorkOrderDetailBean;
import com.at.arouter.third.R;
import com.at.arouter.third.databinding.ItemWorkOrderReplyBinding;
import com.at.arouter.third.utils.click.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class WorkOrderReplyAdapter extends RecyclerView.Adapter<WorkOrderReplyAdapter.WorkOrderReplyHolder> {

    private Context mContext;
    private ArrayList<WorkOrderDetailBean.MessagesBean> mData = new ArrayList<>();

    public WorkOrderReplyAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private ItemClickListener mListener;

    public void setOnItemClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    public void addRefreshData(List<WorkOrderDetailBean.MessagesBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<WorkOrderDetailBean.MessagesBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    public int getAdminId() {
        for (WorkOrderDetailBean.MessagesBean bean : mData) {
            if (bean.isAdmin()) return bean.getId();
        }
        return -1;
    }

    @Override
    public WorkOrderReplyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WorkOrderReplyHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_work_order_reply, parent, false), mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(WorkOrderReplyHolder holder, int position) {
        holder.getBind().tvDate.setText(mData.get(position).isAdmin()
                ? mContext.getString(R.string.a931, mData.get(position).getCreateTime().substring(0, 10))
                : mContext.getString(R.string.a932, mData.get(position).getCreateTime().substring(0, 10)));
        holder.getBind().tvContent.setText(mData.get(position).getDetail());
        holder.getBind().tvContent.setTextColor(ContextCompat.getColor(mContext, mData.get(position).isAdmin() ? R.color.colorPrimary : R.color.color_f274057));
        holder.getBind().tvTime.setText(mData.get(position).getCreateTime().substring(mData.get(position).getCreateTime().length() - 8, mData.get(position).getCreateTime().length()));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class WorkOrderReplyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemWorkOrderReplyBinding mBind;
        private ItemClickListener mListener;

        public WorkOrderReplyHolder(ItemWorkOrderReplyBinding bind, ItemClickListener listener) {
            super(bind.getRoot());
            mBind = bind;
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        public ItemWorkOrderReplyBinding getBind() {
            return mBind;
        }

        public void setBind(ItemWorkOrderReplyBinding bind) {
            mBind = bind;
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) mListener.onItemClick(view, getPosition());
        }

    }

}
