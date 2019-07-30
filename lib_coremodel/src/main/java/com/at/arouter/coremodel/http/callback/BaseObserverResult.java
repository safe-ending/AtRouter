package com.at.arouter.coremodel.http.callback;

import android.util.Log;

import com.at.arouter.coremodel.http.util.TAGUtils;

import cc.duduhuo.applicationtoast.AppToast;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * desc:  封装的Observer  减少代码量
 * author:  yangtao
 * <p> T 为请求结果中的result泛型对象
 * creat:  2019/1/17 15:03
 */

public abstract class BaseObserverResult<T> implements Observer<T> {


    @Override
    public void onSubscribe(Disposable d) {
        //这里可以显示进度框

    }

    @Override
    public abstract void onNext(T t);


    public abstract void onErr(Throwable t);

    @Override
    public void onError(Throwable e) {
        //还可以提示错误消息
        AppToast.showToast(e.getMessage());
        //这里提供外部方法用来隐藏进度框或在onComplete()中进行
        onErr(e);
    }

    @Override
    public void onComplete() {
        Log.i(TAGUtils.LOG_TAG,"请求完成-》BaseObserverResult onComplete");
        //这里用来隐藏进度框
    }
}
