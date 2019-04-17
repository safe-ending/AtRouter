package com.at.arouter.coremodel.service;


import com.at.arouter.coremodel.viewmodel.entities.girls.GirlsData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yangtao on 2019/1/12.
 * 妹子模块接口
 */

public interface GirlsService {

    @GET("api/data/福利/{size}/{index}")
    Observable<GirlsData> getFuliData(@Path("size") String size, @Path("index") String index);

}
