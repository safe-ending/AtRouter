package com.at.arouter.common.data;

/**
 * Created by yangtao on 2019/1/11.
 *
 * release版本的默认域名  以及debug 域名全局变量
 *
 * 注意：retrofit2.3版本下的baseurl需要以/结尾   在Service接口中不能以/开头
 */

public class APIHostManager {

    public static String Gank_Url = "http://gank.io/";//
    public static String Common_Url = "https://wwwtest.idasex.com/exchange/api/";//
    public static String Trade_Url = "https://wwwtest.idasex.com/ts/commonality/";//
    public static String Game_Url = "https://games.pipeapi.cloud/";//游戏
}
