package com.at.arouter.common.model;

import java.io.Serializable;


/**
 * desc:  BaseModel
 * author:  yangtao
 * <p>
 * creat: 2018/8/19 16:05
 */public class BaseModel
        implements Serializable
{

    public long Id;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseModel baseModel = (BaseModel) o;

        return Id == baseModel.Id;
    }

    @Override
    public int hashCode() {
        return (int) (Id ^ (Id >>> 32));
    }
}
