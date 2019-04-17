package com.at.arouter.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;

/**
 * 检查手机状态
 *
 * @author Yek Mobile
 */
public class GetPhoneState {
    private static ConnectivityManager connManager = null;
    private static TelephonyManager telephonyManager = null;
    public static DisplayMetrics dm;


    /**
     * 检测SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable(Context appContext) {
        Context context = appContext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取当前操作系统的语言
     *
     * @return String 系统语言
     */
    public static String getSysLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取手机型号
     *
     * @return String 手机型号
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取操作系统的版本号
     *
     * @return String 系统版本号
     */
    public static String getSysRelease() {
        return android.os.Build.VERSION.RELEASE;
    }


    /**
     * 得到手机的宽度
     *
     * @param context
     * @return
     */
    public static int getPhoneWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 得到手机的高度
     *
     * @param context
     * @return
     */
    public static int getPhoneHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int getScrrenMin(Context context) {
        return Math.min(getPhoneWidth(context), getPhoneHeight(context));
    }

    /**
     * 获取当前应用版本号
     *
     * @param context
     * @return
     */
    public static String getApplicationVersion(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        String version = null;
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return version;
    }


    /**
     * 获取网络类型
     *
     * @param context 上下文
     * @return String 返回网络类型
     */
    public static String getAccessNetworkType(Context context) {
        int type = 0;
        if (connManager != null) {
            type = connManager.getActiveNetworkInfo().getType();
        } else {
            connManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            type = connManager.getActiveNetworkInfo().getType();
        }
        if (type == ConnectivityManager.TYPE_WIFI) {
            return "wifi";
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            return "3G/GPRS";
        }
        return null;
    }

    /**
     * 获取当前时间
     */
    public static String getNowTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(Calendar.getInstance().getTime());
    }

    /**
     * 获取手机Ip地址
     *
     * @return
     */
    public static String getLocalIpAddress() {

        try {

            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {

                NetworkInterface intf = en.nextElement();

                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {

                    InetAddress inetAddress = enumIpAddr.nextElement();

                    if (!inetAddress.isLoopbackAddress()) {

                        return inetAddress.getHostAddress().toString();

                    }

                }

            }

        } catch (SocketException ex) {

            Log.e("", ex.toString());

        }

        return null;

    }

    /**
     * 得到数据库文件夹目录在系统中(手机中)的绝对路径
     *
     * @return
     */
    public static String getDatabasePath(Context context) {
        return context.getDir("databases", Context.MODE_WORLD_WRITEABLE)
                .getParentFile().getAbsolutePath()
                + "/databases";
    }

}
