
package com.at.arouter.third.bridge.m.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.at.arouter.common.ui.WebActivity;
import com.at.arouter.coremodel.viewmodel.entities.third.NoticeModel;
import com.at.arouter.third.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

/**
 * 公告列表适配器
 */
public class NoticeListAdapter extends BaseQuickAdapter<NoticeModel, BaseViewHolder> {


    public NoticeListAdapter(ArrayList<NoticeModel> data) {
        super(R.layout.recycleview_item_notice, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final NoticeModel model) {
        //*****让recycleview的子项自适应高度
//        ViewGroup.LayoutParams layoutParams = baseViewHolder.itemView.getLayoutParams();
//        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;


        //名称
        TextView name = baseViewHolder.getView(R.id.tv_name);
        name.setText("" + model.title);
        //
        TextView time = baseViewHolder.getView(R.id.tv_tm);
        time.setText("" + model.createTime);
        //
        TextView desc = baseViewHolder.getView(R.id.tv_desc);
        desc.setText(TextUtils.isEmpty(model.brief) ? "" : model.brief + "");

        baseViewHolder.getView(R.id.rl_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, WebActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("htmlData", model.content);
                intent.putExtra("title", mContext.getString(R.string.mine_notice));
                mContext.startActivity(intent);
            }
        });
    }


}
