package com.at.arouter.common.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


/**
 * desc:  本地缓存信息
 * author:  yangtao
 * <p>
 * creat: 2018/8/24 16:05
 */

public class AppPref {
    //公用管理对象 退出不清除配置
    public final static String APP_PREF = "app_preferences";
    //钱包地址管理对象 退出清空
    public final static String WALLET_PREF = "wallet_preferences";

    //语言选择
    public final static String KEY_APP_LANGUAGE = "key_app_language";
    //首页刚进入的语言选择
    public final static String KEY_LANGUAGE_CHOOSE = "key_language_state";

    //常用转账地址管理
    public final static String KEY_ETH_WALLET_ADDRESS_LIST = "key_eth_wallet_address_list";

    //域名管理
    public final static String KEY_HOST = "key_host";

    //username
    public final static String KEY_USERNAME = "key_username";


    //v1.2 当前钱包地址
    public final static String KEY_WALLET = "key_wallet";

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;


    //默认钱包配置
    public AppPref(Context context, String name) {
        if (TextUtils.isEmpty(name)) {
            name = WALLET_PREF;
        } else {
            name = APP_PREF;
        }
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    //语言切换
    public String getLanguage() {
        return sp.getString(KEY_APP_LANGUAGE, "cn");
    }

    public void setLanguage(String typeKey) {
        if (TextUtils.isEmpty(typeKey)) {
            editor.remove(KEY_APP_LANGUAGE);
        } else {
            editor.putString(KEY_APP_LANGUAGE, typeKey);
        }
        editor.commit();
    }

    //语言选择界面首次才弹出
    public boolean getLanguageState() {
        return sp.getBoolean(KEY_LANGUAGE_CHOOSE, false);
    }

    public void setLanguageState(boolean typeKey) {
        editor.putBoolean(KEY_LANGUAGE_CHOOSE, typeKey);
        editor.commit();
    }


    //----------host
    public String getHost() {
        return sp.getString(KEY_HOST, APIHostManager.Common_Url + "," + APIHostManager.Trade_Url+ "," + APIHostManager.Game_Url);
    }

    public void setHost(String host) {
        editor.putString(KEY_HOST, host);
        editor.commit();
    }


    //----------USERNAME
    public String getUsername() {
        return sp.getString(KEY_USERNAME, "");
    }

    public void setUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.commit();
    }


    //当前钱包地址
    public String getWallet() {
        return sp.getString(KEY_WALLET, "");
    }

    public void setWallet(String address) {
        editor.putString(KEY_WALLET, address);
        editor.commit();
    }

}
