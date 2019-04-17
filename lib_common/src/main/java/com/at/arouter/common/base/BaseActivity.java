package com.at.arouter.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.at.arouter.common.R;
import com.at.arouter.common.callback.DataCallBack;
import com.at.arouter.common.callback.LoadingCallback;
import com.at.arouter.common.data.AppPref;
import com.at.arouter.common.data.Constants;
import com.at.arouter.common.databinding.ActivityBaseBinding;
import com.at.arouter.common.listener.BaseDataHandler;
import com.at.arouter.common.listener.SimpleTextWatcher;
import com.at.arouter.common.util.AndroidUtil;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Locale;


/**
 * <p>Activity基类 </p>
 *
 * @name BaseActivity
 */
public abstract class BaseActivity extends BaseCompatActivity {

    private static final String TAG = BaseActivity.class.getName();
    private ActivityBaseBinding mBinding;
    protected AppPref walletPref;//钱包的本地存储
    protected AppPref commonPref;//语言等其他本地保存
    protected Activity mActivity;

    /**
     * 1.获取从外部传过来的数据
     * 2.生成本类所需的数据对象dataHandler
     * 3.onCreate之后统一使用dataHandler
     */
    protected abstract void bindMyView(ViewGroup parent, Bundle savedInstanceState);

    protected abstract BaseDataHandler.UIConfig getUIConfig();

    protected void onClickLeft() {
        finish();
    }

    protected void onClickRightButton1() {
    }

    protected void onClickRightButton2() {
    }

    protected void onClickRightButton3() {
    }

    protected void onClickRightButton4() {
    }

    protected void onClickTitle() {
    }

    protected void onClickRightTitle1() {
    }

    /**
     * 输入文本后
     */
    protected void onEditTextafterTextChanged(Editable s) {
    }

    /**
     * 小键盘回车事件
     */
    protected void onEditTextKeyboardSearch() {
    }

    /**
     * 清除图片监听事件
     */
    protected void OnClickClearSearchListener() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        mActivity = this;

        setupData();
        initEvent();
        bindMyView(mBinding.flContentLayout, savedInstanceState);
        mBinding.setConfig(getUIConfig());
    }

    /**
     * 重写 getResource 方法，防止系统字体影响
     */
    @Override
    public Resources getResources() {//禁止app字体大小跟随系统字体大小调节
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
            Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    private void initEvent() {
        //清除图片监听事件
        mBinding.icActivityHeaderView.ivClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.icActivityHeaderView.etSearch.setText("");

                OnClickClearSearchListener();
            }
        });


        //EditText的监听事件
        mBinding.icActivityHeaderView.etSearch.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString()
                        .trim();

                boolean emptier = TextUtils.isEmpty(content);
                //隐藏还是显示 清除图片
                mBinding.icActivityHeaderView.ivClearSearch.setVisibility(emptier
                        ? View.GONE
                        : View.VISIBLE);

                onEditTextafterTextChanged(s);
            }
        });

        //EditText的小键盘回车事件
        mBinding.icActivityHeaderView.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    onEditTextKeyboardSearch();
                    return true;
                }
                return false;
            }
        });
        EventBus.getDefault().register(this);
        changeAppLanguage();

    }

    public LoadService loadService;

    public void setLoadSuccessView(View view, DataCallBack callBack) {
        loadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                loadService.showCallback(LoadingCallback.class);
                // 重新加载逻辑
                callBack.reload();
            }
        });
        loadService.showCallback(LoadingCallback.class);
    }


