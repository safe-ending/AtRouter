package com.at.arouter.third.ui.aty.work_order;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.at.arouter.common.base.BaseActivity;
import com.at.arouter.common.data.ARouterPath;
import com.at.arouter.common.data.Constants;
import com.at.arouter.common.listener.BaseClickHandler;
import com.at.arouter.common.listener.BaseDataHandler;
import com.at.arouter.common.util.ToastUtil;
import com.at.arouter.coremodel.callback.ObserverCallback;
import com.at.arouter.coremodel.APIManager;
import com.at.arouter.coremodel.viewmodel.entities.third.WorkOrderListBean;
import com.at.arouter.coremodel.http.model.RequestResult;
import com.at.arouter.coremodel.service.ThirdService;
import com.at.arouter.coremodel.viewmodel.CommonViewModel;
import com.at.arouter.third.R;
import com.at.arouter.third.bridge.m.adapter.WorkOrderAdapter;
import com.at.arouter.third.databinding.AtyWorkOrderBinding;
import com.at.arouter.third.utils.click.ItemClickListener;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * desc:  工单首页
 * author:  yangtao
 * <p>
 * creat: 2018/8/30 16:05
 */

@Route(path = ARouterPath.WorkListAty)
public class WorkOrderActivity extends BaseActivity implements PullLoadMoreRecyclerView.PullLoadMoreListener,
        ItemClickListener {

    private AtyWorkOrderBinding mBinding;
    private DataHandler mDataHandler;
    private ClickHandler mClickHandler;
    private WorkOrderAdapter mAdapter;
    private int mCount = 1;
    private boolean bLoadMore, mNextPage;


    @Override
    protected void bindMyView(ViewGroup parent, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.aty_work_order,
                parent,
                true);
        mBinding.setDataHandler(mDataHandler = DataHandler.create(savedInstanceState));
        mBinding.setClickHandler(mClickHandler = new ClickHandler(this));
        init();
    }

    private void init() {

        //测试国际化的应用
        getUIConfig().setTitle(getString(R.string.a917));

        mBinding.prvWorkOrder.getRecyclerView().setVerticalScrollBarEnabled(true);
        mBinding.prvWorkOrder.setPullRefreshEnable(true);
        mBinding.prvWorkOrder.setFooterViewText("loading");
        mBinding.prvWorkOrder.setLinearLayout();

        mAdapter = new WorkOrderAdapter(this);
        mBinding.prvWorkOrder.setAdapter(mAdapter);
        mBinding.prvWorkOrder.setOnPullLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);

        mBinding.rlAddWorkOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CreateWorkOrderActivity.class);
            }
        });

        viewModel = ViewModelProviders.of(WorkOrderActivity.this).get(CommonViewModel.class);
    }

    CommonViewModel viewModel;

    @Override
    protected BaseDataHandler.UIConfig getUIConfig() {
        return mDataHandler.uiConfig.get();
    }


    public class ClickHandler
            extends BaseClickHandler {
        private WorkOrderActivity workOrderActivity;

        public ClickHandler(WorkOrderActivity activity) {
            super(activity);
            this.workOrderActivity = activity;
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


    @Override
    protected void onResume() {
        super.onResume();
        mBinding.prvWorkOrder.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("code", mAdapter.getOrderCode(position));
        startActivity(WorkOrderDetailActivity.class, bundle);
    }

    @Override
    public void onItemClick(View view, int position, ArrayList<Object> lists) {
    }

    @Override
    public void onRefresh() {
        bLoadMore = false;
        mCount = 1;
        getWordOrderList();
    }

    @Override
    public void onLoadMore() {
        if (mNextPage) {
            bLoadMore = true;
            mCount += 1;
            getWordOrderList();
        } else {
            mBinding.prvWorkOrder.setPullLoadMoreCompleted();
            ToastUtil.show(getString(R.string.a632));
        }
    }

    private void getWordOrderList() {
        Observable<RequestResult<WorkOrderListBean>> observable = APIManager.buildTradeAPI(ThirdService.class).getOrderList(walletPref.getWallet(), Constants.PAGE_SIZE, mCount);


        viewModel.request(mActivity,null, observable, new ObserverCallback<WorkOrderListBean>() {
            @Override
            public void success(WorkOrderListBean workOrderListBean) {
                mBinding.prvWorkOrder.setPullLoadMoreCompleted();
                mNextPage = !workOrderListBean.isLast();
                if (bLoadMore) {
                    mAdapter.addData(workOrderListBean.getContent());
                } else {
                    mAdapter.addRefreshData(workOrderListBean.getContent());
                }
                setEmpty(workOrderListBean.getContent());
            }

            @Override
            public void failure(Throwable e) {
                mCount -= 1;
                mBinding.prvWorkOrder.setPullLoadMoreCompleted();
            }
        });

    }

    void setEmpty(List<WorkOrderListBean.ContentBean> noticeModels) {
        if (noticeModels == null || noticeModels.size() == 0) {
            mBinding.prvWorkOrder.setVisibility(View.GONE);
            mBinding.includeEmpty.setVisibility(View.VISIBLE);
        } else {
            mBinding.prvWorkOrder.setVisibility(View.VISIBLE);
            mBinding.includeEmpty.setVisibility(View.GONE);
        }
    }

}
