package com.at.arouter.coremodel.viewmodel.entities.user;

/*
 *  @描述：    登录时的用户信息
 */

import java.io.Serializable;

public class UserModel
        implements Serializable
{
    public String id;
    public String nickName;
    public String phone;         //
    public String token;             //
    public String username;          //
    public String createTime;     //
    public String loginTime;    //
    public String vipLevel; //1 2 3

    public boolean bindPay;
    public boolean bindBankCard;
    public boolean payPwdExist;


    public UserModel(String id, String phone) {
        this.id = id;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isBindPay() {
        return bindPay;
    }

    public void setBindPay(boolean bindPay) {
        this.bindPay = bindPay;
    }

    public boolean isBindBankCard() {
        return bindBankCard;
    }

    public void setBindBankCard(boolean bindBankCard) {
        this.bindBankCard = bindBankCard;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isPayPwdExist() {
        return payPwdExist;
    }

    public void setPayPwdExist(boolean payPwdExist) {
        this.payPwdExist = payPwdExist;
    }
}
