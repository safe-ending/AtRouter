package com.at.arouter.third.bridge;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.at.arouter.common.base.BaseActivity;
import com.at.arouter.common.util.ToastUtil;
import com.at.arouter.common.util.Tools;
import com.at.arouter.third.R;
import com.at.arouter.third.ui.aty.news.NewsDetailActivity;
import com.at.arouter.third.ui.view.EditTextMonitor;


/**
 * 作者 : Joker
 * 创建日期 : 2018/7/6
 * 修改日期 :
 * 版权所有 : 深圳艾特科技
 */

@SuppressLint("ValidFragment")
public class CommentDialog extends DialogFragment implements View.OnClickListener, TextWatcher {

    private Dialog mDialog;
    private EditTextMonitor etmComment;
    private TextView tvComment;
    private ImageView ivShare;

    public SendListener mSendListener;
    private String mHintContent;
    private String mTextContent;

    public CommentDialog(String hintContent, String textContent, SendListener sendBackListener) { // 提示文字
        mHintContent = hintContent;
        mTextContent = textContent;
        mSendListener = sendBackListener;
    }

    public interface SendListener {
        void sendComment(String inputText);
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        mDialog = new Dialog(getActivity(), R.style.Comment_Dialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        View contentView = View.inflate(getActivity(), R.layout.dia_comment, null);
        mDialog.setContentView(contentView);
        mDialog.setCanceledOnTouchOutside(true); // 外部点击取消

        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = mDialog.getWindow();
        assert window != null;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.0f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        etmComment = contentView.findViewById(R.id.etm_comment);
        tvComment = contentView.findViewById(R.id.tv_comment);
        ivShare = contentView.findViewById(R.id.iv_share);

        if (!TextUtils.isEmpty(mTextContent)) {
            etmComment.setText(mTextContent);
        } else etmComment.setHint(mHintContent);

        tvComment.setTextColor(ContextCompat.getColor(getContext(),
                TextUtils.isEmpty(mTextContent) ? R.color.color_ACB0C3 : R.color.TitleColor));

        etmComment.setFocusable(true);
        etmComment.setFocusableInTouchMode(true);
        etmComment.requestFocus();

        etmComment.addTextChangedListener(this);
        etmComment.setBackListener((EditTextMonitor.BackListener) getActivity());

        tvComment.setOnClickListener(this);
        ivShare.setOnClickListener(this);

        mDialog.setOnDismissListener((dialog) ->
                new Handler().postDelayed(() ->
                                Tools.softkeyboard((BaseActivity) getActivity(), etmComment, false),
                        50)
        );

        return mDialog;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_comment) {
            checkContent();

        } else if (i == R.id.iv_share) {
            if (getActivity() instanceof NewsDetailActivity) {
                ((NewsDetailActivity) getActivity()).getInviteCode();
            }

        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (getActivity().getCurrentFocus() == null || editable.length() == 0) {
            tvComment.setTextColor(ContextCompat.getColor(getContext(), R.color.color_ACB0C3));
        } else tvComment.setTextColor(ContextCompat.getColor(getContext(), R.color.TitleColor));
    }

    private void checkContent() {
        String content = etmComment.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.show( getString(R.string.comment_content));
            return;
        }
        mSendListener.sendComment(content);
        dismiss();
    }

}
