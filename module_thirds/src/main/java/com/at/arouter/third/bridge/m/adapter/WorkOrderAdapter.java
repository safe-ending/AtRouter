package com.at.arouter.third.bridge.m.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.at.arouter.coremodel.viewmodel.entities.third.WorkOrderListBean;
import com.at.arouter.third.R;
import com.at.arouter.third.bridge.observe.VBooObserve;
import com.at.arouter.third.databinding.ItemWorkOrderBinding;
import com.at.arouter.third.utils.click.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.at.arouter.third.utils.Constants.EVALUATE;


public class WorkOrderAdapter extends RecyclerView.Adapter<WorkOrderAdapter.WorkOrderHolder> {

    private Context mContext;
    private ArrayList<WorkOrderListBean.ContentBean> mData = new ArrayList<>();

    public WorkOrderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private ItemClickListener mListener;

    public void setOnItemClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    public void addRefreshData(List<WorkOrderListBean.ContentBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<WorkOrderListBean.ContentBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    public String getOrderCode(int position) {
        return mData.get(position).getCode();
    }

    @Override
    public WorkOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WorkOrderHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_work_order, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(WorkOrderHolder holder, int position) {
        holder.getBind().tvTitle.setText(mData.get(position).getContent());
        holder.getBind().tvTime.setText(mData.get(position).getCreateTime());

//        @color/f8794A2 : @color/fff5d5d
        if (mData.get(position).getStatus().equals("UNTREATED")){
            holder.getBind().tvStatus.setText(mContext.getString(R.string.untreated));
        }else if (mData.get(position).getStatus().equals("PROCESS")){
            holder.getBind().tvStatus.setText(mContext.getString(R.string.haveHand));
//            holder.getBind().tvStatus.setTextColor(ContextCompat.getColor(mContext,R.color.fff5d5d));
        }else if (mData.get(position).getStatus().equals("EVALUATE")){
            holder.getBind().tvStatus.setText(mContext.getString(R.string.completed));
//            holder.getBind().tvStatus.setTextColor(ContextCompat.getColor(mContext,R.color.TitleColor));
        }
        holder.getBind().tvStatus.setTextColor(ContextCompat.getColor(mContext,R.color.color_f8794A2));
        holder.getBind().setType(new VBooObserve(TextUtils.equals(mData.get(position).getStatus(), EVALUATE)));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class WorkOrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemWorkOrderBinding mBind;
        private ItemClickListener mListener;

        public WorkOrderHolder(ItemWorkOrderBinding bind, ItemClickListener listener) {
            super(bind.getRoot());
            mBind = bind;
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        public ItemWorkOrderBinding getBind() {
            return mBind;
        }

        public void setBind(ItemWorkOrderBinding bind) {
            mBind = bind;
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) mListener.onItemClick(view, getPosition());
        }

    }

}
