package com.at.arouter.coremodel.viewmodel.girls;

import android.app.Application;
import android.support.annotation.NonNull;

import com.at.arouter.coremodel.viewmodel.entities.girls.GirlsData;
import com.at.arouter.coremodel.viewmodel.base.BaseViewFullModel;


/**
 * Created by dxx on 2017/11/20.
 * 动态的url
 */

public class DynamicGirlsViewModel extends BaseViewFullModel<GirlsData> {

    public DynamicGirlsViewModel(@NonNull Application application) {
        super(application);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
