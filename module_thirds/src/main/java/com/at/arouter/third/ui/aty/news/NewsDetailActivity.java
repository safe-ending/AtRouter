package com.at.arouter.third.ui.aty.news;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;

import com.at.arouter.common.base.BaseActivity;
import com.at.arouter.common.data.APIHostManager;
import com.at.arouter.common.listener.BaseClickHandler;
import com.at.arouter.common.listener.BaseDataHandler;
import com.at.arouter.common.data.Constants;
import com.at.arouter.common.util.ToastUtil;
import com.at.arouter.common.util.Tools;
import com.at.arouter.third.R;
import com.at.arouter.third.bridge.CommentDialog;
import com.at.arouter.third.bridge.bean.CommentBean;
import com.at.arouter.third.bridge.m.adapter.CommentAdapter;
import com.at.arouter.third.databinding.AtyNewsDetailBinding;
import com.at.arouter.third.ui.view.EditTextMonitor;
import com.at.arouter.third.ui.view.GradationScrollView;
import com.at.arouter.third.utils.click.ItemClickListener;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * desc:  咨询详情
 * author:  yangtao
 * <p>
 * creat: 2018/8/30 16:05
 */


public class NewsDetailActivity extends BaseActivity implements OnLoadmoreListener,
        EditTextMonitor.BackListener, ItemClickListener, GradationScrollView.ScrollViewListener, View.OnClickListener {

    private String mId, mShareTitle, mShareContent;
    private int mCount = 1;
    private CommentAdapter mAdapter;
    private CommentDialog mCommentDialog;

    private boolean mIsSuccess, mIsError;
    private AtyNewsDetailBinding mBinding;
    private DataHandler mDataHandler;
    private ClickHandler mClickHandler;

    @Override
    protected void bindMyView(ViewGroup parent, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.aty_news_detail,
                parent,
                true);
        mBinding.setDataHandler(mDataHandler = DataHandler.create(savedInstanceState));
        mBinding.setClickHandler(mClickHandler = new ClickHandler(this));
        init();
    }

    public void init() {
        String[] array = getIntent().getExtras().getStringArray("detail");
        if (array != null) {
            mId = array[0];
            mShareTitle = array[1];
            mShareContent = array[2];
        }
        logicBusiness();
    }

    @Override
    protected BaseDataHandler.UIConfig getUIConfig() {
        return mDataHandler.uiConfig.get();
    }


    public class ClickHandler
            extends BaseClickHandler {
        private NewsDetailActivity newsDetailActivity;

        public ClickHandler(NewsDetailActivity activity) {
            super(activity);
            this.newsDetailActivity = activity;
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
    protected void clearData() {
        super.clearData();
        //清空所有Cookie
        CookieSyncManager.createInstance(this.getApplicationContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();

        mBinding.webNews.setWebChromeClient(null);
        mBinding.webNews.setWebViewClient(null);
        mBinding.webNews.getSettings().setJavaScriptEnabled(false);
        mBinding.webNews.clearCache(true);
    }

    private void logicBusiness() {
        getUIConfig().setTitle(getString(R.string.new_details));

        setBtnRight2Res(R.color.color_FFFFFF);
        getUIConfig().setShowRightButton2(false);


        mBinding.ivScrollTop.setVisibility(View.GONE);
        mBinding.rlCommentTitle.setVisibility(View.GONE);

        initWebView();

        mBinding.rvComment.setLayoutManager(Tools.getNotRollLinearLayoutManager(this));
        mBinding.rvComment.setAdapter(mAdapter = new CommentAdapter(this));
        mAdapter.setOnItemClickListener(this);

        mBinding.srlRefresh.setEnableRefresh(false);
        mBinding.srlRefresh.setEnableLoadMore(false);
        mBinding.srlRefresh.setOnLoadmoreListener(this);

        mBinding.ivScrollTop.setOnClickListener(this);
        mBinding.tvComment.setOnClickListener(this);
//        mBinding.ivShare.setOnClickListener(this);
        mBinding.ivComment.setOnClickListener(this);


        getData();
    }

    @Override
    protected void onClickRightButton2() {
        ToastUtil.show(getString(R.string.wait_build));
    }

    private void getData() {
        String ss = APIHostManager.Trade_Url + "/information/news/index" +"?"+ String.valueOf("id" + "=" + mId + "&" + "member" + "=" + walletPref.getWallet());
//        LogUtils.e("url" , ss);
//        mBinding.webNews.loadUrl(ss);
        mBinding.webNews.postUrl(APIHostManager.Trade_Url + "/information/news/index",
                String.valueOf("id" + "=" + mId
                        + "&" + "member" + "=" + walletPref.getWallet())
                        .getBytes());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {

        WebSettings mWebSettings = mBinding.webNews.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);

        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setUseWideViewPort(true);

        mWebSettings.setUserAgentString("atkj");

        mBinding.webNews.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //if (!mIsError) {
                mIsSuccess = true;
                mBinding.rlCommentTitle.setVisibility(View.VISIBLE);
                initListener();
                getCommentsList();
                new Handler().postDelayed(() -> mBinding.gsvView.fullScroll(ScrollView.FOCUS_UP), 50);
                //}
                // mIsError = false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mIsError = true;
                mIsSuccess = false;
            }
        });

    }

    private void initListener() {
        mBinding.webNews.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBinding.ivScrollTop.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                // mViewHeight = webNews.getHeight();
                mBinding.gsvView.setScrollViewListener(NewsDetailActivity.this);
            }
        });
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_scroll_top) {
            mBinding.gsvView.fullScroll(ScrollView.FOCUS_UP);

        } else if (i == R.id.tv_comment) {
            showCommentDialog(-1);

        } else if (i == R.id.iv_comment) {
            mBinding.gsvView.post(() -> mBinding.gsvView.scrollTo(0, mBinding.rlCommentTitle.getTop()));

        } else if (i == R.id.iv_share) {
            ToastUtil.show(getString(R.string.wait_build));
//                getInviteCode();

        }
    }


    @Override
    public void onBack() {
        if (mCommentDialog != null && mCommentDialog.getShowsDialog()) {
            mCommentDialog.dismiss();
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mCount += 1;
        getCommentsList();
    }

    @Override
    public void onItemClick(View view, int position) {
    }

    @Override
    public void onItemClick(View view, int position, ArrayList<Object> lists) {
        int i = view.getId();
        if (i == R.id.iv_comment) {
            if (lists.get(position) instanceof CommentBean.ContentBean) {
                showCommentDialog(((CommentBean.ContentBean) lists.get(position)).getId());
            }

        } else if (i == R.id.tv_reply_comment) {
            if (lists.get(position) instanceof CommentBean.ContentBean.ChildBean) {
                showCommentDialog(((CommentBean.ContentBean.ChildBean) lists.get(position)).getId());
            }

        } else if (i == R.id.tv_look_all_comments) {//                if (lists.get(position) instanceof CommentBean.ContentBean) {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt(appPref.getAddress(), ((CommentBean.ContentBean) lists.get(position)).getId());
//                    String[] value = new String[3];
//                    value[0] = mId;
//                    value[1] = mShareTitle;
//                    value[2] = mShareContent;
//                    bundle.putStringArray("detail", value);
//                    startActivity(CommentDetailActivity.class, bundle);
//                }

        }
    }

    private void showCommentDialog(int id) {
        mCommentDialog = new CommentDialog(getString(R.string.a742), "", (inputText) -> {
            mCount = 1;
            commentOnMessage(inputText, id);
        });
        mCommentDialog.show(getSupportFragmentManager(), "comment");
    }

    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            if (mBinding.ivScrollTop.getVisibility() == View.VISIBLE) {
                Animation startAlphaAnim = AnimationUtils.loadAnimation(this, R.anim.scroll_bar_gone);
                mBinding.ivScrollTop.startAnimation(startAlphaAnim);
                mBinding.ivScrollTop.setVisibility(View.GONE);
            }
        }/*else if (y > 0 && y <= mViewHeight) {
        } */ else {
            if (mBinding.ivScrollTop.getVisibility() == View.GONE) {
                mBinding.ivScrollTop.setVisibility(View.VISIBLE);
                Animation startAlphaAnim = AnimationUtils.loadAnimation(this, R.anim.scroll_bar_into);
                mBinding.ivScrollTop.startAnimation(startAlphaAnim);
            }
        }
    }

    public void getInviteCode() {
        if (TextUtils.isEmpty(mShareTitle)) {
            ToastUtil.show(getString(R.string.a628));
            return;
        }
//        showLoadingDialog();

//        Model.getInviteCode(this, new IModel.AsyncCallBackInviteCode() {
//            @Override
//            public void onSuccess(Result<InviteCodeBean> success) {
//                if (!isAttachView()) return;
//                dismissDialog();
//                OnekeyShare oks = new OnekeyShare();
//                //关闭sso授权
//                oks.disableSSOWhenAuthorize();
//                oks.setTitle(mShareTitle);
//                oks.setText(mShareContent);
//                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//                // oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//                oks.setImageData(BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
//                // url仅在微信（包括好友和朋友圈）中使用
//                oks.setUrl(CONSULT_DETAIL + "?" + ID + "=" + mId + "&" + INVITE_CODE + "=" + success.getResult().getInviteCode());
//                // 启动分享GUI
//                oks.show(NewsDetailActivity.this);
//            }
//
//            @Override
//            public void onError(Object error) {
//                if (!isAttachView()) return;
//                dismissDialog();
//                exceptionBusiness(error.toString());
//            }
//        });
    }

    private void commentOnMessage(String commentContent, int parentId) {
//        HashMap<String, String> body = HttpUtils.getBody();
//        body.put(MEMBER, (String) SPUtils.get(this, ID, ""));
//        body.put(NICKNAME, (String) SPUtils.get(this, USER_NAME, ""));
//        body.put(CONTENT, commentContent);
//        if (parentId != -1) body.put(PARENT, String.valueOf(parentId));
//        HttpUtils
//                .postData(this,
//                        0,
//                        body,
//                        ADD_COMMENT + mId + ADD_COMMENT_TAILING,
//                        "tag")
//                .execute(new ResponseCallback<BaseBean>() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        if (!isAttachView()) return;
//                        exceptionBusiness(e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(BaseBean response, int id) {
//                        if (!isAttachView()) return;
//                        if (response.getStatus() == 1) {
//                            getCommentsList();
//                        } else response.showMsg(NewsDetailActivity.this);
//                    }
//                });
    }

    private void getCommentsList() {
//        HashMap<String, String> body = HttpUtils.getBody();
//        body.put(PAGE, mCount + "");
//        body.put(SIZE, "10");
//        HttpUtils
//                .getData(this,
//                        0,
//                        body,
//                        COMMENTS_LIST + mId + COMMENT_LIST_TAILING,
//                        "tag")
//                .execute(new ResponseCallback<Result<CommentBean>>() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        if (!isAttachView()) return;
//                        mBind.srlRefresh.finishLoadmore();
//                        exceptionBusiness(e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(Result<CommentBean> response, int id) {
//                        if (!isAttachView()) return;
//                        mBind.srlRefresh.finishLoadmore();
//                        if (response.getStatus() == 1) {
//                            if (mCount == 1) {
//                                mAdapter.addRefreshCommentData(response.getResult().getContent());
//                            } else mAdapter.addCommentData(response.getResult().getContent());
//                        } else response.showMsg(NewsDetailActivity.this);
//                    }
//                });
    }

}
