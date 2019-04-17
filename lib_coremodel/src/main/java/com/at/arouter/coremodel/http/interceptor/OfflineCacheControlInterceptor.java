package com.at.arouter.coremodel.http.interceptor;


import android.util.Log;

import com.at.arouter.coremodel.UserDao;
import com.at.arouter.coremodel.util.NetUtils;

import java.io.IOException;

import cc.duduhuo.applicationtoast.AppToast;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yangtao on 19/1/21.
 * <p>
 * 设置请求头
 * 离线读取本地缓存，在线获取最新数据(读取单个请求的请求头，亦可统一设置)
 * <p>
 * 此处要更换成对应app的请求头
 */
public class OfflineCacheControlInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oringin = chain.request();

        //这里就是说判读我们的网络条件，要是有网络的话我么就直接获取网络上面的数据，要是没有网络的话我么就去缓存里面取数据
        if (!NetUtils.isNetConnected(AppToast.getApplication())) {
            oringin = oringin.newBuilder()
                    //这个的话内容有点多啊，大家记住这么写就是只从缓存取，想要了解这个东西我等下在
                    // 给大家写连接吧。大家可以去看下，获取大家去找拦截器资料的时候就可以看到这个方面的东西反正也就是缓存策略。
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .header("lang", "zh_CN")
                    .header("app", "exchange")
                    .header("token", UserDao.getInstance(AppToast.getApplication()).getUser() != null ? UserDao.getInstance(AppToast.getApplication()).getUser().token : "")
                    .build();
            Log.d("CacheInterceptor", "no network");
        } else {
            //网络连接下的请求头
            oringin = oringin.newBuilder()
                    .header("lang", "zh_CN")
                    .header("app", "exchange")
                    .header("token", UserDao.getInstance(AppToast.getApplication()).getUser() != null ? UserDao.getInstance(AppToast.getApplication()).getUser().token : "")
                    .build();
        }

        //下面接着配置缓存处理
        Response originalResponse = chain.proceed(oringin);
        if (NetUtils.isNetConnected(AppToast.getApplication())) {
            //这里大家看点开源码看看.header .removeHeader做了什么操作很简答，就是的加字段和减字段的。
            return originalResponse.newBuilder()
                    //这里设置的为0就是说不进行缓存，我们也可以设置缓存时间
                    .header("Cache-Control", "public, max-age=" + 0)
                    .removeHeader("Pragma")
                    .build();
        } else {
            int maxTime = 4 * 24 * 60 * 60 * 1000;
            return originalResponse.newBuilder()
                    //这里的设置的是我们的没有网络的缓存时间，想设置多少就是多少。
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxTime)
                    .removeHeader("Pragma")
                    .build();
        }


    }


}

