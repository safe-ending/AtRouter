package com.at.arouter.third.bridge.m.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.at.arouter.third.R;
import com.at.arouter.third.bridge.bean.CommentBean;
import com.at.arouter.third.bridge.bean.CommentDetailBean;
import com.at.arouter.third.bridge.m.holder.BaseHolder;
import com.at.arouter.third.databinding.ItemReplyCommentBinding;
import com.at.arouter.third.utils.click.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 : Joker
 * 创建日期 : 2018/7/9
 * 修改日期 :
 * 版权所有 : 深圳艾特科技
 */

public class ReplyCommentAdapter extends RecyclerView.Adapter<BaseHolder> {

    private Context mContext;
    private ArrayList<Object> mData = new ArrayList<>();
    private int mId;
    private String mNickName;
    private ItemClickListener mListener;

    public ReplyCommentAdapter(Context mContext, List<CommentBean.ContentBean.ChildBean> data, int id, ItemClickListener mListener) {
        this.mContext = mContext;
        this.mData.addAll(data);
        mId = id;
        this.mListener = mListener;
    }

    public ReplyCommentAdapter(Context mContext, List<CommentDetailBean.ChildBeanXX.ChildBeanX> data, String nickName, ItemClickListener mListener) {
        this.mContext = mContext;
        this.mData.addAll(data);
        mNickName = nickName;
        this.mListener = mListener;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReplyCommentHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_reply_comment, parent, false), parent, viewType, mListener);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        holder.onBindViewHolder(mData, position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class ReplyCommentHolder extends BaseHolder<ArrayList<Object>> {

        private ItemReplyCommentBinding mBind;
        private ForegroundColorSpan mBlue, mGray;
        private String mComment;
        private SpannableStringBuilder mSpannable;

        public ReplyCommentHolder(ViewDataBinding bind, ViewGroup parent, int viewType,
                                  ItemClickListener listener) {
            super(bind, parent, viewType);
            mContext = ReplyCommentAdapter.this.mContext;
            mListener = listener;

            mBlue = new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_f6087CF));
            mGray = new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_f8794A2));

            mBind = (ItemReplyCommentBinding) bind;
            mBind.tvReplyComment.setOnClickListener(this);

        }

        @Override
        public void onBindViewHolder(ArrayList<Object> data, int position) {
            super.onBindViewHolder(data, position);
            if (data.get(position) instanceof CommentBean.ContentBean.ChildBean) {
                if (((CommentBean.ContentBean.ChildBean) data.get(position)).getParent().getId() == mId) {
                    mComment = ((CommentBean.ContentBean.ChildBean) data.get(position)).getNickName() + "：" + ((CommentBean.ContentBean.ChildBean) data.get(position)).getContent();
                    mSpannable = new SpannableStringBuilder(mComment);
                    mSpannable.setSpan(mBlue, 0, ((CommentBean.ContentBean.ChildBean) data.get(position)).getNickName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mSpannable.setSpan(mGray, ((CommentBean.ContentBean.ChildBean) data.get(position)).getNickName().length() + 1, mComment.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    mComment = ((CommentBean.ContentBean.ChildBean) data.get(position)).getNickName() + " 回复 @" + ((CommentBean.ContentBean.ChildBean) data.get(position)).getParent().getNickName() + "：" + ((CommentBean.ContentBean.ChildBean) data.get(position)).getContent();
                    mSpannable = new SpannableStringBuilder(mComment);
                    mSpannable.setSpan(mBlue, 0, ((CommentBean.ContentBean.ChildBean) data.get(position)).getNickName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mSpannable.setSpan(mGray, ((CommentBean.ContentBean.ChildBean) data.get(position)).getNickName().length() + 1, mComment.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                mBind.tvReplyComment.setText(mSpannable);
            } else if (data.get(position) instanceof CommentDetailBean.ChildBeanXX.ChildBeanX) {
                mComment = ((CommentDetailBean.ChildBeanXX.ChildBeanX) data.get(position)).getNickName() + " 回复 @" + mNickName + "：" + ((CommentDetailBean.ChildBeanXX.ChildBeanX) data.get(position)).getContent();
                mSpannable = new SpannableStringBuilder(mComment);
                mSpannable.setSpan(mBlue, 0, ((CommentDetailBean.ChildBeanXX.ChildBeanX) data.get(position)).getNickName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mSpannable.setSpan(mGray, ((CommentDetailBean.ChildBeanXX.ChildBeanX) data.get(position)).getNickName().length() + 1, mComment.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mBind.tvReplyComment.setText(mSpannable);
            }
        }
    }

}
