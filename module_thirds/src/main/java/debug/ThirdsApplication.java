package debug;

import com.alibaba.android.arouter.launcher.ARouter;
import com.at.arouter.common.base.BaseApplication;
import com.at.arouter.common.util.Utils;
import com.at.arouter.coremodel.callback.EmptyCallback;
import com.at.arouter.coremodel.callback.ErrorCallback;
import com.at.arouter.coremodel.callback.HttpCallback;
import com.at.arouter.coremodel.callback.LoadingCallback;
import com.at.arouter.coremodel.callback.TokenCallback;
import com.kingja.loadsir.core.LoadSir;

import cc.duduhuo.applicationtoast.AppToast;


/**
 * Created by dxx on 2017/11/15.
 * 组件化编译的时候才生效
 */

public class ThirdsApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (Utils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        // 初始化AppToast库
        AppToast.init(this);

        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TokenCallback())
                .addCallback(new HttpCallback())
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();
    }
}
