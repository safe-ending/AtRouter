package com.at.arouter.arouterproject;

import android.content.pm.ApplicationInfo;

import com.alibaba.android.arouter.launcher.ARouter;
import com.at.arouter.common.base.BaseApplication;
import com.at.arouter.common.data.APIHostManager;
import com.at.arouter.common.data.AppPref;
import com.at.arouter.common.util.Utils;
import com.at.arouter.common.util.crash.CrashHandlerException;
import com.at.arouter.coremodel.callback.EmptyCallback;
import com.at.arouter.coremodel.callback.ErrorCallback;
import com.at.arouter.coremodel.callback.HttpCallback;
import com.at.arouter.coremodel.callback.LoadingCallback;
import com.at.arouter.coremodel.callback.TokenCallback;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.kingja.loadsir.core.LoadSir;

import cc.duduhuo.applicationtoast.AppToast;


/**
 * Created by yangtao on 2018/1/13.
 **
 * 要想使用BaseApplication，必须在组件中实现自己的Application，并且继承BaseApplication；
 * 组件中实现的Application必须在debug包中的AndroidManifest.xml中注册，否则无法使用；
 * 组件的Application需置于java/debug文件夹中，不得放于主代码；
 * 组件中获取Context的方法必须为:Utils.getContext()，不允许其他写法；
 * @name BaseApplication
 *
 * 组件moudle/application中的所有引用和初始化都需要加入进来
 */

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        if (Utils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);

        //third module
        // 初始化AppToast库
        AppToast.init(this);

        //配置测试人员的测试环境
        try {
            if ((getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
                String host = new AppPref(this, AppPref.APP_PREF).getHost();
                APIHostManager.Common_Url = host.split(",")[0];
                APIHostManager.Trade_Url = host.split(",")[1];
                APIHostManager.Game_Url = host.split(",")[2];

                CrashHandlerException exception = CrashHandlerException.getInstance();
                exception.initCrashHandlerException(getApplicationContext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new com.at.arouter.common.callback.LoadingCallback())
                .addCallback(new HttpCallback())
                .addCallback(new TokenCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();
    }
}
