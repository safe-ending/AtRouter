package com.at.arouter.user;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.ViewGroup;

import com.at.arouter.common.base.BaseActivity;
import com.at.arouter.common.data.Constants;
import com.at.arouter.common.listener.BaseClickHandler;
import com.at.arouter.common.listener.BaseDataHandler;
import com.at.arouter.common.ui.WebActivity;
import com.at.arouter.common.util.EcologyUtils;
import com.at.arouter.common.util.ToastUtil;
import com.at.arouter.coremodel.callback.ObserverCallback;
import com.at.arouter.coremodel.APIManager;
import com.at.arouter.coremodel.http.model.RequestResult;
import com.at.arouter.coremodel.service.UserService;
import com.at.arouter.coremodel.viewmodel.CommonViewModel;
import com.at.arouter.user.databinding.ActivityRegistInfoBinding;

import org.parceler.Parcel;
import org.parceler.Parcels;

import io.reactivex.Observable;


/**
 * desc:  注册
 * author:  yangtao
 * <p>
 * creat: 2018/8/17 15:05
 */

public class RegistInfoActivity extends BaseActivity {

    private static final String TAG = RegistInfoActivity.class.getSimpleName();
    private ActivityRegistInfoBinding mBinding;
    private DataHandler mDataHandler;
    private ClickHandler mClickHandler;
    CommonViewModel viewModel;

    @Override
    protected void bindMyView(ViewGroup parent, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.activity_regist_info,
                parent,
                true);
        mBinding.setDataHandler(mDataHandler = DataHandler.create(savedInstanceState));
        mBinding.setClickHandler(mClickHandler = new ClickHandler(this));


