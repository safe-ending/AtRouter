package com.at.arouter.arouterproject;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.at.arouter.common.data.ARouterPath;

import cc.duduhuo.applicationtoast.AppToast;

/**
 * desc:  临时工具类
 * author:  yangtao
 * <p>
 * creat:  2019/1/21 9:52
 */

public class Utils {

    /**
     * 跳转登录  框架使用的
     *
     * @param context
     */
    public static void toLogin(Context context) {
        try {//跳转
            AppToast.showToast("token失效，请重试登录");
            ARouter.getInstance()
                    .build(ARouterPath.LoginAty)
                    .withString("username", "13266753946")
                    .navigation(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
