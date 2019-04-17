package com.at.arouter.coremodel.http.model;

/**
 * desc:  请求结果
 * author:  yangtao
 * <p>
 * creat: 2018/8/24 16:05
 */

public interface IResult {
    public int code();
    public boolean failed();
    public String msg();
    public Object result();
    public boolean success();
    public String msgKey();
}
