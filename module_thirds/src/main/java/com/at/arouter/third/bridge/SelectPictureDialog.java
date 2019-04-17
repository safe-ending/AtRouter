package com.at.arouter.third.bridge;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.at.arouter.common.ui.OpenPictureActivity;
import com.at.arouter.third.R;


/**
 * 作者 : Joker
 * 创建日期 : 2018/8/8
 * 修改日期 :
 * 版权所有 : 深圳艾特科技
 */

public class SelectPictureDialog extends Dialog implements View.OnClickListener{

    private Context mContext;

    public SelectPictureDialog(@NonNull Context context) {
        super(context, R.style.map_dialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dia_picture_select);

        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.dialogShow);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = -20;
        lp.width = mContext.getResources().getDisplayMetrics().widthPixels;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // lp.alpha = 9f; // 透明度
        // root.measure(0, 0);
        // lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        dialogWindow.findViewById(R.id.btn_camera).setOnClickListener(this);
        dialogWindow.findViewById(R.id.btn_album).setOnClickListener(this);
        dialogWindow.findViewById(R.id.btn_cancel).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_camera) {
            if (mContext instanceof OpenPictureActivity) {
                ((OpenPictureActivity) mContext).verifyCameraPermissions();
            }

        } else if (i == R.id.btn_album) {
            if (mContext instanceof OpenPictureActivity) {
                ((OpenPictureActivity) mContext).verifyStoragePermissions();
            }

        } else if (i == R.id.btn_cancel) {
        }
        dismiss();
    }
}
