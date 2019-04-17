package com.at.arouter.third.ui.aty.work_order;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.at.arouter.common.data.ARouterPath;
import com.at.arouter.common.listener.BaseClickHandler;
import com.at.arouter.common.listener.BaseDataHandler;
import com.at.arouter.common.data.Constants;
import com.at.arouter.common.ui.OpenPictureActivity;
import com.at.arouter.common.util.ToastUtil;
import com.at.arouter.common.util.Tools;
import com.at.arouter.common.util.Transformation;
import com.at.arouter.coremodel.callback.ObserverCallback;
import com.at.arouter.coremodel.APIManager;
import com.at.arouter.coremodel.viewmodel.entities.third.ReplyWorkOrderBean;
import com.at.arouter.coremodel.viewmodel.entities.third.UploadWorkFileBean;
import com.at.arouter.coremodel.viewmodel.entities.third.WorkOrderListBean;
import com.at.arouter.coremodel.http.model.RequestResult;
import com.at.arouter.coremodel.service.ThirdService;
import com.at.arouter.coremodel.util.TAGUtils;
import com.at.arouter.coremodel.viewmodel.CommonViewModel;
import com.at.arouter.third.R;
import com.at.arouter.third.bridge.SelectPictureDialog;
import com.at.arouter.third.databinding.AtyCreateWorkOrderBinding;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * desc:  创建工单、回复工单
 * author:  yangtao
 * <p>
 * creat: 2018/8/30 16:05
 */

@Route(path = ARouterPath.CreatWorkAty)
public class CreateWorkOrderActivity extends OpenPictureActivity implements View.OnClickListener {

    private String mOrderCode, mFileUrl, mFileType, mFileLocal;
    private int mAddressType, mAdminID;
    private AtyCreateWorkOrderBinding mBinding;
    private DataHandler mDataHandler;
    private ClickHandler mClickHandler;

