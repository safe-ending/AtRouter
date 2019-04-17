package com.at.arouter.common.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.at.arouter.common.R;
import com.at.arouter.common.base.BaseActivity;
import com.at.arouter.common.data.Constants;
import com.at.arouter.common.databinding.ActivityWebBinding;
import com.at.arouter.common.listener.BaseClickHandler;
import com.at.arouter.common.listener.BaseDataHandler;

import org.parceler.Parcel;
import org.parceler.Parcels;

/**
 * desc:  web静态页面
 * author:  yangtao
 * <p>
 * creat: 2018/8/18 12:05
 */

public class WebActivity extends BaseActivity {

    private static final String TAG = WebActivity.class.getSimpleName();
    private ActivityWebBinding mBinding;
    private DataHandler mDataHandler;
    private ClickHandler mClickHandler;
    //网络请求
    public final static int TYPE_REQUEST = 0;
    //显示本地html data
    public final static int TYPE_LOCAL = 1;


    @Override
    protected void bindMyView(ViewGroup parent, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.activity_web,
                parent,
                true);
        mBinding.setDataHandler(mDataHandler = DataHandler.create(savedInstanceState));
        mBinding.setClickHandler(mClickHandler = new ClickHandler(this));


        init();
    }

    void init() {
        initEvent();
        initData();
    }

    void initEvent() {
        mBinding.pb.setMax(100);
        mBinding.webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //调用拨号程序
                if (url.startsWith("mailto:") || url.startsWith("geo:") || url.startsWith("tel:") || url.startsWith("smsto:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }

                // 如果不需要其他对点击链接事件的处理返回true，否则返回false
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mBinding.webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        mBinding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mBinding.pb.setProgress(newProgress);
                if (newProgress >= 100) {
                    mBinding.pb.setVisibility(View.GONE);
                }
            }
        });
    }

    int type = 0;

    private void initData() {
        Intent intent = this.getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        type = intent.getIntExtra("type", 0);

        if (TYPE_REQUEST == type) {
            getUIConfig().setTitle(title);
            //webView.loadUrl(url + "?random=" + new Random().nextInt(100000));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mBinding.webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }

            // 这两行代码一定加上否则效果不会出现
            mBinding.webView.getSettings().setJavaScriptEnabled(true);
            mBinding.webView.getSettings().setDefaultTextEncodingName("gb2312");

            mBinding.webView.getSettings().setUseWideViewPort(true);//自适应
            mBinding.webView.getSettings().setAllowFileAccess(true);
            mBinding.webView.getSettings().setDomStorageEnabled(true);
            mBinding.webView.getSettings().setDatabaseEnabled(true);
            mBinding.webView.getSettings().setAppCacheEnabled(true);
            //设置可以访问文件
            mBinding.webView.getSettings().setAllowFileAccess(true);

            mBinding.webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            mBinding.webView.loadUrl(url);

        } else if (TYPE_LOCAL == type) {
            getUIConfig().setTitle(title);
            String htmlData = intent.getStringExtra("htmlData");

//            htmlData = htmlData.replaceAll("&amp;", "");
//            htmlData = htmlData.replaceAll("quot;", "\"");
//            htmlData = htmlData.replaceAll("lt;", "<");
//            htmlData = htmlData.replaceAll("gt;", ">");
//            //设置图片满屏显示
//            htmlData = htmlData.replace("<img", "<img style=\"width:100%;height:auto\"");
            //自适应屏幕
            mBinding.webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            mBinding.webView.getSettings().setLoadWithOverviewMode(true);
            mBinding.webView.loadDataWithBaseURL(null, htmlData, "text/html", "utf-8", null);
        }
    }


    public WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //调用拨号程序
            if (url.startsWith("mailto:") || url.startsWith("geo:") || url.startsWith("tel:") || url.startsWith("smsto:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }


    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mBinding.webView.canGoBack()) {
            mBinding.webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.webView.removeAllViews();
        mBinding.webView.destroy();
    }

    @Override
    protected BaseDataHandler.UIConfig getUIConfig() {
        return mDataHandler.uiConfig.get();
    }


    public class ClickHandler
            extends BaseClickHandler {
        private WebActivity moudleActivity;

        public ClickHandler(WebActivity activity) {
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
