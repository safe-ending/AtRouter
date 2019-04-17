package com.at.arouter.coremodel.service;

import com.at.arouter.coremodel.http.model.RequestResult;
import com.at.arouter.coremodel.viewmodel.entities.other.UpgradeInfo;
import com.at.arouter.coremodel.viewmodel.entities.third.WorkOrderListBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by yangtao on 2019/1/15.
 * 动态请求 全链接
 */

public interface OtherService {

    //升级
    @GET("sys/appset/getAppSetById")
    Observable<RequestResult<UpgradeInfo>> updateVersion(@Query("origin") String origin,//类型 BJB
                                                        @Query("clientCode") int clientCode);//类型：可选{2->apple,1->android});


}
