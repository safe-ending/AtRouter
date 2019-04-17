package com.at.arouter.third.ui.aty.notice;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.at.arouter.common.base.BaseActivity;
import com.at.arouter.common.callback.DataCallBack;
import com.at.arouter.common.data.ARouterPath;
import com.at.arouter.common.data.Constants;
import com.at.arouter.common.listener.BaseClickHandler;
import com.at.arouter.common.listener.BaseDataHandler;
import com.at.arouter.common.util.MultiLanguageUtil;
import com.at.arouter.coremodel.callback.ObserverCallback;
import com.at.arouter.coremodel.APIManager;
import com.at.arouter.coremodel.viewmodel.entities.third.NoticeModel;
import com.at.arouter.coremodel.http.model.RequestResult;
import com.at.arouter.coremodel.service.ThirdService;
import com.at.arouter.coremodel.viewmodel.ListViewModel;
import com.at.arouter.third.R;
import com.at.arouter.third.bridge.m.adapter.NoticeListAdapter;
import com.at.arouter.third.databinding.ActivityNoticeBinding;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * desc:  公告页面
 * author:  yangtao
 * <p>
 * creat: 2018/8/29 16:05
 */


@Route(path = ARouterPath.NoticeListAty)
public class NoticeActivity extends BaseActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener {

    private static final String TAG = NoticeActivity.class.getSimpleName();
    private ActivityNoticeBinding mBinding;
    private DataHandler mDataHandler;
    private ClickHandler mClickHandler;

    @Override
    protected void bindMyView(ViewGroup parent, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.activity_notice,
                parent,
                true);
        mDataHandler = DataHandler.create(savedInstanceState);
        mClickHandler = new ClickHandler(this);


        init();
    }

    ArrayList<NoticeModel> list = new ArrayList<>();
    NoticeListAdapter noticeListAdapter;

    void init() {

        getUIConfig().setTitle(getString(R.string.mine_notice));
        mBinding.recycleView.getRecyclerView().setVerticalScrollBarEnabled(true);
        mBinding.recycleView.setPullRefreshEnable(true);
        mBinding.recycleView.setFooterViewText("loading");
        mBinding.recycleView.setLinearLayout();
        mBinding.recycleView.setOnPullLoadMoreListener(this);

        noticeListAdapter = new NoticeListAdapter(list);
        noticeListAdapter.isFirstOnly(false);
        mBinding.recycleView.setAdapter(noticeListAdapter);
        //可能需要移除之前添加的布局
        noticeListAdapter.removeAllFooterView();
        noticeListAdapter.notifyDataSetChanged();

        viewModel = ViewModelProviders.of(NoticeActivity.this).get(ListViewModel.class);
        setLoadSuccessView(mBinding.recycleView, new DataCallBack() {
            @Override
            public void reload() {
                onRefresh();
            }
        });

        if (commonPref.getLanguage().equals(MultiLanguageUtil.TYPE_CN)) {
            getData("zh_CN");
        } else {
            getData("zh_EN");
        }

    }

    ListViewModel viewModel;

    @Override
    public void onRefresh() {
        mBinding.recycleView.setRefreshing(true);
        if (commonPref.getLanguage().equals(MultiLanguageUtil.TYPE_CN)) {
            getData("zh_CN");
        } else {
            getData("zh_EN");
        }
    }

    @Override
    public void onLoadMore() {

    }

    void getData(String lang) {
        Observable<RequestResult<ArrayList<NoticeModel>>> observable = APIManager.buildTradeAPI(ThirdService.class).getNotice(lang, true);
        viewModel.request(NoticeActivity.this, loadService, observable, new ObserverCallback<ArrayList<NoticeModel>>() {
            @Override
            public void success(ArrayList<NoticeModel> supportCoinModelRequestResult) {
                mBinding.recycleView.setPullLoadMoreCompleted();
                noticeListAdapter.setNewData(supportCoinModelRequestResult);
//                setEmpty(supportCoinModelRequestResult);
            }

            @Override
            public void failure(Throwable e) {
                mBinding.recycleView.setPullLoadMoreCompleted();
            }
        });

    }

    void setEmpty(List<NoticeModel> noticeModels) {
        if (noticeModels == null || noticeModels.size() == 0) {
            mBinding.recycleView.setVisibility(View.GONE);
            mBinding.includeEmpty.setVisibility(View.VISIBLE);
        } else {
            mBinding.recycleView.setVisibility(View.VISIBLE);
            mBinding.includeEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    protected BaseDataHandler.UIConfig getUIConfig() {
        return mDataHandler.uiConfig.get();
    }


    public class ClickHandler
            extends BaseClickHandler {
        private NoticeActivity moudleActivity;

        public ClickHandler(NoticeActivity activity) {
            super(activity);
            this.moudleActivity = activity;
        }

    }

    @Parcel
    public static class DataHandler
            extends BaseDataHandler {

        public static DataHandler create(Bundle savedInstanceState) {
            DataHandler result = null;
            if (savedInstanceState != null) {
                result = Parcels.unwrap(savedInstanceState.getParcelable(Constants.SAVED_STATE_DATA_HANDLER));
            }

            if (result == null) {
                result = new DataHandler();
            }
            return result;
        }
    }
}
