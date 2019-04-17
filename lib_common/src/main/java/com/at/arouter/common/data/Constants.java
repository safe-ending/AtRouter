package com.at.arouter.common.data;

import java.math.BigDecimal;

/**
 * 常量类
 */
public class Constants {


    public static final String TYPE_CN = "cn";
    public static final String TYPE_EN = "en";

    public static final String PROVIDE = "com.at.router.provider";


    //分页数据
    public static final int PAGE_SIZE = 20;
    //分页刷新
    public static final String PAGE_REFRESH = "refresh";
    //分页加载
    public static final String PAGE_LOAD = "load";

    //dabinding的数据绑定
    public static final String SAVED_STATE_DATA_HANDLER = "SAVED_STATE_DATA_HANDLER";

    public static final BigDecimal ONE_ETHER = new BigDecimal("1000000000000000000");
    public static final BigDecimal ONE_GWEI = new BigDecimal("1000000000");
    //资产   折合usd保留2位，数量4位，矿工费6位和手续费 4位
    public static final int DOT_COUNT = 8;//币
    public static final int DOT_COUNT_MINER = 6;//矿工费
    public static final int DOT_COUNT_FEE = 6;//手续费
    public static final int DOT_COUNT_MONEY = 2;//钱
    public static final int DOT_COUNT_NUMBER = 4;//数量

    public static final int K_DOT_COUNT_NUMBER = 4;//数量
    public static final int K_DOT_COUNT_FEE = 5;//数量
    public static final String PREFIX = "0x";//前缀

    //数量的位数
    public static final int INTEGER_COUNT = 5;//币
    public static final int DEMIC_COUNT = 8;//钱

    //助记词
    public static final String WORDS = "words";
    //扫描二维码
    public final static String QRCODE = "ADDRESS";

    //用户头像
    public static final String USER_HEAD_PORTRAITS = "USER_HEAD_PORTRAITS";

    //修改用户头像完成的广播
    public static final String ACTION_COMPILE_USER_HEAD_PORTRAITS_SUCCEED = "ACTION_COMPILE_USER_SUCCEED";

    //客户已上传的头像数据
    public static final String USER_HEAD_PORTRAITS_SUCCEED_DATA = "USER_HEAD_PORTRAITS_SUCCEED_DATA";

    //用户信息
    public static final String ACCOUNT = "USER_ACCOUNT";

    //是否是选地址
    public static final String ADDRESSLIST_ACTIVITY_CHOOSE = "IS_CHOOSE";

    // 转账记录界面 转账out、全部all、失败failer、收款in 转账out、全部all、失败failer、收款in
    public static final String TYPE_WAY = "way";
    public static final String TYPE_ALL = "all";
    public static final String TYPE_OUT = "out";
    public static final String TYPE_IN = "in";
    public static final String TYPE_FAI = "failer";

    //转账结果记录的状态1 已完成 2 进行中 3已取消
    public static final String STATE_TYPE = "type";
    public static final String STATE_SUCCESS = "1";
    public static final String STATE_FAIL = "3";
    public static final String STATE_IPENDING = "2";

    //转账结果记录的状态123 转入 转出  兑换
    public static final String STATE_INOUT = "inoutType";
    public static final String STATE_IN = "1";
    public static final String STATE_OUT = "2";
    public static final String STATE_EXCHANGE = "3";

    //币信息传参
    public static final String COIN_NAME = "coin";
    public static final String COIN_HASH = "txHash";
    public static final String COIN_INFO = "coin_info";
    public static final String ADDRESS = "address";

    //长连接
    public static final String TRADE_INFO = "trade_socket";
    public static final String BROADCAST_TRADE_INFO = "com.at.wallet.other.broadcast_trade_socket";

    //语言切换
    public static final String EVENT_REFRESH_LANGUAGE = "language_change";
    //钱包切换
    public static final String EVENT_REFRESH_WALLET = "wallet_change";

    //行情颜色
    public static final String COLOR_CHOOSE = "color";
    public static final String TRADE_COLOR_RED = "red";
    public static final String TRADE_COLOR_GREEN = "green";

