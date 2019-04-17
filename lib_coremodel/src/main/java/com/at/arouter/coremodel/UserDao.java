package com.at.arouter.coremodel;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.at.arouter.coremodel.viewmodel.entities.user.UserModel;
import com.at.arouter.coremodel.util.JsonUtil;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;


/**
 * 保存(save)读取(get)删除(delete)用户对象
 *
 * 此处保存在网络模块中
 * 1.提供token
 * 2.给与子类用户信息
 */
public class UserDao {
    private static final String TAG = "UserDao";
    private static UserDao mInstance;
    private Context mContext;
    private final String mUserPath;
    /**
     * 用户基本信息
     */
    private UserModel mUser;

    private UserDao(Context context) {
        File filesDir = context.getFilesDir();
        if (!filesDir.exists()) {
            filesDir.mkdirs();
        }
        this.mUserPath = filesDir + "/user.dat";
    }

    public static UserDao getInstance(Context context) {
        if (mInstance == null) {
            synchronized (UserDao.class) {
                if (mInstance == null) {
                    mInstance = new UserDao(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 保存用户到文件中
     *
     * @return
     */
    public boolean save(UserModel userModel) {
        if (userModel == null) {
            return false;
        }
        try {
            File toFile = new File(mUserPath);
            String json = JsonUtil.JsonBean2Str(userModel);
            FileUtils.writeStringToFile(toFile, json, Charset.defaultCharset());
            mUser = userModel;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public UserModel getUser() {
        if (mUser == null) {
            try {
                File fromFile = new File(mUserPath);
                if (fromFile.exists()) {
                    String json = FileUtils.readFileToString(fromFile, Charset.defaultCharset());
                    mUser = JsonUtil.Str2JsonBean(json, UserModel.class);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mUser;
    }

    /**
     * 删除用户
     *
     * @re turn
     */
    private boolean deleteUser() {
        try {
            File dataFile = new File(mUserPath);
            if (dataFile.exists()) {
                FileUtils.forceDelete(dataFile);
            }
        } catch (Exception e) {
            Log.e(TAG, "delete", e);
            return false;
        }
        return true;
    }

    /**
     * 退出登录
     */
    public void loginOut() {
        deleteUser();
        mUser = null;
    }

    /**
     * 是否登录
     *
     * @return
     */
    public boolean isLogin() {
        return getUser() != null && !TextUtils.isEmpty(getUser().token);
    }


//    public LoginResponseBean getUserData(){
//        LoginResponseBean.UserModel userModel = new LoginResponseBean.UserModel();
//        userModel.setBindBankCard(getUser().bindBankCard);
//        userModel.setBindPay(getUser().bindPay);
//        userModel.setId(getUser().id);
//        userModel.setPayPwdExist(getUser().payPwdExist);
//        userModel.setPhone(getUser().phone);
//        userModel.setToken(getUser().token);
//        LoginResponseBean responseBean = new LoginResponseBean();
//        responseBean.setUserModel(userModel);
////        User.bean = responseBean;
//        return responseBean;
//    }


}
