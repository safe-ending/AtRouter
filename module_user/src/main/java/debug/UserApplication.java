package debug;


import com.alibaba.android.arouter.launcher.ARouter;
import com.at.arouter.common.base.BaseApplication;
import com.at.arouter.common.util.Utils;

import cc.duduhuo.applicationtoast.AppToast;

/**
 * Created by yangtao on 2019/1/19.
 * 组件化编译的时候才生效
 */

public class UserApplication extends BaseApplication {

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

    }
}