    @Override
    protected void bindMyView(ViewGroup parent, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.aty_create_work_order,
                parent,
                true);
        mBinding.setDataHandler(mDataHandler = DataHandler.create(savedInstanceState));
        mBinding.setClickHandler(mClickHandler = new ClickHandler(this));
        init();
    }

    public void init() {
        //        if (bundle == null) return;
        mOrderCode = getIntent().getStringExtra("code");
        mAdminID = getIntent().getIntExtra("id", -1);
        if (!TextUtils.isEmpty(mOrderCode)) {
            getUIConfig().setTitle(getString(R.string.a809));
            mBinding.rlPicture.setVisibility(View.GONE);
            mBinding.llContact.setVisibility(View.GONE);
        } else getUIConfig().setTitle(getString(R.string.a917));
        createModelView = ViewModelProviders.of(CreateWorkOrderActivity.this).get(CommonViewModel.class);

        initClick();
    }
    CommonViewModel createModelView;

    @Override
    protected BaseDataHandler.UIConfig getUIConfig() {
        return mDataHandler.uiConfig.get();
    }


    public class ClickHandler
            extends BaseClickHandler {
        private CreateWorkOrderActivity createWorkOrderActivity;

        public ClickHandler(CreateWorkOrderActivity activity) {
            super(activity);
            this.createWorkOrderActivity = activity;
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
        if (bitmap == null) {
            picturePath = null;
        } else {
            mFileUrl = null;
            mFileType = null;
            mFileLocal = null;
            toggleDisplay();
        }
        mCount = 0;
    }

    private void toggleDisplay() {
        if (mBinding.ivDelete.getVisibility() == View.GONE)
            mBinding.ivDelete.setVisibility(View.VISIBLE);

        mBinding.ivPicture.setImageBitmap(bitmap);
        mBinding.ivPicture.setMaxHeight(350);
        mBinding.ivPicture.setScaleType(ImageView.ScaleType.FIT_CENTER);
        // ivPicture.setVisibility(View.VISIBLE);
        // ivPicture.setVisibility(ImageView.VISIBLE);
    }

    void initClick() {
        mBinding.ivPicture.setOnClickListener(this);
        mBinding.rlPicture.setOnClickListener(this);
        mBinding.ivDelete.setOnClickListener(this);
        mBinding.tvSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_picture || i == R.id.rl_picture) {
            new SelectPictureDialog(this).show();

        } else if (i == R.id.iv_delete) {
            if (bitmap != null) {
                if (mBinding.ivDelete.getVisibility() == View.VISIBLE)
                    mBinding.ivDelete.setVisibility(View.GONE);
                mBinding.ivPicture.setImageResource(R.mipmap.camera);
                mBinding.ivPicture.setScaleType(ImageView.ScaleType.CENTER);
                Transformation.recycleBitmap(bitmap);
                bitmap = null;
            }

        } else if (i == R.id.tv_submit) {
            if (TextUtils.isEmpty(mBinding.etQuestion.getText().toString().trim())) {
                ToastUtil.show(getString(R.string.question_content));
            } else if (!TextUtils.isEmpty(mOrderCode)) {
                replyMsg();
            } else if (!TextUtils.isEmpty(mBinding.etContact.getText().toString().trim())) {
                mAddressType = Tools.verificationAddress(mBinding.etContact.getText().toString().trim());
                if (mAddressType == -1) {
                    ToastUtil.show(getString(R.string.commit_address));
                } else if (mAddressType == -2) {
                    ToastUtil.show(getString(R.string.commit_phone));
                } else submitData();
            } else submitData();

        }
    }

    private void submitData() {
        if (bitmap != null && TextUtils.isEmpty(mFileUrl)) {
            uploadPicture();
        } else submitWorkOrder(true);
    }


    private void replyMsg() {


        showLoadingDialog();
        ReplyWorkOrderBean bean = new ReplyWorkOrderBean();
        bean.setAdmin(false);
//        bean.setAuthor((String) SPUtils.get(this, ID, ""));
        bean.setDetail(mBinding.etQuestion.getText().toString().trim());
        if (mAdminID != -1) {
            ReplyWorkOrderBean.PreviousBean id = new ReplyWorkOrderBean.PreviousBean();
            id.setId(mAdminID);
            bean.setPrevious(id);
        }
        Observable<RequestResult<WorkOrderListBean>> observable = APIManager.buildTradeAPI(ThirdService.class).postOrderReply(mOrderCode, bean);

        createModelView.request(mActivity, null,observable, new ObserverCallback<WorkOrderListBean>() {
            @Override
            public void success(WorkOrderListBean workOrderListBean) {
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

    private void uploadPicture() {
        showLoadingDialog();


        //上传用到
        File file = new File(picturePath);
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Observable<RequestResult<UploadWorkFileBean>> observable = APIManager.buildTradeAPI(ThirdService.class).postOrderPic(body);

        createModelView.request(CreateWorkOrderActivity.this, null,observable, new ObserverCallback<UploadWorkFileBean>() {

            @Override
            public void success(UploadWorkFileBean uploadWorkFileBean) {
                Log.i(TAGUtils.LOG_TAG, "subscribeToModel onChanged onChanged");
                UploadWorkFileBean bean = uploadWorkFileBean;
                createModelView.setUiObservableData(bean);
                mFileUrl = bean.getUrl();
                mFileType = bean.getType();
                mFileLocal = bean.getLocal();
                submitWorkOrder(false);

            }

            @Override
            public void failure(Throwable e) {
                dismissDialog();
            }


        });

    }

    private void submitWorkOrder(boolean flag) {
        if (flag) showLoadingDialog();
        Observable<RequestResult<Object>> observable = APIManager.buildTradeAPI(ThirdService.class)
                .postOrder(
                        walletPref.getWallet(),
                        mBinding.etQuestion.getText().toString().trim(),
                        mAddressType == 1 ? mBinding.etContact.getText().toString().trim() : "",
                        mAddressType == 2 ? mBinding.etContact.getText().toString().trim() : "",
                        !TextUtils.isEmpty(mFileUrl) ? mFileUrl : "",
                        !TextUtils.isEmpty(mFileType) ? mFileType : "",
                        !TextUtils.isEmpty(mFileLocal) ? mFileLocal : "");

        createModelView.request(CreateWorkOrderActivity.this,null,observable, new ObserverCallback<Object>() {
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

}
