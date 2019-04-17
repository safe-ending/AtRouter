package com.at.arouter.module_girls;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.at.arouter.common.data.ARouterPath;
import com.at.arouter.common.base.BaseActivity;
import com.at.arouter.common.listener.BaseClickHandler;
import com.at.arouter.common.listener.BaseDataHandler;
import com.at.arouter.common.data.Constants;
import com.at.arouter.common.util.MultiLanguageUtil;
import com.at.arouter.coremodel.viewmodel.entities.girls.GirlsData;
import com.at.arouter.coremodel.util.TAGUtils;
import com.at.arouter.coremodel.viewmodel.girls.GirlsViewModel;
import com.at.arouter.module_girls.databinding.ActivityGirlsBinding;

import org.parceler.Parcel;
import org.parceler.Parcels;

@Route(path = ARouterPath.GirlsListAty)
public class ActivityGirls extends BaseActivity {

    GirlsAdapter girlsAdapter;
    ActivityGirlsBinding mBinding;
    public DataHandler mDataHandler;
    private ClickHandler mClickHandler;

    @Override
    protected void bindMyView(ViewGroup parent, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.activity_girls,
                parent,
                true);
        mDataHandler = DataHandler.create(savedInstanceState);
        mClickHandler = new ClickHandler(this);

        //测试国际化的应用
        if (commonPref.getLanguage().equals(MultiLanguageUtil.TYPE_CN)) {
            getUIConfig().setTitle("妹子模块");
        }else{
            getUIConfig().setTitle("Module_ActivityGirls");
        }

        GirlsViewModel girlsViewModel = ViewModelProviders.of(ActivityGirls.this).get(GirlsViewModel.class);
        girlsAdapter = new GirlsAdapter(girlItemClickCallback);
        mBinding.setRecyclerAdapter(girlsAdapter);
        subscribeToModel(girlsViewModel);

    }

    @Override
    protected BaseDataHandler.UIConfig getUIConfig() {
        return mDataHandler.uiConfig.get();
    }


    public class ClickHandler
            extends BaseClickHandler {
        private ActivityGirls moudleActivity;

        public ClickHandler(ActivityGirls activity) {
            super(activity);
            this.moudleActivity = activity;
        }

    }

    @Parcel
    public static class DataHandler
            extends BaseDataHandler {

        public static DataHandler create(Bundle savedInstanceState) {
            DataHandler result = null;
            if (savedInstanceState != null) {
                result = Parcels.unwrap(savedInstanceState.getParcelable(Constants.SAVED_STATE_DATA_HANDLER));
            }

            if (result == null) {
                result = new DataHandler();
            }
            return result;
        }
    }

    GirlItemClickCallback girlItemClickCallback = new GirlItemClickCallback() {
        @Override
        public void onClick(GirlsData.ResultsBean fuliItem) {
            ARouter.getInstance()
                    .build(ARouterPath.DynaGirlsListAty)
                    .withString("fullUrl", "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/1")
                    .withTransition(R.anim.activity_up_in, R.anim.activity_up_out)
                    .navigation(ActivityGirls.this, 3);
//            Toast.makeText(ActivityGirls.this, fuliItem.getDesc(), Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 订阅数据变化来刷新UI
     *
     * @param model
     */
    private void subscribeToModel(final GirlsViewModel model) {
        //观察数据变化来刷新UI
        model.getLiveObservableData().observe(this, new Observer<GirlsData>() {
            @Override
            public void onChanged(@Nullable GirlsData girlsData) {
                Log.i(TAGUtils.LOG_TAG, "subscribeToModel onChanged onChanged");
                model.setUiObservableData(girlsData);
                girlsAdapter.setGirlsList(girlsData.getResults());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
