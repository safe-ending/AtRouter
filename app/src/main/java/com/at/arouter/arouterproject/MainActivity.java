package com.at.arouter.arouterproject;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.at.arouter.arouterproject.view.WebHostDialog;
import com.at.arouter.common.base.BaseCompatActivity;
import com.at.arouter.common.data.APIHostManager;
import com.at.arouter.common.data.ARouterPath;
import com.at.arouter.common.data.AppPref;
import com.at.arouter.common.util.ATToastUtils;
import com.at.arouter.common.util.MultiLanguageUtil;
import com.at.arouter.common.util.ToastUtil;
import com.at.arouter.coremodel.callback.ObserverCallback;
import com.at.arouter.coremodel.APIManager;
import com.at.arouter.coremodel.http.model.RequestResult;
import com.at.arouter.coremodel.UserDao;
import com.at.arouter.coremodel.service.UserService;
import com.at.arouter.coremodel.viewmodel.CommonViewModel;
import com.at.arouter.update2.UpdateManager;

import io.reactivex.Observable;

/**
 * 应用首页
 */
public class MainActivity extends BaseCompatActivity {

    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        appPref = new AppPref(this, AppPref.APP_PREF);
    }

    AppPref appPref;

    public void onClick(View view) {
        switch (view.getId()) {
            //组件化
            case R.id.btLogin:
                ARouter.getInstance()
                        .build(ARouterPath.LoginAty)
                        .withString("username", "13266753946")
                        .withTransition(R.anim.activity_up_in, R.anim.activity_up_out)
                        .navigation(MainActivity.this);
                break;

            case R.id.btLoginOut:

                CommonViewModel viewModel = ViewModelProviders.of(MainActivity.this).get(CommonViewModel.class);
                Observable<RequestResult<String>> observer = APIManager.buildAPI(APIHostManager.Common_Url, UserService.class).loginOut();
                viewModel.request(mActivity,null, observer, new ObserverCallback<String>() {

                    @Override
                    public void success(String s) {
                        ToastUtil.show("退出成功");
                        UserDao.getInstance(mActivity).loginOut();
                    }

                    @Override
                    public void failure(Throwable e) {

                    }
                });
                break;


            case R.id.btWork:
                if (UserDao.getInstance(mActivity).isLogin()) {
                    ARouter.getInstance().build(ARouterPath.NoticeListAty).navigation(MainActivity.this);
                } else {
                    Utils.toLogin(MainActivity.this);
                }
                break;
            case R.id.btImage:
                ARouter.getInstance().build(ARouterPath.KchartAty).navigation(MainActivity.this);
                break;

            case R.id.btLan:
                //目前测试俩个module首页的标题   可实现语言切换  对应资源放在各自组建下即可、
                if (appPref.getLanguage().equals(MultiLanguageUtil.TYPE_CN)) {
                    ToastUtil.show("当前语言为中文，切换后为英文");
                    appPref.setLanguage("" + MultiLanguageUtil.TYPE_EN);
                } else {
                    appPref.setLanguage("" + MultiLanguageUtil.TYPE_CN);
                    ToastUtil.show("当前语言为英文，切换后为中文");
                }
                MultiLanguageUtil.autoUpdateLanguageEnviroment(this);

                recreate();
                break;

            case R.id.toast1:
                ATToastUtils.checkNull(this, "姓名");
                break;

            case R.id.toast2:
                ATToastUtils.checkNull(this, "兑换数量", "0.021", "100");
                break;

            case R.id.toast3:
                ATToastUtils.checkFormat(this, "用户名");
                break;

            case R.id.toast4:
                ATToastUtils.checkFormat(this, "挂单价", "0.11", "10");
                break;

            case R.id.toast5:
                ATToastUtils.success(this, "保存");
                break;

            case R.id.toast6:
                ATToastUtils.failure(this, "删除");
                break;

            case R.id.toast7:
                ATToastUtils.checkPwd(this, ATToastUtils.LoginPwdMarkedType, ATToastUtils.FormatPwdErrorType);
                break;

            case R.id.toast8:
                ATToastUtils.waitBuild(this);
                break;

            case R.id.tvChange:
                new WebHostDialog(MainActivity.this).show();
                break;

            case R.id.tvUpdate:
                UpdateManager.getInstance()
                        .checkForUpdate(mActivity, new UpdateManager.UpdateCallback() {
                            @Override
                            public void noUpdate() {
                                ATToastUtils.show("您已是最新版本");
                            }
                        });
                break;
        }
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
}
