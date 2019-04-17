package com.at.arouter.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.at.arouter.common.R;
import com.at.arouter.common.base.BaseActivity;
import com.at.arouter.common.data.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static com.at.arouter.common.BuildConfig.APPLICATION_ID;


/**
 * 作者：Joker
 * 创建日期：2017-12-08
 * 修改时间：
 * 版权所有：
 */

public class Tools {

    private static Toast mToast;

    public static void softkeyboard(BaseActivity activity, View editText, boolean status) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && status) {
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
        } else if (imm != null) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    //判断是否有虚拟键盘
    @SuppressLint("NewApi")
    public static boolean checkDeviceHasNavigationBar(Context activity) {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }

    //获取虚拟键盘高度
    public static int getNavigationBarHeight(Context activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    /**
     * 获取EditText光标所在的位置
     */
    public static int getEditTextCursorIndex(EditText mEditText) {
        return mEditText.getSelectionStart();
    }

    /**
     * 向EditText指定光标位置插入字符串
     */
    public static void insertText(EditText mEditText, String mText) {
        mEditText.getText().insert(getEditTextCursorIndex(mEditText), mText);
    }

    /**
     * 向EditText指定光标位置删除字符串
     */
    public static void deleteText(EditText mEditText) {
        if (!TextUtils.isEmpty(mEditText.getText().toString())) {
            mEditText.getText().delete(getEditTextCursorIndex(mEditText) - 1, getEditTextCursorIndex(mEditText));
        }
    }

    public static void setDigits(EditText editText, String digits) {
        editText.setKeyListener(DigitsKeyListener.getInstance(digits));
    }

    public static void setFilters(TextView view, int length) {
        view.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    public static String insertStringInParticularPosition(String src, String dec, int position) {
        StringBuffer stringBuffer = new StringBuffer(src);
        return stringBuffer.insert(position, dec).toString();
    }

    public static LinearLayoutManager getNotRollLinearLayoutManager(Context context) {
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
    }

    public static int getWindowWidth(BaseActivity activity) {
        return activity.getWindowManager().getDefaultDisplay().getWidth();
    }

    public static int getWindowHeight(BaseActivity activity) {
        return activity.getWindowManager().getDefaultDisplay().getHeight();
    }

    public static Bundle setBundleData(String key, Object value) {
        Bundle bundle = new Bundle();
        if (value instanceof String) {
            bundle.putString(key, (String) value);
        } else if (value instanceof Integer) {
            bundle.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            bundle.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            bundle.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            bundle.putLong(key, (Long) value);
        } else {
            bundle.putString(key, value.toString());
        }
        return bundle;
    }

    public static String toString(String string) {
        return string == null ? "" : string;
    }

    public static String clearIrregular(int i, String str) {
        switch (i) {
            case 0:
                return str.replaceAll("[\u4E00-\u9FA5]", "");
            case 1:
                return str.replaceAll("[^a-zA-Z\u4E00-\u9FA5]", "");
            case 2:
                return str.replaceAll("[^0-9]", "");
            case 3:
                return str.replaceAll("[^a-zA-Z0-9]", "");
        }
        return str;
    }

    public static String stringFilter(String str, int type) throws PatternSyntaxException {
        switch (type) {
            case 1:
                String regEx1 = "[^a-zA-Z\u4E00-\u9FA5]";
                Pattern p1 = Pattern.compile(regEx1);
                Matcher m1 = p1.matcher(str);
                return m1.replaceAll("").trim();
            case 2:
                String regEx2 = "[^a-zA-Z0-9]";
                Pattern p2 = Pattern.compile(regEx2);
                Matcher m2 = p2.matcher(str);
                return m2.replaceAll("").trim();
            case 3:
                String regEx3 = "[^a-zA-Z0-9]+@[a-zA-Z]+\\.+[a-zA-Z]+";
                Pattern p3 = Pattern.compile(regEx3);
                Matcher m3 = p3.matcher(str);
                return m3.replaceAll("").trim();
            case 4:
                String regEx4 = "[^a-zA-Z0-9\u4E00-\u9FA5]";
                Pattern p4 = Pattern.compile(regEx4);
                Matcher m4 = p4.matcher(str);
                return m4.replaceAll("").trim();
            case 5:
                String regEx5 = "[^a-zA-Z0-9]";
                Pattern p5 = Pattern.compile(regEx5);
                Matcher m5 = p5.matcher(str);
                return m5.replaceAll("").trim();
            default:
                return str;
        }
    }

    public static String showMobile(String value, String tag) {
        String mobile = "";
        if (value == null) return mobile;
        if (value.length() == 11) {
            for (int i = 0; i < value.length(); i++) {
                if (TextUtils.isEmpty(mobile)) {
                    mobile = String.valueOf(value.charAt(i));
                } else mobile = mobile + value.charAt(i);
                if (value.substring(0, 1).equals("1") && (i == 2 || i == 6)) {
                    mobile = mobile + tag;
                } else if (value.substring(0, 1).equals("0")) {
                    if (i == 3) mobile = mobile + "-";
                    else if (i == 6) mobile = mobile + " ";
                }
            }
        } else return value;
        return mobile;
    }

    /**
     * 验证手机格式
     */
    public static int verificationAddress(String address) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(address)) {
            return 0;
        } else if (address.contains("@") || address.contains(".com")) {
            return isEmail(address) ? 1 : -1;
        } else if (address.length() < 6) {
            return -2;
        } else {
            String telRegex = "[0-9]*"; // "[1][3578]\\d{9}";
            return address.matches(telRegex) ? 2 : -2;
        }
    }

    //邮箱验证
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            return false;
        } else {
            return strEmail.matches(strPattern);
        }
    }

    public static String showMobile(String mobile) {
        if (TextUtils.isEmpty(mobile)) return mobile;
        StringBuffer value = new StringBuffer();
        for (int i = 0; i < mobile.length(); i++) {
            if (i == 0 || i == 1 || i == 2
                    || i == mobile.length() - 4
                    || i == mobile.length() - 3
                    || i == mobile.length() - 2
                    || i == mobile.length() - 1) {
                value.append(mobile.charAt(i));
            } else value.append("*");
        }
        return value.toString();
    }

    public static String showEmail(String email) {
        if (TextUtils.isEmpty(email) || !email.contains("@")) return email;
        StringBuffer value = new StringBuffer();
        String str = email.substring(0, email.indexOf("@"));
        for (int i = 0; i < str.length(); i++) {
            if (i == 0 || i == 1 || i == 2
                    || i == str.length() - 4
                    || i == str.length() - 3
                    || i == str.length() - 2
                    || i == str.length() - 1) {
                value.append(str.charAt(i));
            } else value.append("*");
        }
        value.append(email.substring(email.indexOf("@"), email.length()));
        return value.toString();
    }

    public static String showPrice(Double price, int type, boolean flag) {
        if (flag) return subZeroAndDot(showPrice(price, type));
        else return showPrice(price, type);
    }

    public static String showPrice(Double price, int type) {
        DecimalFormat df = null;
        switch (type) {
            case -1:
                break;
            case 0:
                df = new DecimalFormat("######0");
                break;
            case 1:
                df = new DecimalFormat("######0.0");
                break;
            case 2:
                df = new DecimalFormat("######0.00");
                break;
            case 4:
                df = new DecimalFormat("######0.0000");
                break;
            case 8:
                df = new DecimalFormat("######0.00000000");
                break;
            case 10:
                String str10 = subZeroAndDot(new BigDecimal(price + "").toString());
                int point10 = str10.indexOf(".");
                // int num10 = str10.length() - point10 - 1;
                if (price == 0 || point10 == -1) {
                    df = new DecimalFormat("######0");
                }
                break;
            case 100:
                // String str100 = getGroupingUsedNum(price);
                String str100 = subZeroAndDot(new BigDecimal(price + "").toString());
                int point100 = str100.indexOf(".");
                int num100 = str100.length() - point100 - 1;
                if (price == 0) {
                    df = new DecimalFormat("######0");
                } else if (point100 == -1) {
                    df = new DecimalFormat("######0.0000");
                } else if (num100 < 4) {
                    df = new DecimalFormat("######0.0000");
                } else if (num100 > 8) {
                    df = new DecimalFormat("######0.00000000");
                }
                break;
            case 108:
                /*String str108 = new BigDecimal(price).toString();
                int num108 = str108.length() - (str108.indexOf(".") + 1);
                if (!str108.contains(".")) {
                    df = new DecimalFormat("######0");
                } else if (num108 == 1) {
                    double eps = 1e-10;
                    if (price - Math.floor(price) < eps) {
                        df = new DecimalFormat("######0");
                    }
                } else if (num108 > 8) {
                    df = new DecimalFormat("######0.00000000");
                }*/
                // String str108 = getGroupingUsedNum(price);
                String str108 = subZeroAndDot(new BigDecimal(price + "").toString());
                int point108 = str108.indexOf(".");
                int num108 = str108.length() - point108 - 1;
                if (price == 0 || point108 == -1) {
                    df = new DecimalFormat("######0");
                } else if (num108 > 8) {
                    df = new DecimalFormat("######0.00000000");
                }
                break;
            case 12:
                if (price == 0) {
                    df = new DecimalFormat("######0");
                } else {
                    df = new DecimalFormat("######0.00");
                }
                break;
            case 14:
                if (price == 0) {
                    df = new DecimalFormat("######0");
                } else {
                    df = new DecimalFormat("######0.0000");
                }
                break;
            case 18:
                if (price == 0) {
                    df = new DecimalFormat("######0");
                } else {
                    df = new DecimalFormat("######0.00000000");
                }
                break;
            case 26:
                String str26 = subZeroAndDot(new BigDecimal(price + "").toString());
                int point26 = str26.indexOf(".");
                int num26 = str26.length() - point26 - 1;
                if (point26 == -1) {
                    df = new DecimalFormat("######0.00");
                } else if (num26 > 6) {
                    df = new DecimalFormat("######0.000000");
                }
                break;
            case 28:
                // String str28 = getGroupingUsedNum(price);
                String str28 = subZeroAndDot(new BigDecimal(price + "").toString());
                int point28 = str28.indexOf(".");
                int num28 = str28.length() - point28 - 1;
                if (point28 == -1) {
                    df = new DecimalFormat("######0.00");
                } else if (num28 > 8) {
                    df = new DecimalFormat("######0.00000000");
                }
                break;
            default:
                df = new DecimalFormat("######0.00000000");
        }
        if (df != null) {
            df.setRoundingMode(RoundingMode.DOWN);
            df.setGroupingUsed(false);
            return df.format(price);
        } else {
            /*NumberFormat nf = NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            return nf.format(price);*/
            return subZeroAndDot(new BigDecimal(price + "").toString());
        }
    }

    public static String getGroupingUsedNum(double price) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        return nf.format(price);
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", ""); // 去掉多余的0
            s = s.replaceAll("[.]$", ""); // 如最后一位是.则去掉
        }
        return s;
    }

    public static String showNum(String num) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < num.length(); i++) {
            if (i == 0 || i == 1 || i == 2 ||
                    i == num.length() - 3 || i == num.length() - 2 || i == num.length() - 1) {
                buffer.append(num.charAt(i));
            } else buffer.append("*");
        }
        return buffer.toString();
    }

    public static double convertPrice(double price) {
        return Double.parseDouble(new BigDecimal(price).toPlainString());
    }

    public static String getPrice(double price, int number) {
        DecimalFormat df = null;
        switch (number) {
            case 0:
                df = new DecimalFormat("######0");
                break;
            case 1:
                df = new DecimalFormat("######0.0");
                break;
            case 2:
                df = new DecimalFormat("######0.00");
                break;
        }
        return df.format(price);
    }


    public static void loadNewsPicture(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .centerCrop()
                        .override(
                                (getWindowWidth((BaseActivity) context) - context.getResources().getDimensionPixelSize(R.dimen.size_50dp)) / 3,
                                context.getResources().getDimensionPixelSize(R.dimen.size_80dp))
                        .placeholder(R.color.white)
                        .error(R.color.white)
                        .priority(Priority.LOW)
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(imageView);
    }


    public static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context, Constants.PROVIDE, file);
        } else uri = Uri.fromFile(file);
        return uri;
    }

