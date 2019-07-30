package com.at.kchart.charting.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.at.arouter.common.data.Constants;
import com.at.arouter.common.util.LogUtils;
import com.at.arouter.coremodel.http.callback.ObserverCallback;
import com.at.arouter.coremodel.APIManager;
import com.at.arouter.coremodel.http.model.RequestResult;
import com.at.arouter.coremodel.service.KchartService;
import com.at.arouter.coremodel.http.util.TAGUtils;
import com.at.arouter.coremodel.viewmodel.CommonViewModel;
import com.at.arouter.coremodel.viewmodel.entities.kchart.KLineDataModel;
import com.at.arouter.kchart.R;
import com.at.kchart.charting.stockChart.CoupleChartGestureListener;
import com.at.kchart.charting.stockChart.data.KLineDataManage;
import com.at.kchart.charting.stockChart.view.KLineView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import io.reactivex.Observable;


/**
 * by yangtao on 2018 10.10
 * K线
 */
public class ChartKLineFragment extends BaseFragment {


    KLineView combinedchart;

    private double mType;//5分钟 0.005   30分钟0.03  1小时 0.1   日K：1；周K：7；月K：30
    private boolean land;//是否横屏
    private KLineDataManage kLineData;
    private int indexType = 1;
    private boolean isInitData = false;//界面是否初始化过
    private boolean isVisibleToUser = false;
    private AsyncTask executeTask;

    public static ChartKLineFragment newInstance(double type, boolean land, String ticket) {
        ChartKLineFragment fragment = new ChartKLineFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble("type", type);
        bundle.putBoolean("landscape", land);
        bundle.putString(Constants.TRADE_TICKET, ticket);
        fragment.setArguments(bundle);
        return fragment;

    }

    //定时刷新
    public static final int GET_DATA = 0x23;
    public static final int GET_DATA_TIME = 1000 * 60;
    private boolean runableIsAlive = false;
    MyHandler myHandler;
    String pageType = "M1";

    //弱引用
    static class MyHandler extends Handler {
        WeakReference<ChartKLineFragment> mWeakReference;

        MyHandler(ChartKLineFragment fragment) {
            mWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            final ChartKLineFragment fragment = mWeakReference.get();
            if (fragment != null) {
                if (msg.what == GET_DATA && fragment.runableIsAlive) {
                    fragment.getKlineData(fragment.getArguments().getString(Constants.TRADE_TICKET), fragment.pageType);
                    this.postDelayed(fragment.runnable, GET_DATA_TIME);
                    LogUtils.e(LogUtils.LOG_TAG, "执行获取");
                }
            }
        }
    }


    //线程
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            myHandler.sendEmptyMessage(GET_DATA);
        }
    };

    public void startClock() {
        //开启线程
        if (myHandler == null) {
            myHandler = new MyHandler(this);
        }
        if (!runableIsAlive) {
            runableIsAlive = true;
            myHandler.sendEmptyMessage(GET_DATA);
            LogUtils.e(TAGUtils.LOG_TAG, "开启线程" + mType);
        }
    }

    public void stopClock() {
        runableIsAlive = false;
        LogUtils.e(TAGUtils.LOG_TAG, "移除线程");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser && isInitData == false) {
            isInitData = true;
            startClock();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mType == 0.005) {
            isInitData = true;
            startClock();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopClock();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消异步任务
        if (executeTask != null && executeTask.getStatus() == AsyncTask.Status.RUNNING) {
            executeTask.cancel(true);
        }
    }

    private void loadIndexData(int type) {
        indexType = type;
        switch (indexType) {
            case 1://成交量
                combinedchart.doBarChartSwitch(indexType);
                break;
            case 2://请求MACD
                kLineData.initMACD();
                combinedchart.doBarChartSwitch(indexType);
                break;
            case 3://请求KDJ
                kLineData.initKDJ();
                combinedchart.doBarChartSwitch(indexType);
                break;
            case 4://请求BOLL
                kLineData.initBOLL();
                combinedchart.doBarChartSwitch(indexType);
                break;
            case 5://请求RSI
                kLineData.initRSI();
                combinedchart.doBarChartSwitch(indexType);
                break;
            default:
                break;
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_kline;
    }

    @Override
    public void updateIndex() {
        combinedchart.setIndex(this.getId(), combinedchart.getPosition());
    }


    @Override
    protected void initBase(View view, Bundle savedInstanceState) {
        mType = getArguments().getDouble("type");
        land = getArguments().getBoolean("landscape", false);
        kLineData = new KLineDataManage(getActivity());
        combinedchart = view.findViewById(R.id.combinedchart);
        combinedchart.initChart(land);

        combinedchart.getGestureListenerCandle().setCoupleClick(new CoupleChartGestureListener.CoupleClick() {
            @Override
            public void singleClickListener() {
                //单击横屏
//                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制为竖屏
            }
        });

        combinedchart.getGestureListenerBar().setCoupleClick(new CoupleChartGestureListener.CoupleClick() {
            @Override
            public void singleClickListener() {
                loadIndexData(indexType < 5 ? ++indexType : 1);

            }
        });
        if (mType == 100) {
            return;
        }

//        if (TextUtils.isEmpty(getArguments().getString(Constants.TRADE_TICKET)) || "null".equals(getArguments().getString(Constants.TRADE_TICKET))) {
//            ToastUtil.show("ticket null");
//            return;
//        }
        if (mType == 0.005) {
            pageType = "M5";

        } else if (mType == 0.03) {
            pageType = "M30";

        } else if (mType == 0.1) {
            pageType = "H1";

        } else if (mType == 1) {
            pageType = "D1";

        } else if (mType == 7) {
            pageType = "W1";
        }
        //暂时写死
//        getKlineData(ethbtc"", dcId);
//        if (mType == 0.005 || isVisibleToUser && isInitData == false) {
//            UiUtils.errPrint("initBase " + mType + (!isVisibleToUser ? "隐藏" : "显示"));
//            isInitData = true;
//            getKlineData(getArguments().getString(Constants.TRADE_TICKET), dcId);
//        }
//            getKlineData(getArguments().getString(Constants.TRADE_TICKET), dcId);
    }

    void getKlineData(String ticket, String type) {
        Observable<RequestResult<ArrayList<KLineDataModel>>> observable = APIManager.buildTradeAPI(KchartService.class).getTrade(ticket, type);

        CommonViewModel viewModel = ViewModelProviders.of(ChartKLineFragment.this).get(CommonViewModel.class);
        viewModel.request(mAct,null, observable, new ObserverCallback<ArrayList<KLineDataModel>>() {
            @Override
            public void success(ArrayList<KLineDataModel> kLineDataModels) {
                //数据越多  缩放程度可越大
//                assetDetailsModelRequestResult.result.addAll(assetDetailsModelRequestResult.result);
                //标识000001.IDX.SH
                long l = System.currentTimeMillis();
                kLineData.parseKlineData(kLineDataModels, "000001.IDX.SH", land);
                combinedchart.setDataToChart(kLineData);
            }

            @Override
            public void failure(Throwable e) {
                combinedchart.setEmptyData();
            }
        });

    }


}
