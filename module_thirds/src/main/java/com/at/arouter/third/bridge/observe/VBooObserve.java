package com.at.arouter.third.bridge.observe;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.at.arouter.third.BR;


/**
 * 作者：Joker
 * 创建日期：2017-12-07
 * 修改时间：
 * 版权所有：
 */

public class VBooObserve extends BaseObservable {

    public ObservableField<Boolean> booObserve = new ObservableField<>();

    public VBooObserve(Boolean booObserve) {
        this.booObserve.set(booObserve);
    }

    @Bindable
    public Boolean getBooObserve() {
        return this.booObserve.get();
    }

    public void setBooObserve(Boolean booObserve) {
        this.booObserve.set(booObserve);
        notifyPropertyChanged(BR.booObserve);
    }
}
