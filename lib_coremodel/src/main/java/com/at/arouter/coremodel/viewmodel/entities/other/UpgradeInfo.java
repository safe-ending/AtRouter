package com.at.arouter.coremodel.viewmodel.entities.other;

import com.at.arouter.common.model.BaseModel;

/**
 * 应用升级模型
 */
public class UpgradeInfo extends BaseModel {


    public String downloadUrl;//ios用
    public String newestVersion;//比这个小选择更新“1.0.0”
    public String latestVersionDesc;
    public String lowestVersion;//比这个小强制更新“1.0.0”
    public String minVersionDesc;
    public String pic;
    public String remark;
    public String appUpdateIp;//android用


}