//    public void bindLoadSir(View view, RequestResult httpResult) {
//        LoadService loadService = LoadUtils.getDefault().register(view, new Callback.OnReloadListener() {
//            @Override
//            public void onReload(View v) {
//                // 重新加载逻辑
//            }
//        }, new Convertor<RequestResult>() {
//            @Override
//            public Class<? extends Callback> map(RequestResult httpResult) {
//                Class<? extends Callback> resultCode = SuccessCallback.class;
//                switch (httpResult.code()) {
//                    case RequestResult.CODE_REQUEST_SUCCESS://成功回调
//                        if (new Gson().fromJson(httpResult.result.toString(), ArrayList.class).size() > 0) {
//                            resultCode = SuccessCallback.class;
//                        } else {
//                            resultCode = EmptyCallback.class;
//                        }
//                        break;
//                    default:
//                        resultCode = ErrorCallback.class;
//                        break;
//                }
//                return resultCode;
//            }
//
//        });
//        loadService.showWithConvertor(httpResult);
//    }


    public void changeAppLanguage() {
        String sta = TextUtils.isEmpty(commonPref.getLanguage()) || commonPref.getLanguage().equals(Constants.TYPE_CN) ? "zh" : "en";//这是SharedPreferences工具类，用于保存设置，代码很简单，自己实现吧
        // 本地语言设置
        Locale myLocale;
        switch (sta) {
            case Constants.TYPE_CN:
                myLocale = Locale.SIMPLIFIED_CHINESE;
                break;
            case Constants.TYPE_EN:
                myLocale = Locale.ENGLISH;
                break;

            default:
                myLocale = Locale.SIMPLIFIED_CHINESE;
                break;
        }

        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    @Subscribe//切换语言不可少
    public void onEvent(String str) {
        switch (str) {
            case Constants.EVENT_REFRESH_LANGUAGE:
                changeAppLanguage();
                recreate();//刷新界面
                break;

            case Constants.EVENT_REFRESH_WALLET:
                setupData();
                recreate();//刷新界面
                break;
        }
    }

    private void setupData() {
        walletPref = new AppPref(this, "");
        commonPref = new AppPref(this, AppPref.APP_PREF);
    }

    /**
     * 设置EditText文本
     *
     * @param content
     */
    public void setEditTextContent(String content) {
        mBinding.icActivityHeaderView.etSearch.setText(content);
        mBinding.icActivityHeaderView.etSearch.setSelection(mBinding.icActivityHeaderView.etSearch.getText()
                .length());
    }

    //软键盘的隐藏或者显示   当前隐藏就显示   反之
    public void setInputVisibility() {
        InputMethodManager inputManager = (InputMethodManager) mBinding.icActivityHeaderView.etSearch.getContext()
                .getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        //                    inputManager.showSoftInput(mBinding.icActivityHeaderView.etTitle, 0);
        inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public View getActionBarView() {
        return mBinding.icActivityHeaderView.rlLayoutTopTitle;
    }

    public void setTitleRightImgRes(int resource) {
        mBinding.icActivityHeaderView.ivTitleRightImg.setImageResource(resource);
    }

    public void setBtnLeft(int resource) {
        mBinding.icActivityHeaderView.ivBtnLeft.setImageResource(resource);
    }

    public void setBtnRight1Res(int resource) {
        mBinding.icActivityHeaderView.ivBtnRight1.setImageResource(resource);
    }

    public void setBtnRight2Res(int resource) {
        mBinding.icActivityHeaderView.ivBtnRight2.setImageResource(resource);
    }

    public void setBtnRight3(String title) {
        setBtnRight3(title, 0);
    }

    public String getBtnRight3() {
        return mBinding.icActivityHeaderView.tvBtnRight3.getText().toString();
    }

    public void setBtnRight3(String title, int textColor) {
        setBtnRight3(title, textColor, 0);
    }

    public void setBtnRight3(String title, int textColor, int resource) {
        mBinding.icActivityHeaderView.tvBtnRight3.setText(title);

        if (textColor > 0) {
            int color = getResources().getColor(textColor);
            mBinding.icActivityHeaderView.tvBtnRight3.setTextColor(color);
        }

        if (resource > 0) {
            Drawable drawable = getResources().getDrawable(resource);
            mBinding.icActivityHeaderView.tvBtnRight3.setCompoundDrawablesWithIntrinsicBounds(null,
                    null,
                    drawable,
                    null);
            mBinding.icActivityHeaderView.tvBtnRight3.setCompoundDrawablePadding((int) AndroidUtil.dp2px(
                    BaseActivity.this,
                    2));
        }
    }

    public void setBtnRight4(String title) {
        setBtnRight4(title, 0);
    }

    public void setBtnRight4(String title, int textColor) {
        setBtnRight4(title, textColor, 0);
    }

    public void setBtnRight4(String title, int textColor, int resource) {
        mBinding.icActivityHeaderView.tvBtnRight4.setText(title);

        if (textColor > 0) {
            int color = getResources().getColor(textColor);
            mBinding.icActivityHeaderView.tvBtnRight4.setTextColor(color);
        }

        if (resource > 0) {
            Drawable drawable = getResources().getDrawable(resource);
            mBinding.icActivityHeaderView.tvBtnRight4.setCompoundDrawablesWithIntrinsicBounds(null,
                    null,
                    drawable,
                    null);
            mBinding.icActivityHeaderView.tvBtnRight4.setCompoundDrawablePadding((int) AndroidUtil.dp2px(
                    BaseActivity.this,
                    2));
        }
    }

    public void setTitleRightDrawable(String title, int resource) {
        if (resource > 0) {
            Drawable drawable = getResources().getDrawable(resource);
            mBinding.icActivityHeaderView.tvTitle.setCompoundDrawablesWithIntrinsicBounds(null,
                    null,
                    drawable,
                    null);
            mBinding.icActivityHeaderView.tvTitle.setCompoundDrawablePadding((int) AndroidUtil.dp2px(
                    BaseActivity.this,
                    2));
        }
        if (!TextUtils.isEmpty(title)) {
            getUIConfig().setTitle(title);
        }
    }

    protected TextView getTvTitle() {
        return mBinding.icActivityHeaderView.tvTitle;
    }

    protected View getTitleView() {
        return mBinding.icActivityHeaderView.rlLayoutTopTitle;
    }


    //必不可少，否则所有的组件都不会有TouchEvent了
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN || ev.getAction() == MotionEvent.ACTION_MOVE || ev.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
            View v = getCurrentFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (isShouldHideInput(v, ev) && imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return super.dispatchTouchEvent(ev);
        }

        return super.dispatchTouchEvent(ev);
        //            return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public View getEmptyView() {
        return mBinding.rlNoDataLayout;
    }

    /**
     * 通过点击区域，判断是否隐藏EditText
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0,
                    0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                //点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回键事件
     */
    public void onClickLeftButton(View view) {
        onClickLeft();
    }

    public void onClickRightButton1(View view) {
        onClickRightButton1();
    }

    public void onClickRightButton2(View view) {
        onClickRightButton2();
    }

    public void onClickRightButton3(View view) {
        onClickRightButton3();
    }

    public void onClickRightButton4(View view) {
        onClickRightButton4();
    }

    public void onClickRightTitle1(View view) {
        onClickRightTitle1();
    }

    public void onClickTitle(View view) {
        onClickTitle();
    }


    public boolean mIsForeground;

    @Override
    protected void onResume() {
        super.onResume();
        mIsForeground = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsForeground = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearData();
        EventBus.getDefault().unregister(this);
    }

    protected void showLoad() {
    }

    protected void showSuccess() {
    }

    protected void showError() {
    }

    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivityForResult(intent, requestCode);
    }

    protected void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    protected void clearData() {
        dismissDialog();
    }

    public LoadingDailog loadingDailog;

    public void showLoadingDialog() {
        showLoadingDialog(getString(R.string.loading));
    }

    public void showCommitDialog() {
        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                .setMessage("正在努力提交中")
                .setCancelable(true)
                .setCancelOutside(false);
        loadingDailog = loadBuilder.create();
        loadingDailog.show();
    }

    public void showLoadingDialog(String msg) {
        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(this)
                .setMessage(msg)
                .setCancelable(true)
                .setCancelOutside(false);
        loadingDailog = loadBuilder.create();
        loadingDailog.show();
    }

    public void dismissDialog() {
        if (loadingDailog != null && loadingDailog.isShowing()) loadingDailog.dismiss();
    }

}
