package com.at.arouter.third.bridge.bean;

import com.at.arouter.common.model.BaseModel;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/29 0029.
 */

public class ResultData<T>  extends BaseModel {
    public T obj;
    public int status;
    public String msg;
    public int dataType;

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
