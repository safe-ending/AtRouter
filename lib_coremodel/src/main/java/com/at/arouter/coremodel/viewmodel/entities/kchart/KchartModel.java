package com.at.arouter.coremodel.viewmodel.entities.kchart;

import java.io.Serializable;

/**
 * desc:  k线
 * author:  yangtao
 * <p>
 * creat:  2019/1/25 11:31
 */

public class KchartModel implements Serializable {

    public int id = 0;
    public String exchangeId;
    public String exchangeType;
    public String exchangeName;
    public String currencyType;
    public String currencyName;
    public String enName;
    public String unit;
    public String slug;
    public String iconUrl;
    public String sort;
    public String exchangeRate;
    public String lastCny;
    public String lastUsd;
    public String high;
    public String low;
    public String open;
    public String close;
    public String degree;
    public String vol;
    public String buy;
    public String sell;
    public String cslogo;
    public String ticker;
    public String mainHold;
    public String capitalInflows;
    public String codeCommit;
    public String isOperable;
    public String last;
    public String currencySymbol;
    public boolean isDealType = false;
    public boolean isOpenable = true;
    public int dcrId = -1;
    public int unitPriceDec = -1;  //单价小数位数  6
    public int tokenDcDec = -1;//基本小数位 6
    public int monetaryDcDec = -1;//计价小数位 4
}
