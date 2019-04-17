package com.at.arouter.third.ui.aty.work_order;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.at.arouter.common.base.BaseActivity;
import com.at.arouter.common.data.ARouterPath;
import com.at.arouter.common.data.Constants;
import com.at.arouter.common.listener.BaseClickHandler;
import com.at.arouter.common.listener.BaseDataHandler;
import com.at.arouter.coremodel.callback.ErrorCallback;
import com.at.arouter.coremodel.callback.LoadingCallback;
import com.at.arouter.coremodel.callback.ObserverCallback;
import com.at.arouter.coremodel.APIManager;
import com.at.arouter.coremodel.viewmodel.entities.third.WorkOrderDetailBean;
import com.at.arouter.coremodel.http.model.RequestResult;
import com.at.arouter.coremodel.service.ThirdService;
import com.at.arouter.coremodel.util.TAGUtils;
import com.at.arouter.coremodel.viewmodel.CommonViewModel;
import com.at.arouter.third.R;
import com.at.arouter.third.bridge.m.adapter.WorkOrderReplyAdapter;
import com.at.arouter.third.databinding.AtyWorkOrderDetailBinding;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.parceler.Parcel;
import org.parceler.Parcels;

import io.reactivex.Observable;


/**
 * desc:  工单详情
 * author:  yangtao
 * <p>
 * creat: 2018/8/30 16:05
 */
@Route(path = ARouterPath.WorkDetailAty)
public class WorkOrderDetailActivity extends BaseActivity implements OnRefreshListener {


    private String mOrderCode;
    private LoadService loadService;
    private boolean mRefresh;
    private AtyWorkOrderDetailBinding mBinding;
    private DataHandler mDataHandler;
    private ClickHandler mClickHandler;
    private WorkOrderReplyAdapter mAdapter;

    @Override
    protected void bindMyView(ViewGroup parent, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.aty_work_order_detail,
                parent,
                true);
        mBinding.setDataHandler(mDataHandler = DataHandler.create(savedInstanceState));
        mBinding.setClickHandler(mClickHandler = new ClickHandler(this));
        init();
    }


    private void init() {
        mOrderCode = (String) getIntent().getExtras().get("code");
        getUIConfig().setTitle(getString(R.string.order_details));
        loadService = LoadSir.getDefault().register(this, (v) -> getOrderDetail());

        mBinding.srlRefresh.setOnRefreshListener(this);

        mBinding.rvReply.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvReply.setNestedScrollingEnabled(false);
        mBinding.rvReply.setFocusableInTouchMode(false);
        mBinding.rvReply.setAdapter(mAdapter = new WorkOrderReplyAdapter(this));

        mBinding.tvResolved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complete();
            }
        });

        mBinding.tvUnsolved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("code", mOrderCode);
                bundle.putInt("id", mAdapter.getAdminId());
                startActivity(CreateWorkOrderActivity.class, bundle);
            }
        });
        viewModel = ViewModelProviders.of(WorkOrderDetailActivity.this).get(CommonViewModel.class);

    }

    CommonViewModel viewModel;

    @Override
    protected void onResume() {
        super.onResume();
        getOrderDetail();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mRefresh = true;
        getOrderDetail();
    }

    private void complete() {
        showLoadingDialog();
        Observable<RequestResult<?>> observable = APIManager.buildTradeAPI(ThirdService.class).getOrderDetails(mOrderCode, true);

        viewModel.request(mActivity,null, observable, new ObserverCallback() {
            @Override
            public void success(Object o) {
                Log.i(TAGUtils.LOG_TAG, "subscribeToModel onChanged onChanged");
                dismissDialog();
                finish();
            }

            @Override
            public void failure(Throwable e) {
                dismissDialog();
            }
        });

    }

    private void getOrderDetail() {
        if (!mRefresh) loadService.showCallback(LoadingCallback.class);
        Observable<RequestResult<WorkOrderDetailBean>> observable = APIManager.buildTradeAPI(ThirdService.class).getOrderDetails2(mOrderCode);
        viewModel.request(mActivity,null, observable, new ObserverCallback<WorkOrderDetailBean>() {
            @Override
            public void success(WorkOrderDetailBean workOrderDetailBean) {
                Log.i(TAGUtils.LOG_TAG, "subscribeToModel onChanged onChanged");
                if (mRefresh) {
                    mBinding.srlRefresh.finishRefresh();
                } else {
                    loadService.showSuccess();
                }
                WorkOrderDetailBean.MessagesBean user = new WorkOrderDetailBean.MessagesBean();
                user.setAuthor(walletPref.getWallet());
                user.setDetail(workOrderDetailBean.getContent());
                user.setCreateTime(workOrderDetailBean.getCreateTime());
                workOrderDetailBean.getMessages().add(user);
                mAdapter.addRefreshData(workOrderDetailBean.getMessages());
                if (TextUtils.equals(workOrderDetailBean.getStatus(), "PROCESS")) {
                    mBinding.llReply.setVisibility(View.VISIBLE);
                } else {
                    mBinding.llReply.setVisibility(View.GONE);
                }
                mRefresh = false;
            }

            @Override
            public void failure(Throwable e) {
                if (mRefresh) {
                    mBinding.srlRefresh.finishRefresh();
                } else loadService.showCallback(ErrorCallback.class);
                mRefresh = false;
            }
        });

    }

    @Override
    protected BaseDataHandler.UIConfig getUIConfig() {
        return mDataHandler.uiConfig.get();
    }


    public class ClickHandler
            extends BaseClickHandler {
        private WorkOrderDetailActivity workOrderDetailActivity;

        public ClickHandler(WorkOrderDetailActivity activity) {
            super(activity);
            this.workOrderDetailActivity = activity;
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
