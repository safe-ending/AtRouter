package com.at.arouter.third.utils;

import android.Manifest;
import android.os.Environment;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public interface Constants {

    // int DEBUGLEVEL = L.LEVEL_ALL;

    String app_key = "awi9Of";
    String secret = "ctc_secret";

    int ORDER_TYPE_ALL = 0;//全部
    int ORDER_TYPE_UNPAID = 1;//未付款
    int ORDER_TYPE_PAID = 2;//已付款
    int ORDER_TYPE_COMPLETED = 3;//已完成
    int ORDER_TYPE_APPEAR = 4;//申诉中

    int PUBLISH_TYPE_ALL = 0;//全部
    int PUBLISH_TYPE_PENDING = 1;//挂单中
    int PUBLISH_TYPE_TRANSACTION = 2;//交易中
    int PUBLISH_TYPE_COMPLETED = 3;//已完成

    //状态 我的订单  //1-挂单，  2-未付款，3-已付款，4-已完成，  5-取消订单   6-申诉中，
    //状态 我的发布    1-挂单中，2-交易中，3-已完成，4-系统取消，5-未确认订单，6-用户取消）

    int ORDER_STATUS_PENDING = 1;//挂单中
    int ORDER_STATUS_UNPAID = 2;//未付款
    int ORDER_STATUS_PAID = 3;//已付款
    int ORDER_STATUS_COMLETED = 4;//已完成
    int ORDER_STATUS_CANCEL = 5;//取消订单
    int ORDER_STATUS_APPEAR = 6;//申诉中


    String ORDER_PENDING = "PENDING";
    String ORDER_TRANSATION = "TRANSATION";
    String ORDER_COMPLETE = "COMPLETE";
    String ORDER_CANCEL = "CANCEL";
    String ORDER_UNCONFIRMED = "UNCONFIRMED";


    int PAYMENT_WEICHAT = 1;//微信
    int PAYMENT_ALIPAY = 2;//支付宝
    int PAYMENT_YINLIAN = 3;//银联

    String USER_PHONE = "phone";
    // String USER_NAME = "username";
    String PASS_WORD = "password";
    String USER = "user";
    String IS_LOGIN = "islogin";

    String SP_USER_INFO = "userInfo";

    String CLIENT_TYPE_ANDROID = "2";


    int ORDER_SELL = 1;
    int ORDER_BUY = 2;
    int ACTIVITY_PAPERWORK_INFO = 1;
    int BIND_TYPE_ZFB = 1;
    int BIND_TYPE_WX = 2;
    int BIND_TYPE_YL = 3;
    String MEDIATYPE = "application/json;charset=utf-8";

    int MEMBER_ORDINARY = 1;//普通用户
    int MEMBER_VIP = 2;//VIP用户
    int MEMBER_SPECIAL_VIP = 3;//特殊VIP用户


    /**
     * 扫描类型
     * 条形码或者二维码：REQUEST_SCAN_MODE_ALL_MODE
     * 条形码： REQUEST_SCAN_MODE_BARCODE_MODE
     * 二维码：REQUEST_SCAN_MODE_QRCODE_MODE
     */
    String REQUEST_SCAN_MODE = "ScanMode";
    /**
     * 条形码： REQUEST_SCAN_MODE_BARCODE_MODE
     */
    int REQUEST_SCAN_MODE_BARCODE_MODE = 0X100;
    /**
     * 二维码：REQUEST_SCAN_MODE_ALL_MODE
     */
    int REQUEST_SCAN_MODE_QRCODE_MODE = 0X200;
    /**
     * 条形码或者二维码：REQUEST_SCAN_MODE_ALL_MODE
     */
    int REQUEST_SCAN_MODE_ALL_MODE = 0X300;


    double PRICE_MAX = 10000000;//价格最大值
    String MEMBER_PRICE = "199";
    String LANGUAGE = "language";
    int CURRENCY_MARKET = 1;//大盘 币种关系
    int CURRENCY_C2C = 2;//币币 币种关系
    int CURRENCY_P2P = 3;//点对点 币种关系


    //类型,1-会员升级,2-静态奖,3-直推奖,4-共享奖,5-幸运奖,6-提取USD,7-会员返利,8-服务费
    int BONUS_TYPE_ALL = 0;//全部
    int BONUS_TYPE_MEMBER_UPGRADE = 1;//会员升级
    int BONUS_TYPE_STATIC = 2;//静态奖
    int BONUS_TYPE_SHARE = 3;//分享奖
    int BONUS_TYPE_SHARED = 4;//共享奖
    int BONUS_TYPE_LUCKY = 5;//幸运奖
    int BONUS_TYPE_EXTRACT = 6;//提取USD
    int BONUS_TYPE_REBATE = 7;//会员返利
    int BONUS_TYPE_SERVICE = 8;//服务费


    int CURRENCY_TYPE_C2C = 4;
    int CURRENCY_TYPE_P2P = 3;
    String MINI_EXTRACT = "0.001";//最小提币数量;
    String SP_USER_JSON = "user_json";


    String CONNECT_EXCEPTION = "ConnectException";
    String UNKNOWN_HOST_EXCEPTION = "UnknownHostException";
    String SOCKET_TIME_OUT_EXCEPTION = "SocketTimeoutException";
    String JSON_SYNTAX_EXCEPTION = "JsonSyntaxException";
    String RESPONSE_CODE = "reponse's code";
    String BE_OVERDUE = "401";

    String MAIN_HOST_KEY = "main_host_key";
    String WEB_HOST_KEY = "host_key";

    String RESPONSE_DATA = "Response Data";
    String MSG = "msg";

    String ANDROID = "android";
    String PHONE = "phone";
    String EMAIL = "email";
    String OLD_PHONE = "oldPhone";
    String PWD = "pwd";
    String OLD_PWD = "oldPwd";
    String TYPE = "type";
    String CODE = "code";
    String INVITE_CODE = "inviteCode";
    String USER_NAME = "username";
    String ID = "id";
    String USER_ID = "userId";
    String TOKEN = "token";
    String CREATE_TIME = "createtime";
    String PAY_PWD = "payPwd";
    String CHANGE_PAY_PWD = "changePayPwd";
    String RESET_PAY_PWD = "resetPayPwd";
    String COIN_TYPE = "coinType";
    String TO_ADDR = "toAddr";
    String TO_Phone = "toPhone";
    String AMOUNT = "amount";
    String PAGE_ID = "pageId";
    String PAGE_SIZE = "pageSize";
    String STATUS = "status";
    String PAGE_NUM = "pageNum";
    String OWNER = "OWNER";
    String SHARE = "SHARE";
    String LEADER = "LEADER";
    String WITHDRAW = "WITHDRAW";
    String TEAM_SERVICE = "TEAM_SERVICE";
    String TEAM_SERVICE_LEADER = "TEAM_SERVICE_LEADER";
    String COMMON = "COMMON";
    String LEVEL1 = "LEVEL1";
    String LEVEL12 = "LEVEL12";
    String LEVEL13 = "LEVEL13";
    String LEVEL2 = "LEVEL2";
    String BONUS_ETH = "BONUS_ETH";
    String BONUS_CPC = "BONUS_CPC";
    String WALLET_ETH = "WALLET_ETH";
    String WALLET_CPC = "WALLET_CPC";
    String PAGING = "PAGING";
    String BUY_EXTRACTION_CARD = "BuyExtractionCard";
    String ADDRESS = "address";
    String MEMBER = "member";
    String PAGE = "page";
    String SIZE = "size";
    String HOT = "hot";
    String NEWSFLASH = "newsflash";
    String VIEWPOINT = "viewpoint";
    String NICKNAME = "nickName";
    String CONTENT = "content";
    String PARENT = "parent";
    String UPLOAD_VOUCHER = "upload_voucher";
    String BUY_TRADE_BAR = "buy_trade_bar";
    String SELL_TRADE_BAR = "sell_trade_bar";
    String CARRY_ON = "carry_on";
    String COMPLETE = "complete";
    String CANCEL = "cancel";
    String VIEW = "view";
    String DC_ID = "dcId";
    String ADS_TYPE = "adsType";
    String PAY_WAY = "payway";
    String EXPIRE = "expire";
    String PRICE = "price";
    String QUALITY = "quality";
    String MIN_QUALITY = "minQuality";
    String MAX_QUALITY = "maxQuality";
    String ADS_ID = "adsId";
    String FILES = "files";
    String FILE = "file";
    String FEE_DC_ID = "feeDcId";
    String APPLY_ID = "applyId";
    String IDS = "ids";
    String NAME = "name";
    String ACCOUNT_NAME = "accountName";
    String BANK_CODE = "bankCode";
    String REAL_NAME = "realName";
    String BANK_NAME = "bankName";
    String BANK_BRANCH = "bankBranch";
    String CLUB_VIP = "CLUB_VIP";
    String GIVE_CPC = "give_CPC";
    String GIVE_CARD = "give_card";
    String GIVE_QUOTA = "give_quota";
    String AUTHOR = "author";
    //  CONFIRM = "CONFIRM";
    String UNTREATED = "UNTREATED";
    String PROCESS = "PROCESS";
    String EVALUATE = "EVALUATE";
    String SLAVE = "slave";
    String START = "start";


    // 验证码类型
    String REGISTER = "REGISTER";
    String RESET_PASSWORD = "RESET_PASSWORD";
    String RESET_PAY_PASSWORD = "RESET_PAY_PASSWORD";
    String WALLET_ETH_WITHDRWAL = "WALLET_ETH_WITHDRWAL";
    String RESET_PHONE = "RESET_PHONE";


    String RESULE_CODE = "request_code";
    String RESULR_CODE = "result_code";
    String IS_ASSETS = "isAssets";
    String FUND = "fund";
    String NEW = "new";
    String RESULT = "result";
    String ETH = "ETH";
    String CPC = "CPC";
    String MODIFY = "modify";
    String BONUS_EXPLAIN = "bonus_explain";
    String EXTRACT_EXPLAIN = "extract_explain";
    String PLATFORM = "platform";
    String WALLET = "wallet";
    String FLAG = "flag";
    String VIP = "VIP";
    String SVIP = "SVIP";
    String VIP3 = "VIP3";
    String VIP2 = "VIP2";
    String VIP1 = "VIP1";
    String FROZEN_EXPLAIN = "frozen_explain";
    String DETAIL = "detail";
    String BUY = "buy";
    String SELL = "sell";
    String TRANSACTION = "transaction";
    String TRADE_BAR = "trade_bar";
    String ORDER = "order";
    String SEQ_NO = "seqNo";
    String RATE = "rate";
    String VOUCHER_URL = "voucher_url";
    String BUYER = "buyer";
    String SERVICE_CHARGE = "Service_Charge";
    String ALIPAY = "ALIPAY";
    String BANK = "bank";
    String INFO = "info";
    String AUTHENTICATION = "Authentication";
    String NOTICE = "notice";
    String VALIDATE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String INVITE_EMAIL = "inviteEmail";
    String INVITE_PHONE = "invitePhone";

    int BOOT_PAGE_ANIMATION_TIME = 1000;
    int DROP_ANIMATION_TIME = 1000;
    int DIALOG_ANIMATION_TIME = 500;
    int REQUEST_LOGIN = 1;
    int RESULT_LOGIN = 2;
    int REQUEST_CAMERA = 11;
    int RESULT_CAMERA = 22;

    String HOME_FRAGMENT_KEY = "homepageFragment";
    String FIND_FRAGMENT_KEY = "findFragment";
    String ACTIVITY_FRAGMENT_KEY = "activityFragment";
    String ASSETS_FRAGMENT_KEY = "assetsFragment";
    String MY_FRAGMENT_KEY = "myFragment";

    String APK_URL = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/chainplusclub/";
    String APK_URL_FLAG = "apk_url";

    String[] EXTERNAL_PERMISSION = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String[] CAMERA_PERMISSION = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
    String[] PHONE_PERMISSION = {Manifest.permission.CALL_PHONE};

}
