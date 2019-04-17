package com.at.kchart.charting.ui;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.at.arouter.common.data.ARouterPath;
import com.at.arouter.common.data.Constants;
import com.at.arouter.common.util.DecimalCalUtils;
import com.at.arouter.common.util.StatusBarUtil;
import com.at.arouter.common.util.Tools;
import com.at.arouter.common.widget.NoTouchScrollViewpager;
import com.at.arouter.coremodel.viewmodel.entities.kchart.KchartModel;
import com.at.arouter.kchart.R;
import com.at.kchart.charting.utils.CommonUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;


/**
 * 股票详情页
 */
@Route(path = ARouterPath.KchartAty)
public class StockDetailActivity extends FragmentActivity {


    TabLayout tabLayout;
    NoTouchScrollViewpager viewPager;
    Toolbar toolbar;
    RelativeLayout rl_top;
    TextView tvUsdt;//成交金额
    TextView tvCny;
    TextView tvUpDown;
    TextView tv24max;
    TextView tv24num;//24小时成交量
    TextView tv24min;

    private String type = "";
    private KchartModel tradeModel;
    public int dcrId = -1;
    private boolean isHaveBtn = false;
    String[] titles;

    private String currencyNameS;
    private String exchangeNameS;

    // 将数据保存到savedInstanceState对象中, 该对象会在重建activity时传递给onCreate方法
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("full", full);
        savedInstanceState.putInt("lastItem", lastItem);

    }

    BaseFragment[] fragments;
    SimpleFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            full = savedInstanceState.getBoolean("full", false);
            lastItem = savedInstanceState.getInt("lastItem", 0);
        }

        setContentView(R.layout.activity_stock_detail);
        //设置状态栏颜色
        StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.color_FFFFFF));
        //日间模式速度比夜间模式快很多？
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.view_pager);
        toolbar = findViewById(R.id.toolbar);
        rl_top = findViewById(R.id.rl_top);
        tvUsdt = findViewById(R.id.tv_usdt);

        tvCny = findViewById(R.id.tv_cny);
        tvUpDown = findViewById(R.id.tv_up_down);
        tv24max = findViewById(R.id.tv_24max);
        tv24num = findViewById(R.id.tv_24num);
        tv24min = findViewById(R.id.tv_24min);

        titles = new String[]{getResources().getString(R.string.kline_m5),
                getResources().getString(R.string.kline_m30),
                getResources().getString(R.string.kline_h1),
                getResources().getString(R.string.kline_d1),
                getResources().getString(R.string.kline_w1),
                getResources().getString(R.string.kline_all)};
        type = getIntent().getStringExtra(Constants.COIN_NAME) + "";
        dcrId = getIntent().getIntExtra(Constants.COIN_DCR_ID, -1);
        isHaveBtn = getIntent().getBooleanExtra(Constants.IS_SHOW_BTN, false);


        toolbar.setTitle(type);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        KchartModel tradeModel = (KchartModel) getIntent().getSerializableExtra(Constants.TRADE_TICKET);
        setData(tradeModel);
        String ticket = "ethbtc";
        if (fragments == null || fragments.length == 0) {
            fragments = new BaseFragment[]{ChartKLineFragment.newInstance(0.005, true, ticket), ChartKLineFragment.newInstance(0.03, true, ticket),
                    ChartKLineFragment.newInstance(0.1, true, ticket), ChartKLineFragment.newInstance(1, true, ticket),
                    ChartKLineFragment.newInstance(7, true, ticket), ChartKLineFragment.newInstance(100, true, ticket)};
        }
        viewPager.setOffscreenPageLimit(fragments.length);
        adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //横竖屏切换切换  状态保存为full  放入onSaveInstanceState中在切换后进入OnCreate中拿取
                if (tab.getPosition() == 5) {
                    tab.setText("");
                    tab.setIcon(R.mipmap.land);
                    if (!full) {
                        full = true;
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
                    } else {
                        full = false;
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制为竖屏
                    }
                    setFullScreen(full);
                } else {
                    lastItem = tab.getPosition();
                }
                viewPager.setCurrentItem(lastItem);
                tabLayout.getTabAt(lastItem).select();
                tabLayout.setScrollPosition(lastItem, 0, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);

        //修改tablayout的下划线宽度
        tabLayout.post(new Runnable() {
            @Override//我们在这里对TabLayout的宽度进行修改。。数值越大表示宽度越小。
            public void run() {
                setIndicator(tabLayout, 10, 10);
            }
        });
        //全屏按钮
        TabLayout.Tab tab = tabLayout.getTabAt(5);
        if (tab != null) {
            tab.setText("");
            tab.setIcon(R.mipmap.land);
        }
    }


    //设置顶部信息
    void setData(KchartModel tradeModel) {
        try {
            this.tradeModel = tradeModel;
            if (isHaveBtn) {
                currencyNameS = tradeModel.currencyName.toUpperCase();
                exchangeNameS = tradeModel.exchangeName.toUpperCase();
            } else {
                currencyNameS = tradeModel.currencyName;
                exchangeNameS = tradeModel.exchangeName;
            }
            toolbar.setTitle(exchangeNameS + "/" + currencyNameS);

            tvUsdt.setText("" + (isHaveBtn ? tradeModel.lastUsd : tradeModel.lastCny));
            tvCny.setText("  $" + DecimalCalUtils.round(new BigDecimal(tradeModel.lastCny).doubleValue(), Constants.DOT_COUNT_MONEY));
            tv24max.setText("" + DecimalCalUtils.round(new BigDecimal(tradeModel.high).doubleValue(), Constants.DOT_COUNT_MONEY));
            tv24min.setText("" + DecimalCalUtils.round(new BigDecimal(tradeModel.low).doubleValue(), Constants.DOT_COUNT_MONEY));
            Double numb24 = Double.valueOf(tradeModel.vol);
            tv24num.setText(Tools.showPrice(isHaveBtn ? numb24 : numb24 * 10000, 0));

            if (new BigDecimal(tradeModel.degree).compareTo(BigDecimal.ZERO) > 0) {

                if (tradeModel.degree.contains("+")) {
                    tvUpDown.setText("" + tradeModel.degree + "%");
                } else {
                    tvUpDown.setText("+" + tradeModel.degree + "%");
                }

                tvUsdt.setTextColor(ContextCompat.getColor(this, R.color.up_color));
                tvUpDown.setTextColor(ContextCompat.getColor(this, R.color.up_color));


            } else if (new BigDecimal(tradeModel.degree).compareTo(BigDecimal.ZERO) < 0) {

                if (tradeModel.degree.contains("-")) {
                    tvUpDown.setText("" + tradeModel.degree + "%");
                } else {
                    tvUpDown.setText("-" + tradeModel.degree + "%");
                }

                tvUsdt.setTextColor(ContextCompat.getColor(this, R.color.down_color));
                tvUpDown.setTextColor(ContextCompat.getColor(this, R.color.down_color));


            } else {
                tvUpDown.setText("" + tradeModel.degree + "%");
                tvUsdt.setTextColor(ContextCompat.getColor(this, R.color.TitleColor));
                tvUsdt.setTextColor(ContextCompat.getColor(this, R.color.equal_color));
                tvUpDown.setTextColor(ContextCompat.getColor(this, R.color.equal_color));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置tablayout的下划线
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
            LinearLayout llTab = null;
            try {
                llTab = (LinearLayout) tabStrip.get(tabs);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
            int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

            for (int i = 0; i < llTab.getChildCount(); i++) {
                View child = llTab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = left;
                params.rightMargin = right;
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        //开启长连接
    }

    @Override
    public void onPause() {
        super.onPause();
        //关闭长连接
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragments = null;
        //再次执行，确保关闭
        if (serviceConn != null && isBind) {
            this.unbindService(serviceConn);
            isBind = false;
        }
    }


    @Override
    public void onBackPressed() {
        if (full) {
            full = false;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制为竖屏
            setFullScreen(full);
        } else {
            finish();
        }
    }

    private boolean full = false;
    private int lastItem = 0;


    //隐藏状态栏
    private void setFullScreen(boolean fullScreen) {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        try {
            if (fullScreen) {
                attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                getWindow().setAttributes(attrs);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                if (full && toolbar != null && rl_top != null) {
                    LinearLayout.LayoutParams la = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CommonUtil.dip2px(this, 50));
                    la.topMargin = CommonUtil.dip2px(this, 40);
                    toolbar.setLayoutParams(la);
                    toolbar.setVisibility(View.GONE);
                    rl_top.setVisibility(View.GONE);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    viewPager.setLayoutParams(layoutParams);
                }

                //把原来的fragment时间区间位置重置
                fragments[lastItem].updateIndex();
            } else {
                attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().setAttributes(attrs);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                if (!full && toolbar != null && rl_top != null) {
                    LinearLayout.LayoutParams la = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CommonUtil.dip2px(this, 50));
                    la.topMargin = CommonUtil.dip2px(this, 0);
                    toolbar.setLayoutParams(la);
                    toolbar.setVisibility(View.VISIBLE);
                    rl_top.setVisibility(View.VISIBLE);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CommonUtil.dip2px(this, 408));
                    viewPager.setLayoutParams(layoutParams);
                }
                //把原来的fragment时间区间位置重置
                fragments[lastItem].updateIndex();
            }
//        ll_bottom.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //=============================================================================================
    boolean isBind = false;
    private ServiceConnection serviceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }
    };

}
