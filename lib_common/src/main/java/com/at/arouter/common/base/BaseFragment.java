package com.at.arouter.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.at.arouter.common.callback.LoadingCallback;
import com.at.arouter.common.data.AppPref;
import com.at.arouter.common.util.LogUtils;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;


public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;// mActivity替代getActivity方法


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 解决应用（内存）重启时getActivity()空指针
        this.mActivity = (Activity) context;
    }

    protected String TAG = this.getClass().getSimpleName();

    protected final String ERROR_TAG = "类型错误：";

    /**
     * 是否第一次加载
     */
    protected boolean mIsFirstLoad = true;

    /**
     * view error
     */
    protected boolean mErrorView;

    /**
     * 标志位，View已经初始化完成。
     * 用isAdded()属性代替
     * isPrepared还是准一些,isAdded有可能出现onCreateView没走完但是isAdded了
     */
    private boolean mIsPrepared;

    /**
     * 是否可见状态
     */
    protected boolean mIsVisible;

    /**
     * 是否已经加载数据
     */
    protected boolean mIsLoadData;

    /**
     * 容器是否为ViewPager
     */
    protected boolean mIsViewPager;

    /**
     * 容器为ViewPager，点击按钮显示不相邻的Fragment会重走onActivityCreated方法
     * 从而执行lazyLoad()的logicBusiness()初始化与loadData()加载数据
     * 执行过程：
     * setUserVisibleHint()->loadData()->onActivityCreated()->lazyLoad()->logicBusiness()->loadData()
     * <p>
     * 是否显示旧数据：logicBusiness()初始化全局数据(ArrayList||Adapter(内含有数据源))其一非空判断
     * <p>
     * loadData()：
     * 刷新数据无控件特效(需在logicBusiness()初始化)：略过 (拦截loadData()执行2次)；
     * 刷新数据有控件特效(需在logicBusiness()初始化)：子类修改 mVPFragmentDrawCompletion 为负int最大值，
     * ----第一次loadData()会因重走logicBusiness()而取消，执行第二次loadData()
     */
    protected int mVPFragmentDrawCompletion;
    protected AppPref walletPref;//钱包的本地存储
    protected AppPref commonPref;//语言等其他本地保存

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mIsFirstLoad = false;

        //View view = initViews(inflater, container, savedInstanceState);

        ViewDataBinding bind = DataBindingUtil.inflate(inflater, bindLayout(), container, false);

        LogUtils.d(TAG, " initViews");

        if (convertView(bind)) {
            mErrorView = true;
            convertError();
            return bind.getRoot();
        }

        return bind.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.d(TAG, "onActivityCreated");
        if (mIsViewPager) mVPFragmentDrawCompletion += 1;
        mIsPrepared = true;
        walletPref = new AppPref(mActivity, "");
        commonPref = new AppPref(mActivity, AppPref.APP_PREF);
        lazyLoad();
    }

    public LoadService loadService;

    public void setLoadSuccessView(View view) {
        loadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                loadService.showCallback(LoadingCallback.class);
                // 重新加载逻辑
                reload();
            }
        });
        loadService.showCallback(LoadingCallback.class);

    }

    /**
     * 与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.d(TAG, "setUser......" + isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    /**
     * 通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     *               visible.
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.d(TAG, "show and hide");
        if (!hidden) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见时调用
     */
    protected void onVisible() {
        LogUtils.d(TAG, "可见时调用 : onVisible");
        lazyLoad();
    }

    /**
     * 原fragment开启新activity，remove后返回当前的fragment执行
     */
    @Override
    public void onStart() {
        super.onStart();
        // case : mIsViewPager = true
        if (!mIsFirstLoad && !mIsFirstLoad && !mIsVisible) return;
        if (!mIsLoadData) {
            LogUtils.d(TAG, "执行onStart -- > 懒加载");
            lazyLoad();
        }
    }

    //弹出错误界面时点击重新加载
    public abstract void reload();

    /**
     * 再次调用可见方法
     * //再次修改此方法的时候,注意主界面的线程调度
     */
    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }

    }


    //再次修改此方法的时候,注意主界面的线程调度
    @Override
    public void onPause() {
        //可见时先设置界面的将不可见,可避免fragment生命周期的混乱
        if (getUserVisibleHint()) {
            onInvisible();
        }
        super.onPause();
        LogUtils.d(TAG, "不可见 -- onPause");

        mIsLoadData = false;
    }

    /**
     * 不可见时调用
     */
    protected void onInvisible() {
        LogUtils.d(TAG, "不可见时调用 : onInvisible");
        mIsLoadData = false;
    }

    /**
     * 可见时调用(懒加载)
     */
    protected void lazyLoad() {
        if (mErrorView) {
            LogUtils.d(TAG, "view error");
            return;
        }
        LogUtils.d(TAG,
                "mIsFirstLoad : " + mIsFirstLoad +
                        " mIsPrepared : " + mIsPrepared +
                        " mIsVisible : " + mIsVisible);

        if (mIsViewPager && !mIsVisible) {
            mIsViewPager = false;
        } else if (mIsFirstLoad && mIsVisible && !mIsPrepared) {
            LogUtils.d(TAG, "略过......");
        } else if (!mIsFirstLoad && mIsPrepared) {
            LogUtils.d(TAG, "先初始化，再懒加载");
            initData();
            mIsFirstLoad = false;
            mIsPrepared = false;
            if (!mIsViewPager || mVPFragmentDrawCompletion <= 1) {
                LogUtils.d(TAG, "执行懒加载");
                loadData();
            }
            mIsLoadData = true;
        } else {
            LogUtils.d(TAG, "直接执行懒加载");
            loadData();
            mIsLoadData = true;
        }

    }

    @Override
    public void onDestroy() {
        clearData();
        super.onDestroy();
    }


    /**
     * bind layout
     *
     * @return layout
     */
    protected abstract int bindLayout();

    /**
     * sub
     *
     * @param bind
     */
    protected abstract boolean convertView(ViewDataBinding bind);

    /**
     * =。= bug
     */
    protected void convertError() {
        LogUtils.e(TAG, "convertError");
    }


    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * clear data
     */
    protected void clearData() {
        if (mErrorView) return;
    }

    protected void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    protected void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getContext(), cls);
        startActivityForResult(intent, requestCode);
    }

    protected void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getContext(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


}
