package com.at.arouter.third.ui.frg;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.at.arouter.common.base.BaseFragment;
import com.at.arouter.common.util.ToastUtil;
import com.at.arouter.third.R;
import com.at.arouter.third.bridge.m.adapter.NewsAdapter;
import com.at.arouter.third.bridge.m.adapter.NewsFlashAdapter;
import com.at.arouter.third.databinding.FrgListBinding;
import com.at.arouter.third.ui.aty.news.NewsDetailActivity;
import com.at.arouter.third.utils.click.ItemClickListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.at.arouter.third.utils.Constants.DETAIL;
import static com.at.arouter.third.utils.Constants.NEWSFLASH;
import static com.at.arouter.third.utils.Constants.TYPE;


/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class ListFragment extends BaseFragment implements OnRefreshListener, ItemClickListener {

    private FrgListBinding mBind;
    private String mType;

    private StaggeredGridLayoutManager mLayoutManager;
    private NewsAdapter mNewsAdapter;
    private NewsFlashAdapter mNewsFlashAdapter;

    private boolean bLoadMore;
    private int mCount = 1;
    // recyclerview最后显示的Item,用于判断recyclerview自动加载下一页
    private int lastVisibleItem;


    public static ListFragment newInstance() {
        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int bindLayout() {
        mIsViewPager = true;
        return R.layout.frg_list;
    }

    @Override
    protected boolean convertView(ViewDataBinding bind) {
        boolean error = false;
        if (bind instanceof FrgListBinding) {
            mBind = (FrgListBinding) bind;
        } else {
            Log.e(TAG, ERROR_TAG + bind.getClass().getSimpleName());
            error = true;
        }
        return error;
    }

    void init() {
        mType = getArguments().getString(TYPE);
        mVPFragmentDrawCompletion = Integer.MIN_VALUE;
        mBind.srlRefresh.setOnRefreshListener(this);

        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mBind.rvList.setLayoutManager(mLayoutManager);
        if (TextUtils.equals(mType, NEWSFLASH) && mNewsFlashAdapter == null) {
            mNewsFlashAdapter = new NewsFlashAdapter(getContext());
            mNewsFlashAdapter.setOnItemClickListener(this);
        } else if (!TextUtils.equals(mType, NEWSFLASH) && mNewsAdapter == null) {
            mNewsAdapter = new NewsAdapter(getContext());
            mNewsAdapter.setOnItemClickListener(this);
        }
        mBind.rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // State三种状态：
                // SCROLL_STATE_IDLE（静止）、
                // SCROLL_STATE_DRAGGING（上升）、
                // SCROLL_STATE_SETTLING（下落）
                if (newState == SCROLL_STATE_IDLE) {
                    if (!TextUtils.equals(mType, NEWSFLASH)) {
                        // Glide.with(getContext()).resumeRequests();
                        mNewsAdapter.setScrolling(false);
                        mNewsAdapter.notifyDataSetChanged();
                    }
                    if (lastVisibleItem + 1 >= mLayoutManager.getItemCount()) {
                        bLoadMore = true;
                        mCount += 1;
                        if (TextUtils.equals(mType, NEWSFLASH)) {
                            getNewsFlash();
                        } else getConsultPages();
                    }
                } else if (!TextUtils.equals(mType, NEWSFLASH)) {
                    // Glide.with(getContext()).pauseRequests();
                    mNewsAdapter.setScrolling(true);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] positions = mLayoutManager.findLastVisibleItemPositions(null);
                // lastVisibleItem = Math.max(positions[0], positions[1]);
                lastVisibleItem = positions[0];
            }
        });
        mBind.rvList.setAdapter(TextUtils.equals(mType, NEWSFLASH) ? mNewsFlashAdapter : mNewsAdapter);
    }

    @Override
    protected void loadData() {
        bLoadMore = false;
        mCount = 1;
        mBind.srlRefresh.autoRefresh();


    }

    @Override
    protected void initData() {
        init();
    }

    @Override
    public void reload() {

    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        // cancel request data
    }

    public void setInvisible() {
        if (TextUtils.equals(mType, NEWSFLASH) && mNewsFlashAdapter != null) {
            loadData();
        } else if (!TextUtils.equals(mType, NEWSFLASH) && mNewsAdapter != null) {
            loadData();
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        bLoadMore = false;
        mCount = 1;
        if (TextUtils.equals(mType, NEWSFLASH)) {
            getNewsFlash();
        } else getConsultPages();
    }

    @Override
    public void onItemClick(View view, int position) {
        if (!TextUtils.equals(mType, NEWSFLASH) && view.getParent() == mBind.rvList) {
            Bundle bundle = new Bundle();
            bundle.putStringArray(DETAIL, mNewsAdapter.getValue(position));
            startActivity(NewsDetailActivity.class, bundle);
        } else if (TextUtils.equals(mType, NEWSFLASH)) {
            int i = view.getId();
            if (i == R.id.iv_share || i == R.id.iv_comment || i == R.id.iv_fabulous) {
                ToastUtil.show(getString(R.string.wait_build));

            }
        }
    }

    @Override
    public void onItemClick(View view, int position, ArrayList<Object> lists) {
    }

    private void getConsultPages() {
//        APIManager.buildTradeAPI(APIHostManager.Trade_Url).getHotList(mCount,
//                10,
//                mType,
//                new Callback<RequestResult<NewsBean>>() {
//                    @Override
//                    public void success(RequestResult<NewsBean> commentBeanRequestResult, Response response) {
//                        LogUtils.e("新闻条数"+commentBeanRequestResult.result.getContent().size() );
//                        if (APIManager.hasError(mActivity, commentBeanRequestResult)) {
//                            if (!bLoadMore) {
//                                mBind.srlRefresh.finishRefresh();
//                            } else {
//                                mCount -= 1;
//                            }
//                            return;
//                        }
//                        if (!bLoadMore) {
//                            mBind.srlRefresh.finishRefresh();
//                            mNewsAdapter.addNewData(commentBeanRequestResult.result.getContent() == null ? new ArrayList<>() : commentBeanRequestResult.result.getContent());
//                        } else {
//                            mNewsAdapter.addData(commentBeanRequestResult.result.getContent() == null ? new ArrayList<>() : commentBeanRequestResult.result.getContent());
//                        }
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        APIManager.showError(mActivity, error);
//                        if (!bLoadMore) {
//                            mBind.srlRefresh.finishRefresh();
//                        } else {
//                            mCount -= 1;
//                        }
//                    }
//                });

    }

    private void getNewsFlash() {
//        APIManager.buildTradeAPI(APIHostManager.Trade_Url).getLiveList(mCount,
//                10,
//                new Callback<RequestResult<NewsFlashBean>>() {
//                    @Override
//                    public void success(RequestResult<NewsFlashBean> commentBeanRequestResult, Response response) {
//                        if (APIManager.hasError(mActivity, commentBeanRequestResult)) {
//                            if (!bLoadMore) {
//                                mBind.srlRefresh.finishRefresh();
//                            } else {
//                                mCount -= 1;
//                            }
//                            return;
//                        }
//                        if (!bLoadMore) {
//                            mBind.srlRefresh.finishRefresh();
//                            mNewsFlashAdapter.addNewData(commentBeanRequestResult.result.getContent() == null ? new ArrayList<>() : commentBeanRequestResult.result.getContent());
//                        } else {
//                            mNewsFlashAdapter.addData(commentBeanRequestResult.result.getContent() == null ? new ArrayList<>() : commentBeanRequestResult.result.getContent());
//                        }
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        APIManager.showError(mActivity, error);
//                        if (!bLoadMore) {
//                            mBind.srlRefresh.finishRefresh();
//                        } else {
//                            mCount -= 1;
//                        }
//                    }
//                });
    }

}
