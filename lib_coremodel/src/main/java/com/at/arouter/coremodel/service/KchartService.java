package com.at.arouter.coremodel.service;

import com.at.arouter.coremodel.http.model.RequestResult;
import com.at.arouter.coremodel.viewmodel.entities.kchart.KLineDataModel;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * desc:  K线接口
 * author:  yangtao
 * <p>
 * creat:  2019/1/25 15:16
 */

public interface KchartService {

    /**
     * 行情 币种各平台实时价格列表
     * ticker
     * type  M1代表1分钟图
     */
    @GET("market/kline/{ticker}/{type}")
    Observable<RequestResult<ArrayList<KLineDataModel>>> getTrade(@Path("ticker") String ticker,
                                                                  @Path("type") String type);
}
