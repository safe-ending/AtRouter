package com.at.arouter.coremodel.viewmodel.girls;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.util.Log;

import com.at.arouter.common.data.APIHostManager;
import com.at.arouter.coremodel.APIManager;
import com.at.arouter.coremodel.viewmodel.entities.girls.GirlsData;
import com.at.arouter.coremodel.service.GirlsService;
import com.at.arouter.coremodel.util.NetUtils;
import com.at.arouter.coremodel.util.SwitchSchedulers;
import com.at.arouter.coremodel.util.TAGUtils;
import com.at.arouter.coremodel.viewmodel.base.BaseViewModel;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 保存生命周期和UI所使用的数据
 * Created by dxx on 2017/11/10.
 */

public class GirlsViewModel extends BaseViewModel {


    public GirlsViewModel(@NonNull Application application) {
        super(application);
        Log.i(TAGUtils.LOG_TAG, "GirlsViewModel------>");
        //这里的trigger为网络检测，也可以换成缓存数据是否存在检测
        mLiveObservableData = Transformations.switchMap(NetUtils.netConnected(application), new Function<Boolean, LiveData<GirlsData>>() {
            @Override
            public LiveData<GirlsData> apply(Boolean isNetConnected) {
                Log.i(TAGUtils.LOG_TAG, "当前apply模块->" + application.getPackageName() + "");
                if (!isNetConnected) {
                    return ABSENT; //网络未连接返回空
                }
                MutableLiveData<GirlsData> applyData = new MutableLiveData<>();

                Observable<GirlsData> observableForGetFuliDataFromNetWork = APIManager.buildAPI(APIHostManager.Gank_Url, GirlsService.class).getFuliData("20", "1");
                observableForGetFuliDataFromNetWork
                        .compose(SwitchSchedulers.applySchedulers())
                        .subscribe(new Observer<GirlsData>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                mDisposable.add(d);
                            }

                            @Override
                            public void onNext(GirlsData value) {
                                Log.i(TAGUtils.LOG_TAG, "setValue------>");
                                applyData.setValue(value);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAGUtils.LOG_TAG, "onError------>");
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {
                                Log.i(TAGUtils.LOG_TAG, "onComplete------>");
                            }
                        });
                return applyData;
            }
        });
    }


}
