package com.at.arouter.coremodel.viewmodel.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;

import com.at.arouter.coremodel.APIManager;
import com.at.arouter.coremodel.util.JsonUtil;
import com.at.arouter.coremodel.util.SwitchSchedulers;
import com.at.arouter.coremodel.util.TAGUtils;

import java.lang.reflect.ParameterizedType;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Created by yangtao on 2019/1/11.
 *
 * 完整链接访问时的请求结果绑定监听
 */

public class BaseViewFullModel<T> extends AndroidViewModel {

    //生命周期观察的数据
    private MutableLiveData<T> liveObservableData = new MutableLiveData<>();
    //UI使用可观察的数据 ObservableField是一个包装类
    public ObservableField<T> uiObservableData = new ObservableField<>();

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    private static final MutableLiveData ABSENT = new MutableLiveData();

    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }


    public BaseViewFullModel(@NonNull Application application) {
        super(application);
        Log.i(TAGUtils.LOG_TAG,"创建BaseViewFullModel生命周期连接");
    }

    public static <T>Observable getDynamicData(String pullUrl, final Class<T> clazz) {

        return
                APIManager
                        .getDynamicDataService()
                        .getDynamicData(pullUrl)
                        .compose(SwitchSchedulers.applySchedulers())
                        .map(new Function<ResponseBody, T>() {
                            @Override
                            public T apply(ResponseBody responseBody) throws Exception {
                                return JsonUtil.Str2JsonBean(responseBody.string(), clazz);
                            }
                        });
    }
    /**
     * @param fullUrl
     */
    public void loadData(String fullUrl) {
        getDynamicData(fullUrl, getTClass())
                .compose(SwitchSchedulers.applySchedulers())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(T value) {
                        if (null != value) {
                            liveObservableData.setValue(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * LiveData支持了lifecycle生命周期检测
     *
     * @return
     */
    public LiveData<T> getLiveObservableData() {
        return liveObservableData;
    }

    /**
     * 当主动改变数据时重新设置被观察的数据
     *
     * @param product
     */
    public void setUiObservableData(T product) {
        this.uiObservableData.set(product);
    }

    public Class<T> getTClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.clear();
        Log.i(TAGUtils.LOG_TAG,"清除BaseViewFullModel生命周期连接");
    }
}
