package com.at.arouter.coremodel.service;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by yangtao on 2019/1/15.
 * 动态请求 全链接
 */

public interface DynamicApiService{

    @GET
    Observable<ResponseBody> getDynamicData(@Url String fullUrl);


}