//    //保存文件到指定路径
//    public static boolean saveImageToGallery2(Context context, Bitmap bmp, String name) {
//        // 首先保存图片
//        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + MyApplication.getInstance().getPackageName();
//        File appDir = new File(storePath);
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
//        // String fileName = System.currentTimeMillis() + ".jpg";
//        String fileName = SPUtils.get(context, PHONE, "") + "_" + name + ".jpg";
//        File file = new File(appDir, fileName);
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            // 通过io流的方式来压缩保存图片
//            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
//            fos.flush();
//            fos.close();
//
//            // 把文件插入到系统图库
//            // MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
//
//            // 保存图片后发送广播通知更新数据库
//            Uri uri = Uri.fromFile(file);
//            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
//            return isSuccess;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public static void exceptionBusiness(Context context, String e) {
//        if (e.contains(CONNECT_EXCEPTION) || e.contains(UNKNOWN_HOST_EXCEPTION)) {
//            Tools.showMsg(context, context.getString(R.string.a888));
//        } else if (e.contains(SOCKET_TIME_OUT_EXCEPTION)) {
//            Tools.showMsg(context, context.getString(R.string.a889));
//        } else if (e.contains(JSON_SYNTAX_EXCEPTION)) {
//            Tools.showMsg(context, context.getString(R.string.a890));
//        } else if (e.contains(RESPONSE_CODE) && e.contains(BE_OVERDUE)) {
//            AppUtils.logonExpires(context);
//        } else if (e.contains(RESPONSE_CODE)) {
//            Tools.showMsg(context, context.getString(R.string.a891, e.substring(e.lastIndexOf(":"), e.length())));
//        } else Tools.showMsg(context, context.getString(R.string.a892));
//    }
//
//    public static void exceptionBusiness(Context context, String e, TextView textView) {
//        if (e.contains(CONNECT_EXCEPTION) || e.contains(UNKNOWN_HOST_EXCEPTION)) {
//            textView.setText(R.string.a888);
//        } else if (e.contains(SOCKET_TIME_OUT_EXCEPTION)) {
//            textView.setText(R.string.a889);
//        } else if (e.contains(JSON_SYNTAX_EXCEPTION)) {
//            textView.setText(R.string.a890);
//        } else if (e.contains(RESPONSE_CODE) && e.contains(BE_OVERDUE)) {
//            textView.setText(R.string.a955);
//            AppUtils.logonExpires(context);
//        } else if (e.contains(RESPONSE_CODE)) {
//            textView.setText(context.getString(R.string.a891, e.substring(e.lastIndexOf(":"), e.length())));
//        } else textView.setText(R.string.a892);
//    }
//
//    public static void beOverdue(Context context, String e) {
//        if (e.contains(RESPONSE_CODE) && e.contains(BE_OVERDUE)) {
//            AppUtils.logonExpires(context);
//        }
//    }
//
//    public static boolean loginOverdue(Context context, String e) {
//        if (e.contains(RESPONSE_CODE) && e.contains(BE_OVERDUE)) {
//            return true;
//        }
//        return false;
//    }

}