    //币种显示结算
    public static final String COIN_CHOOSE = "coin";
    public static final String COIN_CNY = "CNY";
    public static final String COIN_USDT = "USDT";

    //资产切换
    public static final String ASSET_CHOOSE = "asset";
    public static final String ASSET_ETH = "ETH";
    public static final String ASSET_CPC = "CPC";

    //区块查询
    public static final String EXCHANGE_SELECT = "https://etherscan.io/tx/";


    //忘记密码
    public static final String FORGET_PWD = "forget_pwd";

    //行情详细ticket
    public static final String TRADE_TICKET = "ticket";


    //安全中心
    public static final String SECURITY_CODE = "security_code";
    public static final String CHENGE_PHONE_INIT = "phone_edit_init";
    public static final String CHENGE_PHONE_CONFIRM = "phone_edit_confirm";
    public static final String FORGET_LOGIN_PWD = "forget_login_pwd";
    public static final String UPDATE_LOGIN_PWD = "update_login_pwd";
    public static final String FORGET_EX_PWD = "forget_ex_pwd";
    public static final String FORCE_LOGIN = "force_login";
    public static final String SEE_WORDS = "see_words";

    //地址管理
    public static final String ADDRESS_WAY = "address_way";
    public static final String ADDRESS_ADDRESS = "address_address";
    public static final String ADDRESS_ADD = "address_add";
    public static final String ADDRESS_SEE = "address_see";
    public static final String ADDRESS_EDIT = "address_edit";

    //验证码系列
    /**
     * REGISTER("注册消息", SmsTplEnum.CLUB_USER_REGISTER),
     * RESET("重新设置用户手机号", SmsTplEnum.DEFAULT),
     * RESET_PASSWORD("重新设置登录密码", SmsTplEnum.DEFAULT),
     * BACKEND_RESET_USER_PASSWORD("后台重置用户密码", SmsTplEnum.NOTICE),
     * RESET_PAY_PASSWORD("重新设置交易密码", SmsTplEnum.DEFAULT),
     * WALLET_ETH_WITHDRWAL("提现验证码", SmsTplEnum.WALLET_ETH_WITHDRWAL),
     * CLUB_ADMIN_RESETPWD("管理员重置密码", SmsTplEnum.DEFAULT),
     * CLUB_ADMIN_ACTION("管理员操作验证码", SmsTplEnum.DEFAULT),
     * BINDACCOUNT("绑定手机号", SmsTplEnum.DEFAULT),
     * UNBINDACCOUNT("解绑手机号", SmsTplEnum.DEFAULT),
     * DEFAULT("默认消息", SmsTplEnum.DEFAULT);
     */

    public static final String CODE_REGISTER = "REGISTER";
    public static final String CODE_RESET = "RESET";
    public static final String CODE_RESET_PASSWORD = "RESET_PASSWORD";
    public static final String CODE_RESET_PAY_PASSWORD = "RESET_PAY_PASSWORD";
    public static final String CODE_BINDACCOUNT = "BINDACCOUNT";
    public static final String CODE_UNBINDACCOUNT = "UNBINDACCOUNT";

    //手机号
    public static final String PARA_PHONE = "phone";
    //支付密码
    public static final String PARA_PAY_PWD = "pay_pwd";

    //类型
    public static final String PARA_TYPE = "type";
    //不强制跳转登录
    public static final String PARA_TYPE_SIMPLE = "simple";

    //我的好友
    public static final String MY_FRIEND_STATE_OPEN = "OPEN_YBB";
    public static final String MY_FRIEND_STATE_CLOSE = "CLOSE_YBB";

    //所有数字字母汉字
    public static final String NICKNAME_PACH_ALL = "[^A-Z0-9a-z\u4E00-\u9FA5]";

    //是否从币币界面打开k线图
    public static final String IS_OPEN_FROM_MAIN = "is_open_from_main";

    public static final String COIN_DCR_ID = "coin_dcr_id";
    public static final String IS_SHOW_BTN= "is_show_btn";
    public static final String monetaryDcDec= "monetaryDcDec";
    public static final String unitPriceDec= "unitPriceDec";
    public static final String tokenDcDec= "unitPriceDec";
}
