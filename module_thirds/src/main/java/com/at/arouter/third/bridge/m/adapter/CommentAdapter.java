package com.at.arouter.third.bridge.m.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.at.arouter.common.util.Tools;
import com.at.arouter.third.R;
import com.at.arouter.third.bridge.bean.CommentBean;
import com.at.arouter.third.bridge.bean.CommentDetailBean;
import com.at.arouter.third.bridge.m.holder.BaseHolder;
import com.at.arouter.third.databinding.ItemCommentBinding;
import com.at.arouter.third.utils.click.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 : Joker
 * 创建日期 : 2018-07-09
 * 修改日期 :
 * 版权所有 :
 */

public class CommentAdapter extends RecyclerView.Adapter<BaseHolder> {

    private Context mContext;
    private ArrayList<Object> mData = new ArrayList<>();

    public CommentAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private ItemClickListener mListener;

    public void setOnItemClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    public void addRefreshCommentData(List<CommentBean.ContentBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addCommentData(List<CommentBean.ContentBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addCommentDetailData(List<CommentDetailBean.ChildBeanXX> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    // 条目样式
    // private final int VERTICAL_VIEW = 1000;
    // private final int HORIZONTAL_VIEW = 1000;

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_comment, parent, false), parent, viewType, mListener);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        holder.onBindViewHolder(mData, position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class CommentHolder extends BaseHolder<ArrayList<Object>> {

        private ItemCommentBinding mBind;

        public CommentHolder(ViewDataBinding binding, ViewGroup parent, int viewType,
                             ItemClickListener listener) {
            super(binding, parent, viewType);
            mContext = CommentAdapter.this.mContext;
            mListener = listener;
            mBind = (ItemCommentBinding) binding;
            mBind.ivComment.setOnClickListener(this);
            mBind.tvLookAllComments.setOnClickListener(this);
        }

        @Override
        public void onBindViewHolder(ArrayList<Object> data, int position) {
            super.onBindViewHolder(data, position);
            if (data.get(position) instanceof CommentBean.ContentBean) {
                mBind.tvName.setText(((CommentBean.ContentBean) data.get(position)).getNickName());
                mBind.tvTime.setText(((CommentBean.ContentBean) data.get(position)).getCreateTime());
                mBind.tvComment.setText(((CommentBean.ContentBean) data.get(position)).getContent());

                mBind.rvReplyComment.setLayoutManager(Tools.getNotRollLinearLayoutManager(mContext));
                if (!((CommentBean.ContentBean) data.get(position)).getChild().isEmpty() && ((CommentBean.ContentBean) data.get(position)).getChild().size() > 3) {
                    ArrayList<CommentBean.ContentBean.ChildBean> screenData = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        screenData.add(((CommentBean.ContentBean) data.get(position)).getChild().get(i));
                    }
                    mBind.tvLookAllComments.setText("查看" + ((CommentBean.ContentBean) data.get(position)).getChild().size() + "条评论");
                    mBind.rvReplyComment.setAdapter(new ReplyCommentAdapter(mContext, screenData, ((CommentBean.ContentBean) data.get(position)).getId(), mListener));
                } else {
                    mBind.rvReplyComment.setAdapter(new ReplyCommentAdapter(mContext, ((CommentBean.ContentBean) data.get(position)).getChild(), ((CommentBean.ContentBean) data.get(position)).getId(), mListener));
                }
                mBind.rvReplyComment.setVisibility(((CommentBean.ContentBean) data.get(position)).getChild().isEmpty() ? View.GONE : View.VISIBLE);

                mBind.tvLookAllComments.setVisibility(((CommentBean.ContentBean) data.get(position)).getChild().size() > 3 ? View.VISIBLE : View.GONE);
            } else if (data.get(position) instanceof CommentDetailBean.ChildBeanXX) {
                mBind.tvName.setText(((CommentDetailBean.ChildBeanXX) data.get(position)).getNickName());
                mBind.tvTime.setText(((CommentDetailBean.ChildBeanXX) data.get(position)).getCreateTime());
                mBind.tvComment.setText(((CommentDetailBean.ChildBeanXX) data.get(position)).getContent());
                mBind.rvReplyComment.setLayoutManager(Tools.getNotRollLinearLayoutManager(mContext));
                mBind.rvReplyComment.setAdapter(new ReplyCommentAdapter(mContext, ((CommentDetailBean.ChildBeanXX) data.get(position)).getChild(), ((CommentDetailBean.ChildBeanXX) data.get(position)).getNickName(), mListener));
                mBind.rvReplyComment.setVisibility(((CommentDetailBean.ChildBeanXX) data.get(position)).getChild().isEmpty() ? View.GONE : View.VISIBLE);
                mBind.tvLookAllComments.setVisibility(View.GONE);
            }
        }
    }

}
