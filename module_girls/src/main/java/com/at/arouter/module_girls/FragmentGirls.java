package com.at.arouter.module_girls;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.at.arouter.common.data.ARouterPath;
import com.at.arouter.common.base.BaseFragment;
import com.at.arouter.coremodel.viewmodel.entities.girls.GirlsData;
import com.at.arouter.coremodel.util.TAGUtils;
import com.at.arouter.coremodel.viewmodel.girls.GirlsViewModel;
import com.at.arouter.module_girls.databinding.FragmentGirlsBinding;


/**
 * @Desc FragmentGirls
 */
@Route(path = ARouterPath.GirlsListFgt)
public class FragmentGirls extends BaseFragment {

    FragmentGirlsBinding mBinding;

    GirlsAdapter            girlsAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void reload() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_girls;
    }

    @Override
    protected boolean convertView(ViewDataBinding bind) {
        boolean error = false;
        if (bind instanceof FragmentGirlsBinding) {
            mBinding = (FragmentGirlsBinding) bind;
        } else {
            error = true;
        }
        return error;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initData() {
        ARouter.getInstance().inject(FragmentGirls.this);
        girlsAdapter = new GirlsAdapter(girlItemClickCallback);
        mBinding.setRecyclerAdapter(girlsAdapter);
        final GirlsViewModel girlsViewModel =
                ViewModelProviders.of(FragmentGirls.this).get(GirlsViewModel.class);

        subscribeToModel(girlsViewModel);
    }


    GirlItemClickCallback   girlItemClickCallback = new GirlItemClickCallback() {
        @Override
        public void onClick(GirlsData.ResultsBean fuliItem) {
            Toast.makeText(getContext(), fuliItem.getDesc(), Toast.LENGTH_SHORT).show();
        }
    };
    /**
     * 订阅数据变化来刷新UI
     * @param model
     */
    private void subscribeToModel(final GirlsViewModel model){
        //观察数据变化来刷新UI
        model.getLiveObservableData().observe(FragmentGirls.this, new Observer<GirlsData>() {
            @Override
            public void onChanged(@Nullable GirlsData girlsData) {
                Log.i(TAGUtils.LOG_TAG, "subscribeToModel onChanged onChanged");
                model.setUiObservableData(girlsData);
                girlsAdapter.setGirlsList(girlsData.getResults());
            }
        });
    }

}
