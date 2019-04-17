package com.at.arouter.coremodel.http.model;


import java.io.Serializable;

/**
 * desc:  结果解析
 * author:  yangtao
 * <p>
 * creat: 2018/8/24 16:05
 */
public class RequestResult<T>
        implements Serializable, IResult {

    public static final int CODE_REQUEST_SUCCESS = 1;//请求正确

    /**
     * 1：成功，0：失败
     */
    public int code;
    public boolean failed;
    public String msg;
    public T result;
    public String msgKey;

    public boolean success;

    @Override
    public int code() {
        return code;
    }

    @Override
    public boolean failed() {
        return failed;
    }

    @Override
    public String msg() {
        return msg;
    }

    @Override
    public Object result() {
        return result;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public String msgKey() {
        return msgKey;
    }


}
