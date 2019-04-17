package com.at.arouter.coremodel.service;


import com.at.arouter.coremodel.viewmodel.entities.third.NoticeModel;
import com.at.arouter.coremodel.viewmodel.entities.third.ReplyWorkOrderBean;
import com.at.arouter.coremodel.viewmodel.entities.third.UploadWorkFileBean;
import com.at.arouter.coremodel.viewmodel.entities.third.WorkOrderDetailBean;
import com.at.arouter.coremodel.viewmodel.entities.third.WorkOrderListBean;
import com.at.arouter.coremodel.http.model.RequestResult;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yangtao on 2019/1/15.
 * 公告工单模块接口
 */

public interface ThirdService {


    //_______________________工单模块开始______________________________________


    //* 工单首页数据
    @GET("ticket/pages")
    Observable<RequestResult<WorkOrderListBean>> getOrderList(@Query("author") String author,
                                                              @Query("size") int size,
                                                              @Query("page") int page);

    //* 工单图片
    @Multipart
    @POST("information/file/upload")
    Observable<RequestResult<UploadWorkFileBean>> postOrderPic(@Part MultipartBody.Part file);

    //* 工单提交
    @FormUrlEncoded
    @POST("ticket/")
    Observable<RequestResult<Object>> postOrder(

            @Field("author") String author,
            @Field("content") String content,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("attachments[0].url") String aurl,
            @Field("attachments[0].type") String atype,
            @Field("attachments[0].local") String local);

    //* 工单回复
    @POST("ticket/{orderCode}/message")
    Observable<RequestResult<WorkOrderListBean>> postOrderReply(@Path("orderCode") String orderCode,
                                                                @Body ReplyWorkOrderBean bean);

    //* 工单详情
    @PUT("ticket/{orderCode}")
    Observable<RequestResult<?>> getOrderDetails(@Path("orderCode") String orderCode,
                                                 @Header("slave") boolean slave);


    //* 工单详情
    @GET("ticket/{orderCode}")
    Observable<RequestResult<WorkOrderDetailBean>> getOrderDetails2(@Path("orderCode") String orderCode);

    //_______________________工单模块结束______________________________________

    /**
     * 公告
     */
    @GET("notice/")
    Observable<RequestResult<ArrayList<NoticeModel>>> getNotice(@Query("lang") String lang,//zh_CN
                                                                @Query("content") boolean content);//true兼容
}
