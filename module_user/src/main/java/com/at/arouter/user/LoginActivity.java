package com.at.arouter.user;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.at.arouter.common.base.BaseActivity;
import com.at.arouter.common.data.APIHostManager;
import com.at.arouter.common.data.ARouterPath;
import com.at.arouter.common.data.AppPref;
import com.at.arouter.common.data.Constants;
import com.at.arouter.common.listener.BaseClickHandler;
import com.at.arouter.common.listener.BaseDataHandler;
import com.at.arouter.common.util.ToastUtil;
import com.at.arouter.coremodel.http.callback.ObserverCallback;
import com.at.arouter.coremodel.APIManager;
import com.at.arouter.coremodel.viewmodel.entities.user.UserModel;
import com.at.arouter.coremodel.http.model.RequestResult;
import com.at.arouter.coremodel.UserDao;
import com.at.arouter.coremodel.service.UserService;
import com.at.arouter.coremodel.viewmodel.CommonViewModel;
import com.at.arouter.user.databinding.ActivityLoginBinding;

import org.parceler.Parcel;
import org.parceler.Parcels;

import io.reactivex.Observable;


/**
 * desc:  登录
 * author:  yangtao
 * <p>
 * creat: 2019/1/19 15:05
 */

@Route(path = ARouterPath.LoginAty)
public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private ActivityLoginBinding mBinding;
    private DataHandler mDataHandler;
    private ClickHandler mClickHandler;
    private AppPref appPref;

    @Autowired(name = "username")
    public String name;

    @Override
    protected void bindMyView(ViewGroup parent, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.activity_login,
                parent,
                true);
        mBinding.setDataHandler(mDataHandler = DataHandler.create(savedInstanceState));
        mBinding.setClickHandler(mClickHandler = new ClickHandler(this));


        init();
    }

    void init() {
        appPref = new AppPref(mActivity, AppPref.APP_PREF);
        getUIConfig().setShowTitle(false);
        //注册后填入
        if (!TextUtils.isEmpty(getIntent().getStringExtra(Constants.PARA_PHONE))) {
            mBinding.tvUserName.setText(getIntent().getStringExtra(Constants.PARA_PHONE));
        } else {
            try {
                mBinding.tvUserName.setText(appPref.getUsername());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //inject需要注入后才可以读取到携带过来的参数
        ARouter.getInstance().inject(this);
        mBinding.tvUserName.setText(name);
        if ((getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            mBinding.tvChange.setVisibility(View.VISIBLE);
            mBinding.tvChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    new WebHostDialog(mActivity).show();
                }
            });
        } else {
            mBinding.tvChange.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Constants.FORCE_LOGIN.equals(intent.getStringExtra(Constants.SECURITY_CODE))) {
            mBinding.tvUserName.setText(intent.getStringExtra(Constants.PARA_PHONE));
            mBinding.tvPwd.setText("");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected BaseDataHandler.UIConfig getUIConfig() {
        return mDataHandler.uiConfig.get();
    }


    public class ClickHandler
            extends BaseClickHandler {
        private LoginActivity loginActivity;

        public ClickHandler(LoginActivity activity) {
            super(activity);
            this.loginActivity = activity;
        }

        public void onClickFinish(View view) {
            finish();
        }

        public void onClickLogin(View view) {
            if (TextUtils.isEmpty(mBinding.tvUserName.getText())) {
                ToastUtil.show(getResources().getString(R.string.login_account));
                return;
            }

            if (TextUtils.isEmpty(mBinding.tvPwd.getText())) {
                ToastUtil.show(getResources().getString(R.string.login_pwd));
                return;
            }
            showLoadingDialog(getString(R.string.login_ing));
            login();
        }

        public void onClickForget(View view) {
//            Intent intent = new Intent(loginActivity, ForgetPwdActivity.class);
//            intent.putExtra(Constants.SECURITY_CODE, Constants.FORGET_LOGIN_PWD);
//            startActivity(intent);
        }

        public void onClickRegist(View view) {
            Intent intent = new Intent(loginActivity, RegistInfoActivity.class);
            startActivity(intent);
        }
    }

    public void login() {

        Observable<RequestResult<UserModel>> observer = APIManager.buildAPI(APIHostManager.Common_Url,UserService.class).login(mBinding.tvUserName.getText(), mBinding.tvPwd.getText());
        CommonViewModel viewModel = ViewModelProviders.of(LoginActivity.this).get(CommonViewModel.class);
        viewModel.request(this,null, observer, new ObserverCallback<UserModel>() {

            @Override
            public void success(UserModel userModel) {
                dismissDialog();
                UserDao.getInstance(mClickHandler.loginActivity).save(userModel);
                appPref.setUsername(UserDao.getInstance(mActivity).getUser().getPhone());
                ToastUtil.show("登录成功");
                finish();
//                User.isLogin = UserDao.getInstance(mActivity).isLogin();
//                User.bean = UserDao.getInstance(mActivity).getUserData();
//                User.token = UserDao.getInstance(mActivity).getUser().token;
//                if (TextUtils.isEmpty(getIntent().getStringExtra(Constants.STATE_TYPE))) {
//                    startActivity(MainActivity.class);
//                    finish();
//                } else {
//                    finish();
//                }
            }

            @Override
            public void failure(Throwable e) {
                dismissDialog();
            }
        });

    }

    public void reboot() {
        Intent intent = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        Process.killProcess(Process.myPid());
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
