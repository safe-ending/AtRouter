package com.at.kchart.charting.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public abstract class BaseFragment extends Fragment {
    public Activity mAct;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAct = activity;
    }

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            } else {
                updateIndex();
            }
        } else {
            view = inflater.inflate(setLayoutId(), container, false);
            initBase(view, savedInstanceState);
        }
        return view;
    }


    protected abstract int setLayoutId();

    public abstract void updateIndex();

    protected abstract void initBase(View view, Bundle savedInstanceState);

}
