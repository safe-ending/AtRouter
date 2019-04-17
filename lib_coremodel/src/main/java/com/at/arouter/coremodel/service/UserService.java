package com.at.arouter.coremodel.service;

import com.at.arouter.coremodel.viewmodel.entities.user.UserModel;
import com.at.arouter.coremodel.http.model.RequestResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * desc:  用户模块接口
 * author:  yangtao
 * <p>
 * creat:  2019/1/19 17:16
 */

public interface UserService {

    //    用户中心模块
    //登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<RequestResult<UserModel>> login(@Field("username") String username,
                                               @Field("pwd") String pwd);

    //登出
    @POST("user/logout")
    Observable<RequestResult<String>> loginOut();


    //忘记登录密码
    @FormUrlEncoded
    @POST("user/resetPwd")
    Observable<RequestResult<String>> forgetLoginPwd(@Field("username") String username,
                                                     @Field("pwd") String pwd,
                                                     @Field("code") String code,
                                                     @Field("nickName") String nickName);

    /**
     * @param type  REGISTER("注册消息", SmsTplEnum.CLUB_USER_REGISTER),
     *              RESET("重新设置用户手机号", SmsTplEnum.DEFAULT),
     *              RESET_PASSWORD("重新设置登录密码", SmsTplEnum.DEFAULT),
     *              BACKEND_RESET_USER_PASSWORD("后台重置用户密码", SmsTplEnum.NOTICE),
     *              RESET_PAY_PASSWORD("重新设置交易密码", SmsTplEnum.DEFAULT),
     *              WALLET_ETH_WITHDRWAL("提现验证码", SmsTplEnum.WALLET_ETH_WITHDRWAL),
     *              CLUB_ADMIN_RESETPWD("管理员重置密码", SmsTplEnum.DEFAULT),
     *              CLUB_ADMIN_ACTION("管理员操作验证码", SmsTplEnum.DEFAULT),
     *              BINDACCOUNT("绑定手机号", SmsTplEnum.DEFAULT),
     *              UNBINDACCOUNT("解绑手机号", SmsTplEnum.DEFAULT),
     *              DEFAULT("默认消息", SmsTplEnum.DEFAULT);
     * @param phone
     */
    //发送验证码   REGISTER RESET RESET_PASSWORD
    @FormUrlEncoded
    @POST("sms/sendCode")
    Observable<RequestResult<String>> sendCode(@Field("phone") String phone,
                                               @Field("type") String type);
}
