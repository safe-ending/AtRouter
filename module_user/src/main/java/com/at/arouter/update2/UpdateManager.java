package com.at.arouter.update2;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;

import com.at.arouter.common.data.APIHostManager;
import com.at.arouter.common.util.EcologyUtils;
import com.at.arouter.common.util.FileUtils;
import com.at.arouter.coremodel.APIManager;
import com.at.arouter.coremodel.http.callback.ObserverCallback;
import com.at.arouter.coremodel.http.model.RequestResult;
import com.at.arouter.coremodel.service.OtherService;
import com.at.arouter.coremodel.viewmodel.CommonViewModel;
import com.at.arouter.coremodel.viewmodel.entities.other.UpgradeInfo;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;


public class UpdateManager {
    private static UpdateManager mInstance = new UpdateManager();
    private UpgradeInfo mUpdateInfoModel;

    public static UpdateManager getInstance() {
        return mInstance;
    }

    private UpdateManager() {
    }

    public void checkForUpdate(Context context) {
        checkForUpdate(context, null);
    }

    public void checkForUpdate(final Context context, final UpdateCallback callback) {
        Observable<RequestResult<UpgradeInfo>> observable = APIManager.buildAPI(APIHostManager.Common_Url, OtherService.class).updateVersion("BJB", 1);
        CommonViewModel viewModel = ViewModelProviders.of((FragmentActivity) context).get(CommonViewModel.class);
        viewModel.request(context, null, observable, new ObserverCallback<UpgradeInfo>() {
            @Override
            public void success(UpgradeInfo mUpdateInfoModel) {
                String minVersion = mUpdateInfoModel.lowestVersion;
                String lastVersion = mUpdateInfoModel.newestVersion;
                String currentVersion = EcologyUtils.getAppVersionName();

                //用.将字符串分开，得到字符串数组
                String[] min = minVersion.split("\\.");
                String[] last = lastVersion.split("\\.");
                String[] current = currentVersion.split("\\.");

                //将字符串数组转换成集合list
                List<String> minList = Arrays.asList(min);
                List<String> lastList = Arrays.asList(last);
                List<String> currentList = Arrays.asList(current);

                //查看集合
                if ((Double.valueOf(currentList.get(0)).intValue() < Double.valueOf(minList.get(0)).intValue()) ||
                        (Double.valueOf(currentList.get(0)).intValue() == Double.valueOf(minList.get(0)).intValue()
                                && Double.valueOf(currentList.get(1)).intValue() < Double.valueOf(minList.get(1)).intValue()) ||
                        (Double.valueOf(currentList.get(0)).intValue() == Double.valueOf(minList.get(0)).intValue()
                                && Double.valueOf(currentList.get(1)).intValue() == Double.valueOf(minList.get(1)).intValue())
                                && Double.valueOf(currentList.get(2)).intValue() < Double.valueOf(minList.get(2)).intValue()) {
                    //强制更新
                    showUpdate(context, mUpdateInfoModel, true);
                } else if (Double.valueOf(currentList.get(0)).intValue() < Double.valueOf(lastList.get(0)).intValue() ||
                        (Double.valueOf(currentList.get(0)).intValue() == Double.valueOf(lastList.get(0)).intValue()
                                && Double.valueOf(currentList.get(1)).intValue() < Double.valueOf(lastList.get(1)).intValue()) ||
                        (Double.valueOf(currentList.get(0)).intValue() == Double.valueOf(lastList.get(0)).intValue()
                                && Double.valueOf(currentList.get(1)).intValue() == Double.valueOf(lastList.get(1)).intValue()
                                && Double.valueOf(currentList.get(2)).intValue() < Double.valueOf(lastList.get(2)).intValue())) {
                    //选择更新

                    showUpdate(context, mUpdateInfoModel, false);

                } else {
                    //无更新
                    if (callback != null) {
                        callback.noUpdate();
                    }
                }


            }

            @Override
            public void failure(Throwable e) {
            }
        });


    }


    public void showUpdate(final Context context, UpgradeInfo upgradeInfo, boolean force) {
        String storage = FileUtils.AccessoryStorage + FileUtils.Apk + FileUtils.NamePath;
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(storage, PackageManager.GET_ACTIVITIES);

        final File file = new File(storage);
//        if (file.exists() && packageInfo != null
//                &&//本地文件的版本号和服务器最新的版本号一样
//                packageInfo.versionName.equals(mUpdateInfoModel.newestVersion)
//                &&//本地文件的包名和本应用一样
//                packageInfo.packageName.equals(context.getPackageName())
//                ) {
//            AlertDialog alertDialog = new AlertDialog.Builder(context)
//                    .setTitle(R.string.update_t4)
//                    .setMessage(R.string.update_t5)
//                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            FileProvider7.installApk(context, file);
//                        }
//                    })
//                    .create();
//            if (force) {
//                alertDialog.setCancelable(false);
//            } else {
//                alertDialog.setCancelable(true);
//            }
//            alertDialog.show();
//        } else {
        showUpdateDialog(context, upgradeInfo, force);
//        }
    }

    UpdateDialog updateDialog;

    /**
     * 弹窗提示下载
     *
     * @param context
     */
    private void showUpdateDialog(Context context, UpgradeInfo upgradeInfo, boolean force) {
        if (updateDialog == null) {
            updateDialog = new UpdateDialog(context, force);
        }
        //文件下载地址
        String url = upgradeInfo.appUpdateIp;
        try {
            //mUpdateInfoModel.app_ApkUrl
            updateDialog.setDownloadUrl(url)
                    .setCurrentVersionName("" + EcologyUtils.getAppVersionName())
                    .setUpdateVersionName(mUpdateInfoModel.newestVersion)
                    .setUpdateDesc(mUpdateInfoModel.latestVersionDesc)
                    .setFileName(FileUtils.ApkName)
                    .setFilePath(FileUtils.AccessoryStorage + FileUtils.Apk + FileUtils.NamePath);///storage/emulated/0/ATWallet/Apk/bjb.apk
            if (!((Activity) context).isFinishing() && !updateDialog.isShowing()) {
                updateDialog.show();
            }

            if (force) {
                updateDialog.setCancelable(false);
            } else {
                updateDialog.setCancelable(true);
            }

        } catch (Exception e) {
            updateDialog.dismiss();
        }

    }


    public interface UpdateCallback {
        //没有更新
        void noUpdate();
    }

}
