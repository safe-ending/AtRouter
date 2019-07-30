package com.at.arouter.module_girls;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.at.arouter.common.data.ARouterPath;
import com.at.arouter.common.base.BaseActivity;
import com.at.arouter.common.listener.BaseDataHandler;
import com.at.arouter.coremodel.viewmodel.entities.girls.GirlsData;
import com.at.arouter.coremodel.http.util.TAGUtils;
import com.at.arouter.coremodel.viewmodel.base.BaseViewFullModel;
import com.at.arouter.coremodel.viewmodel.girls.DynamicGirlsViewModel;
import com.at.arouter.module_girls.databinding.ActivityGirlsBinding;


/**
 * Created by yt on 2017/11/20.
 */
@Route(path = ARouterPath.DynaGirlsListAty)
public class ActivityDynamicGirls extends BaseActivity {
    @Autowired(name = "fullUrl")
    public String fullUrl;
    GirlsAdapter            girlsAdapter;
    ActivityGirlsBinding mBinding;


    @Override
    protected void bindMyView(ViewGroup parent, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.activity_girls,
                parent,
                true);

        setTitle("Module_ActivityDynamicGirls");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding = DataBindingUtil.setContentView(ActivityDynamicGirls.this,R.layout.activity_girls);
        //inject需要注入后才可以读取到携带过来的参数
        ARouter.getInstance().inject(this);
        Log.i(TAGUtils.LOG_TAG, "fullUrl-->"+fullUrl);
        if(TextUtils.isEmpty(fullUrl)){
            return;
        }

        DynamicGirlsViewModel dynamicGirlsViewModel =
                ViewModelProviders.of(ActivityDynamicGirls.this).get(DynamicGirlsViewModel.class);
        dynamicGirlsViewModel.loadData(fullUrl);
        girlsAdapter = new GirlsAdapter(girlItemClickCallback);
        mBinding.setRecyclerAdapter(girlsAdapter);
        subscribeToModel(dynamicGirlsViewModel);
    }

    @Override
    protected BaseDataHandler.UIConfig getUIConfig() {
        return null;
    }





    GirlItemClickCallback   girlItemClickCallback = new GirlItemClickCallback() {
        @Override
        public void onClick(GirlsData.ResultsBean fuliItem) {
            Toast.makeText(ActivityDynamicGirls.this, fuliItem.getDesc(), Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 订阅数据变化来刷新UI
     * @param model
     */
    private void subscribeToModel(final BaseViewFullModel model){
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
