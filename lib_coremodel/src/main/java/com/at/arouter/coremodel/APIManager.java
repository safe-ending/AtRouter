package com.at.arouter.coremodel;


import android.util.Log;

import com.at.arouter.common.data.APIHostManager;
import com.at.arouter.coremodel.service.DynamicApiService;
import com.at.arouter.coremodel.http.util.HttpsUtils;
import com.at.arouter.coremodel.http.interceptor.OfflineCacheControlInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yangtao on 2019/1/10.
 * <p>
 * 网络请求
 * <p>
 * 注意：在com.at.arouter.coremodel.util.interceptor.OfflineCacheControlInterceptor下配置header
 * 语言切换需要获取本地存储的语言状态暂未考虑  默认请求中文数据
 */

public class APIManager {

    /**
     * 获取指定数据类型 默认为普通接口请求
     *
     * @return
     */
    public static <T> T buildAPI(String baseurl, Class<T> clazz) {
        return initService(baseurl, clazz);
    }

    /**
     * 获取数据  为第三方服务接口请求
     *
     * @return
     */
    public static <T> T buildTradeAPI(Class<T> clazz) {
        return initService(APIHostManager.Trade_Url, clazz);
    }


    /**
     * 动态url获取数据
     *
     * @return baseUrl为必填项 不然会报url null空奔溃  任意域名都可，最终会以动态全链接访问
     */
    public static DynamicApiService getDynamicDataService() {

        DynamicApiService dynamicApiService = initService(APIHostManager.Trade_Url, DynamicApiService.class);

        return dynamicApiService;
    }

    /**
     * 获得想要的 retrofit service
     *
     * @param baseUrl 数据请求url
     * @param clazz   想要的 retrofit service 接口，Retrofit会代理生成对应的实体类
     * @param <T>     api service
     * @return
     */
    private static <T> T initService(String baseUrl, Class<T> clazz) {
        return getRetrofitInstance(baseUrl).create(clazz);
    }

    /**
     * 单例retrofit
     */
    private static Retrofit retrofitInstance;

    private static Retrofit getRetrofitInstance(String baseurl) {
        if (retrofitInstance != null) {
            Log.i("host name", retrofitInstance.baseUrl().url().toString() + "");
        }
        if (retrofitInstance == null || !retrofitInstance.baseUrl().url().toString().equals(baseurl)) {
            synchronized (APIManager.class) {
                if (retrofitInstance == null || !retrofitInstance.baseUrl().url().toString().equals(baseurl)) {
                    retrofitInstance = new Retrofit.Builder()
                            .baseUrl(baseurl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(getOkHttpClientInstance())
                            .build();
                }
            }
        }
        return retrofitInstance;
    }

    /**
     * 单例OkHttpClient
     */
    private static OkHttpClient okHttpClientInstance;
    public final static int CONNECT_TIMEOUT = 10;//超时
    public final static int READ_TIMEOUT = 30;
    public final static int WRITE_TIMEOUT = 10;

    private static Interceptor cacheInterceptor = new OfflineCacheControlInterceptor();

    private static OkHttpClient getOkHttpClientInstance() {
        if (okHttpClientInstance == null) {
            synchronized (APIManager.class) {
                if (okHttpClientInstance == null) {
                    OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
                    builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);//设置读取超时时
                    builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);//设置写的超时时间
                    builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);//设置连接超时时间
                    //设置缓存
                    builder.addInterceptor(cacheInterceptor);
                    builder.addNetworkInterceptor(cacheInterceptor);

                    //debug下展示日志
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(httpLoggingInterceptor);
                    }

                    HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
                    builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
                    okHttpClientInstance = builder.build();
                }
            }
        }
        return okHttpClientInstance;
    }

}
