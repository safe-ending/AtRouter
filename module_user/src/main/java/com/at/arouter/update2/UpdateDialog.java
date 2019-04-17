package com.at.arouter.update2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.at.arouter.common.util.DownloadUtil;
import com.at.arouter.common.util.FileProvider7;
import com.at.arouter.common.util.FileUtils;
import com.at.arouter.common.util.StringUtils;
import com.at.arouter.common.util.ToastUtil;
import com.at.arouter.user.R;

import java.io.File;

public class UpdateDialog
        extends Dialog {
    private Context mContext;
    private View mView;
    private TextView mTvUpdateTitle;
    private TextView mTvCurrentVersion;
    private TextView mTvUpdateVersion;
    private TextView mTvUpdateDesc;
    private ProgressBar mTvUpdateProgress;
    private TextView mTvConfirm;
    private TextView mTvCancel;

    //软件下载地址
    private String mDownloadUrl;
    //标题
    private String mTitle;
    //当前版本名
    private String mCurrentVersionName;
    //更新版本名
    private String mUpateVersionName;
    //更新日志
    private String mAppDesc;
    //文件存储路径
    private String mFilePath;
    //自定义的文件名
    private String mFileName;
    //时间间隔
    private long mTimeRange;
    private boolean force;

    public UpdateDialog(Context context, boolean force) {
        super(context, R.style.Dialog_Theme_Transparent);
        this.mContext = context;
        setDialogTheme();
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        this.force = force;
    }

    /**
     * set dialog theme(设置对话框主题)
     */
    private void setDialogTheme() {
        getWindow().setBackgroundDrawable(mContext.getResources()
                .getDrawable(R.drawable.shape_corner10_white));
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (((Activity) mContext).getWindowManager()
                .getDefaultDisplay()
                .getWidth() * 0.7);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = LayoutInflater.from(mContext)
                .inflate(R.layout.view_update_dialog, null);
        setContentView(mView);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        //更新
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });

        //不更新
        if (force){
            mTvCancel.setVisibility(View.GONE);
        }
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 开始下载
     */
    private void download() {

        //防抖动,两次点击间隔小于500ms都return;
        if (System.currentTimeMillis() - mTimeRange < 500) {
            return;
        }

        //文件存储目录
        String storage = FileUtils.AccessoryStorage + FileUtils.Apk;

        //如果不存在下载的路径就创建
        FileUtils.isFileDirExist(storage);

        if (mContext.getString(R.string.install_t1).equals(mTvConfirm.getText()
                .toString()
                .trim())) {
            File file = new File(mFilePath);
            if (file.exists()) {
                FileProvider7.installApk(mContext, file);
            } else {
                ToastUtil.show(mContext.getString(R.string.install_t2));
                mTvConfirm.setText(R.string.install_t3);
                mTvConfirm.setEnabled(true);
//                download();
            }
            return;
        }
        mTvUpdateProgress.setVisibility(View.VISIBLE);


        DownloadUtil.getInstance()
                .download(mDownloadUrl, storage, false, new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(String path) {
                        //下载成功
                        mTvConfirm.setEnabled(true);
                        mTvConfirm.setText(R.string.install_t1);
                        setFilePath(path);
                        FileProvider7.installApk(mContext, new File(path));
                    }

                    @Override
                    public void onDownloading(int progress) {
                        //下载进度
                        mTvConfirm.setEnabled(false);
                        mTvConfirm.setText(R.string.install_t5);
                        mTvUpdateProgress.setProgress(progress);
                        Log.i("onDownloading", " " + progress);
                        //mTvUpdateProgress.setMax((int) (totalProgress));
                    }

                    @Override
                    public void onDownloadFailed() {
                        mTvConfirm.setEnabled(true);
                        mTvConfirm.setText(R.string.install_t6);
                    }
                });
    }

    private void initData() {
        mTvConfirm.setText(R.string.update);
        mTvCancel.setText(R.string.no);

        if (!StringUtils.isEmpty(mTitle)) {
            mTvUpdateTitle.setText(mTitle + "");
        }
        if (!StringUtils.isEmpty(mCurrentVersionName)) {
            mTvCurrentVersion.setText(mCurrentVersionName);
        }
        if (!StringUtils.isEmpty(mUpateVersionName)) {
            mTvUpdateVersion.setText(mUpateVersionName);
        }
        if (!StringUtils.isEmpty(mAppDesc)) {
            try {
                //以","进行换行   1.新增界面,2.优化,3.修复bug
                String[] contents = mAppDesc.split(",");

                String newContent = "";

                for (int i = 0; i < contents.length; i++) {
                    newContent += contents[i] + "\n";
                }

                mTvUpdateDesc.setText(newContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initView() {
        //标题
        mTvUpdateTitle = mView.findViewById(R.id.tvUpdateTitle);
        //当前的版本
        mTvCurrentVersion = mView.findViewById(R.id.tvCurrentVersion);
        //最新的版本
        mTvUpdateVersion = mView.findViewById(R.id.tvUpdateVersion);
        //更新内容
        mTvUpdateDesc = mView.findViewById(R.id.tvUpdateDesc);
        //安装进度
        mTvUpdateProgress = mView.findViewById(R.id.tvUpdateProgress);
        //确认  安装
        mTvConfirm = mView.findViewById(R.id.tvConfirm);
        //取消
        mTvCancel = mView.findViewById(R.id.tvCancel);
    }


    /**
     * 设置文件下载地址
     */
    public UpdateDialog setDownloadUrl(String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
        return this;
    }

    /**
     * 设置dialog显示标题
     */
    public UpdateDialog setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    /**
     * 设置当前版本名,如2.2.1
     */
    public UpdateDialog setCurrentVersionName(String currentVersionName) {
        this.mCurrentVersionName = currentVersionName;
        return this;
    }

    /**
     * 设置更新版本名,如2.2.1
     */
    public UpdateDialog setUpdateVersionName(String updateVersionName) {
        this.mUpateVersionName = updateVersionName;
        return this;
    }

    /**
     * 设置更新日志,需要自己分好段落
     */
    public UpdateDialog setUpdateDesc(String updateDesc) {
        this.mAppDesc = updateDesc;
        return this;
    }

    /**
     * 设置文件存储路径
     */
    public UpdateDialog setFilePath(String filePath) {
        this.mFilePath = filePath;
        return this;
    }

    /**
     * 设置下载文件名
     */
    public UpdateDialog setFileName(String fileName) {
        this.mFileName = fileName;
        return this;
    }
}
