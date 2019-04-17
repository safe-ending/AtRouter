package debug;

import com.alibaba.android.arouter.launcher.ARouter;
import com.at.arouter.common.base.BaseApplication;
import com.at.arouter.common.util.Utils;
import com.facebook.drawee.backends.pipeline.Fresco;


/**
 * Created by dxx on 2017/11/15.
 * 组件化编译的时候才生效
 */

public class GirlsApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        if (Utils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
//        assert
        ARouter.init(this);
    }
}
