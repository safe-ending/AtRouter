package com.at.arouter.common.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;


import com.at.arouter.common.R;
import com.at.arouter.common.callback.ClickWatchCallBack;
import com.at.arouter.common.util.LogUtils;
import com.at.arouter.common.util.TimeStyleUtil;
import com.at.arouter.common.util.ToastUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * desc:  按钮点击10次之后锁定30分钟
 * author:  yangtao
 * <p>如支付按钮，表单提交。。。本地限制方式来设置按钮不可用
 * creat:  2018/9/17 11:19
 */

public class SubmitClickWatcher {

    //重复输入的次数
    public static final int MAX_CLICK_COUNT = 9;
    //锁定的时间秒数
    public static final int LOCK_TIME = 98;

    //计数
    public static final String COUNT_PRE = "click";

    //计时
    public static final String LOCK_PRE = "clear_time";

    //存的key值
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_IMPORT = "import";

    public Context context;
    //内部计数
    private int count = 0;
    private ClickWatchCallBack clickWatchCallBack;

    public SubmitClickWatcher(Context context, ClickWatchCallBack clickWatchCallBack) {
        this.context = context;
        this.clickWatchCallBack = clickWatchCallBack;
    }

    public SubmitClickWatcher(Context context) {
        this.context = context;
    }

    public void onClick(View v, String key) {
        //统计次数
        count = context.getSharedPreferences(COUNT_PRE, Activity.MODE_PRIVATE).getInt(key + "", 0);
        long firstTm = context.getSharedPreferences(LOCK_PRE, Activity.MODE_PRIVATE).getLong(key + "", -1);
        //隔天清除，当前的时间比第一次统计提交时间后的凌晨时间还要大时，初始化点击时间和次数的统计
        if (firstTm != -1 && getSecondsNextEarlyMorning(firstTm) > 0) {
            count = 0;
            //时间到，重新计算  重置点击次数和清算时间
            context.getSharedPreferences(COUNT_PRE, Activity.MODE_PRIVATE).edit().putInt(key + "", ++count).apply();
            context.getSharedPreferences(LOCK_PRE, Activity.MODE_PRIVATE).edit().putLong(key + "", -1).apply();
            clickWatchCallBack.clickComplete(count);
        } else {
            //当天的统计
            if (count < MAX_CLICK_COUNT) {
                context.getSharedPreferences(COUNT_PRE, Activity.MODE_PRIVATE).edit().putInt(key + "", ++count).apply();
                clickWatchCallBack.clickComplete(count);
            } else {
                //点击到10次后第一次统计时间
                if (context.getSharedPreferences(LOCK_PRE, Activity.MODE_PRIVATE).getLong(key + "", -1) < 0) {
                    //锁定时间记录
                    context.getSharedPreferences(LOCK_PRE, Activity.MODE_PRIVATE).edit().putLong(key + "", System.currentTimeMillis()).apply();
                    ToastUtil.show(context, context.getString(R.string.click_count_hint1) + (LOCK_TIME / 60 + 1) + context.getString(R.string.click_count_hint2));
                } else if ((System.currentTimeMillis() - context.getSharedPreferences(LOCK_PRE, Activity.MODE_PRIVATE).getLong(key + "", -1)) / 1000 < LOCK_TIME) {
                    //每次点击都更新时间计算并提示
                    long lockTm = ((System.currentTimeMillis() - context.getSharedPreferences(LOCK_PRE, Activity.MODE_PRIVATE).getLong(key + "", -1)) / 1000);
                    long waitTm = (LOCK_TIME - lockTm) / 60 + 1;
                    ToastUtil.show(context, context.getString(R.string.click_count_hint1) + waitTm + context.getString(R.string.click_count_hint2));
                } else {
                    count = 0;
                    //时间到，重新计算  重置点击次数和清算时间
                    context.getSharedPreferences(COUNT_PRE, Activity.MODE_PRIVATE).edit().putInt(key + "", ++count).apply();
                    context.getSharedPreferences(LOCK_PRE, Activity.MODE_PRIVATE).edit().putLong(key + "", -1).apply();
                    clickWatchCallBack.clickComplete(count);
                }

            }
        }
    }

    //清空统计
    public boolean clearStatis(View v, String key) {
        //统计次数
        count = context.getSharedPreferences(COUNT_PRE, Activity.MODE_PRIVATE).getInt(key + "", 0);
        long firstTm = context.getSharedPreferences(LOCK_PRE, Activity.MODE_PRIVATE).getLong(key + "", -1);
        if (count < MAX_CLICK_COUNT ||
                (System.currentTimeMillis() - context.getSharedPreferences(LOCK_PRE, Activity.MODE_PRIVATE).getLong(key + "", -1)) / 1000 > LOCK_TIME ||
                (firstTm != -1 && getSecondsNextEarlyMorning(firstTm) > 0)) {
            count = 0;
            context.getSharedPreferences(COUNT_PRE, Activity.MODE_PRIVATE).edit().putInt(key + "", count).apply();
            context.getSharedPreferences(LOCK_PRE, Activity.MODE_PRIVATE).edit().putLong(key + "", -1).apply();
            return true;
        } else {
            //每次点击都更新时间计算并提示
            long lockTm = ((System.currentTimeMillis() - context.getSharedPreferences(LOCK_PRE, Activity.MODE_PRIVATE).getLong(key + "", -1)) / 1000);
            long waitTm = (LOCK_TIME - lockTm) / 60 + 1;
            ToastUtil.show(context, context.getString(R.string.click_count_hint1) + waitTm + context.getString(R.string.click_count_hint2));
            return false;
        }

    }

    //获取时间后面到第一个凌晨0点的秒数，用以清除当天的错误输入统计
    public Long getSecondsNextEarlyMorning(long time) {

        try {
            SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");
            long overTime = (time - (sdfOne.parse(sdfOne.format(time)).getTime())) / 1000;
//            //当前毫秒数
//            LogUtils.e("当前毫秒数" + time);
//            //当前时间  距离当天凌晨  秒数
//            LogUtils.e("当前时间  距离当天凌晨  秒数" + overTime);
//            //当天凌晨毫秒数
//            LogUtils.e("当天凌晨毫秒数" + sdfOne.parse(sdfOne.format(time)).getTime());
//            //当天凌晨日期
            SimpleDateFormat sdfTwo = new SimpleDateFormat(TimeStyleUtil.DATE_TYPE6);
//            LogUtils.e("当天凌晨日期" + sdfTwo.format(sdfOne.parse(sdfOne.format(time)).getTime()));

            Calendar calendar = new GregorianCalendar();

            calendar.setTime(new Date(sdfOne.parse(sdfOne.format(time)).getTime()));
            calendar.add(calendar.DATE, 1);
            LogUtils.e(LogUtils.LOG_TAG,"次日凌晨日期" + sdfTwo.format(calendar.getTime()));
            return System.currentTimeMillis() - TimeStyleUtil.getLongFromString(sdfTwo.format(calendar.getTime()), TimeStyleUtil.DATE_TYPE6);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return -1l;
    }
}