        init();
    }

    void init() {

        getUIConfig().setShowTitle(false);
        viewModel = ViewModelProviders.of(RegistInfoActivity.this).get(CommonViewModel.class);
        mBinding.etPhone.getEditext().setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.letter1)));
        mBinding.etCode.getEditext().setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.letter1)));
        mBinding.etExPwd.getEditext().setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.letter1)));
        mBinding.etUserName.getEditext().addTextChangedListener(new TextWatcher() {
            String str;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strs = mBinding.etUserName.getEditext().getText().toString();
                str = EcologyUtils.stringFilter2(strs, Constants.NICKNAME_PACH_ALL);
                if (!strs.equals(str)) {
                    mBinding.etUserName.getEditext().setText(str);
                    mBinding.etUserName.getEditext().setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mBinding.etCode.setOnRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EcologyUtils.checkPhone(mBinding.etPhone.getText())) {
                    showLoadingDialog();
                    sendCode();
                } else {
                    ToastUtil.show(getString(R.string.err_phone));
                }
            }
        });

    }

    //60000代表的是 60秒每隔1秒去更改btnGetCode获取验证码按钮的显示的时间（执行onTick()方法）
    //60秒之后执行onFinish()。
    //+500避免内部计时器通过精确的时间(非整毫秒数)折算的计时时间不准
    private CountDownTimer timer = new CountDownTimer(60000 + 500, 1000) {
        //我们在这里去更改定时改变的东西
        @Override
        public synchronized void onTick(long millisUntilFinished) {
            if (mClickHandler.registInfoActivity != null) {
                mBinding.etCode.getSendCodeView().setClickable(false);
                String str = "s";
                if (millisUntilFinished / 1000 == 0) {
                    onFinish();
                } else {
                    str = millisUntilFinished / 1000 + str;
                    mBinding.etCode.getSendCodeView().setText(str);
                }
            }
        }

        //60秒执行完之后，执行的方法。
        @Override
        public void onFinish() {
            mBinding.etCode.getSendCodeView().setText(getResources().getString(R.string.get_code));
            mBinding.etCode.getSendCodeView().setEnabled(true);
            mBinding.etCode.getSendCodeView().setClickable(true);
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }


    public void sendCode() {
        Observable<RequestResult<String>> observable = APIManager.buildTradeAPI(UserService.class).sendCode(mBinding.etPhone.getText(), Constants.CODE_REGISTER);
        viewModel.request(mActivity,null, observable, new ObserverCallback<String>() {
            @Override
            public void success(String result) {
                dismissDialog();
                ToastUtil.show(getString(R.string.send_code_success));
                timer.start();
            }

            @Override
            public void failure(Throwable e) {
                dismissDialog();
            }
        });
//        APIManager.buildAPI()
//                .sendCode(mBinding.etPhone.getText(), Constants.CODE_REGISTER,
//                        new Callback<RequestResult<?>>() {
//                            @Override
//                            public void success(RequestResult<?> requestResult,
//                                                Response response) {
//                                dismissDialog();
//                                if (APIManager.hasError(mActivity, requestResult)) {
//                                    return;
//                                }
//                                ToastUtil.show(getString(R.string.send_code_success));
//                                timer.start();
//                            }
//
//                            @Override
//                            public void failure(RetrofitError error) {
//                                dismissDialog();
//                                APIManager.showError(mClickHandler.registInfoActivity, error);
//                            }
//                        });
    }


    public void checkCode() {
//        APIManager.buildAPI()
//                .checkCode2(mBinding.etPhone.getText(), Constants.CODE_REGISTER, mBinding.etCode.getText(), mBinding.etUserName.getText(), mBinding.etInvite.getText(),
//                        new Callback<RequestResult<String>>() {
//                            @Override
//                            public void success(RequestResult<String> requestResult,
//                                                Response response) {
//
//                                if (APIManager.hasError(mActivity, requestResult)) {
//                                    dismissDialog();
//                                    mBinding.tvLogin.setEnabled(true);
//                                    return;
//                                }
//                                try {
//                                    generateMnemonic(mClickHandler.registInfoActivity);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    ToastUtil.show(getString(R.string.creat_words_fail));
//                                    dismissDialog();
//                                    mBinding.tvLogin.setEnabled(true);
//                                }
//
//                            }
//
//                            @Override
//                            public void failure(RetrofitError error) {
//                                dismissDialog();
//                                mBinding.tvLogin.setEnabled(true);
//                                APIManager.showError(mClickHandler.registInfoActivity, error);
//                            }
//                        });
    }

    private void getWeb(String type) {
        //SERVICEPROTOCOL("SP", "服务协议"),
        // NOTICECENTOR("NC", "公告中心"),
        // USAGEPROTOCOL("UP", "使用协议"),
        // VIPNOTICE("VP", "VIP公告"),
        // ABOUTUS("AU", "关于我们");

        Intent intent = new Intent();
        intent.setClass(mActivity, WebActivity.class);
        intent.putExtra("id", "");
        intent.putExtra("type", type);
        if ("UP".equals(type)) {
            intent.putExtra("title", "使用协议");
        } else {
            intent.putExtra("title", getResources().getString(R.string.regist_html));
        }
        startActivity(intent);
//        APIManager.buildTradeAPI("")
//                .notice(type,
//                        new Callback<RequestResult<NoticeModel>>() {
//                            @Override
//                            public void success(RequestResult<NoticeModel> requestResult,
//                                                Response response) {
//                                Intent intent = new Intent();
//                                intent.setClass(mActivity, WebNoticeActivity.class);
//                                if (requestResult.result == null) {
//                                    intent.putExtra("id", "");
//                                } else {
//                                    intent.putExtra("id", requestResult.result.id);
//                                }
//                                if ("UP".equals(type)) {
//                                    intent.putExtra("title", "使用协议");
//                                } else {
//                                    intent.putExtra("title", getResources().getString(R.string.regist_html));
//                                }
////                                Intent intent = new Intent();
////                                intent.setClass(mActivity, WebActivity.class);
////                                intent.putExtra("type", 1);
////                                intent.putExtra("htmlData", requestResult.result.content);
////                                if ("SP".equals(type)) {
////                                    intent.putExtra("title", getResources().getString(R.string.regist_html));
////                                }
//                                startActivity(intent);
//                            }
//
//                            @Override
//                            public void failure(RetrofitError error) {
//                                APIManager.showError(mActivity, error);
//                            }
//                        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

//            if (QRScanActivity.REQUEST_CODE == requestCode) {
//                String result = data.getStringExtra("" + Constants.QRCODE);
//                if (!TextUtils.isEmpty(result)) {
//                    if (result.length() <= 8) {
//                        mBinding.etInvite.setText("" + result);
//                    } else {
//                        int dex = result.indexOf("=");
//                        mBinding.etInvite.setText(result.substring(result.indexOf("=") + 1, result.length()));
//                    }
//                }
//
//            }
        }
    }

    @Override
    protected BaseDataHandler.UIConfig getUIConfig() {
        return mDataHandler.uiConfig.get();
    }


    public class ClickHandler
            extends BaseClickHandler {
        private RegistInfoActivity registInfoActivity;

        public ClickHandler(RegistInfoActivity activity) {
            super(activity);
            this.registInfoActivity = activity;
        }

        public void onClickFinish(View view) {
            finish();
        }

        //扫描
        public void onClickScan(View view) {
//            Intent qr = new Intent(registInfoActivity, QRScanActivity.class);
//            startActivityForResult(qr, QRScanActivity.REQUEST_CODE);
        }

        //单选
        public void onClickCheckbox(View view) {
            if (mBinding.cbChoice.isChecked()) {
                mBinding.cbChoice.setChecked(false);
            } else {
                mBinding.cbChoice.setChecked(true);
            }
        }

        //静态链接
        public void onClickShedule(View view) {

            getWeb("SP");
        }

        //提交
        public void onClickCommit(View view) {

            checkInput();
        }

        //跳转登录
        public void onClickToLogin(View view) {
            Intent intent = new Intent(registInfoActivity, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void checkInput() {
//        if (TextUtils.isEmpty(mBinding.etUserName.getText())) {
//            ToastUtil.show(getString(R.string.string_header) + getString(R.string.regist_name_hint));
//            mBinding.tvLogin.setEnabled(true);
//            return;
//        }
//        if (mBinding.etUserName.getText().length() < 6) {
//            ToastUtil.show(getString(R.string.string_header) + getString(R.string.regist_name_hint));
//            mBinding.tvLogin.setEnabled(true);
//            return;
//        }
//
//        if (TextUtils.isEmpty(mBinding.etPhone.getText())) {
//            ToastUtil.show(getString(R.string.string_header) + getString(R.string.regist_phone));
//            mBinding.tvLogin.setEnabled(true);
//            return;
//        }
//
//        if (TextUtils.isEmpty(mBinding.etCode.getText())) {
//            ToastUtil.show(getString(R.string.string_header) + getString(R.string.regist_code));
//            mBinding.tvLogin.setEnabled(true);
//            return;
//        }
//        if (TextUtils.isEmpty(mBinding.etPwd1.getText())) {
//            ToastUtil.show(getString(R.string.regist_login_pwd));
//            mBinding.tvLogin.setEnabled(true);
//            return;
//        }
//        if (!EcologyUtils.checkPwd(mBinding.etPwd1.getText())) {
//            ToastUtil.show(getString(R.string.err_pwd));
//            mBinding.tvLogin.setEnabled(true);
//            return;
//        }
//
//        if (TextUtils.isEmpty(mBinding.etPwd2.getText())) {
//            ToastUtil.show(getString(R.string.regist_login_pwd2));
//            mBinding.tvLogin.setEnabled(true);
//            return;
//        }
//        if (TextUtils.isEmpty(mBinding.etExPwd.getText())) {
//            ToastUtil.show(getString(R.string.regist_ex_pwd));
//            mBinding.tvLogin.setEnabled(true);
//            return;
//        }
//
//        if (!mBinding.cbChoice.isChecked()) {
//            ToastUtil.show(getString(R.string.string_header_1) + getString(R.string.regist_agree) + getString(R.string.regist_html));
//            mBinding.tvLogin.setEnabled(true);
//            return;
//        }
//
//        if (!mBinding.etPwd1.getText().equals(mBinding.etPwd2.getText())) {
//            ToastUtil.show(getString(R.string.login_pwd_not_same));
//            return;
//        }
//        showCommitDialog();
//        mBinding.tvLogin.setEnabled(false);
//        checkCode();

    }

//    //12个助记词
//    ArrayList<String> words = new ArrayList<>();
//
//    public void generateMnemonic(Context context) throws Exception {
//
//        MnemonicCode mnemonicCode = new MnemonicCode(context.getAssets().open("english.txt"), null);
//        SecureRandom secureRandom = SecureRandomUtils.secureRandom();
//        byte[] initialEntropy = new byte[16];//算法需要，必须是被4整除
//        secureRandom.nextBytes(initialEntropy);
//        List<String> wd = mnemonicCode.toMnemonic(initialEntropy);
//
//        if (wd == null || wd.size() != 12) {
//            dismissDialog();
//            mBinding.tvLogin.setEnabled(true);
//            throw new RuntimeException("generate word error");
//        } else {
//            words.clear();
//            words.addAll(wd);
//            StringBuilder save = new StringBuilder();
//            for (String word : words) {
//                save.append(word).append(" ");
//            }
//            //真正的助记词钱包地址创建流程
//            DeterministicSeed deterministicSeed = new DeterministicSeed(save.toString().substring(0, save.toString().length() - 1), null, "", 0);
//            List<String> ls = deterministicSeed.getMnemonicCode();
//            DeterministicKeyChain deterministicKeyChain = DeterministicKeyChain.builder().seed(deterministicSeed).build();
//            //DERIVATION_PATH = "m/44'/60'/0'/0/0"
//            List<ChildNumber> keyPath = HDUtils.parsePath("M/44H/60H/0H/0/0");
//            BigInteger privateKey = deterministicKeyChain.getKeyByPath(keyPath, true).getPrivKey();
//            ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
//
//            //这里只传递参数
//            //助记词确认后再保存本地数据
//            Intent intent = new Intent(mClickHandler.registInfoActivity, BackUpWallet2Activity.class);
//            intent.putStringArrayListExtra(Constants.WORDS, words);
//            Bundle bundle = new Bundle();
//            RegistModel registModel1 = new RegistModel();
//            registModel1.nickName = mBinding.etUserName.getText();
//            registModel1.username = mBinding.etPhone.getText();
//            registModel1.pwd = mBinding.etPwd2.getText();
//            registModel1.payPwd = mBinding.etExPwd.getText();
//            registModel1.inviteCode = mBinding.etInvite.getText();
//            registModel1.code = mBinding.etCode.getText();
//            registModel1.mnemonicWord = save.toString().substring(0, save.toString().length() - 1);
//            bundle.putParcelable(Constants.ACCOUNT, registModel1);
//            intent.putExtras(bundle);
//            startActivity(intent);
//            dismissDialog();
//            mBinding.tvLogin.setEnabled(true);
//        }
//
//
//    }

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
