package com.at.arouter.common.listener;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.DrawableRes;

import com.at.arouter.common.R;
import com.at.arouter.common.data.Constants;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.io.Serializable;

/**
 * desc:  BaseDaDataHandler
 * author:  yangtao
 * <p>
 * creat: 2018/8/19 19:05
 */
public abstract class BaseDataHandler
        extends BaseObservable {

    public ObservableField<UIConfig> uiConfig = new ObservableField<>(new UIConfig());

    public BaseDataHandler() {
    }

    public void save(Bundle outState) {
        outState.putParcelable(Constants.SAVED_STATE_DATA_HANDLER, Parcels.wrap(this));
    }
    /**
     * UI相关的配置
     * 如:
     * 标题栏
     * loading文字
     * 数据为空的文字
     */
    public static class UIConfig extends BaseObservable implements Serializable {

        public boolean showTitle = true;
        public boolean showNoData = false;
        public boolean showLoading = false;
        public boolean showRightButton1 = false;
        public boolean showRightButton2 = false;
        public boolean showRightButton3 = false;
        public boolean showRightButton4 = false;
        public boolean showLlEditText = false;
        public boolean showLeftButton1 = true;
        public boolean showLeftButton2 = false;
        public boolean showTitleRightImg = false;
        public String title = "";
        public String noDataText = "";
        public String loadingText = "";
        public String backTitle = "";

        public UIConfig() {
        }

        public UIConfig(Context context) {
            title = "默认标题";
            backTitle = "返回";
            Resources resources = context.getResources();
            noDataText = (resources.getString(R.string.no_data));
            loadingText = (resources.getString(R.string.loading));
        }

        public void setShowTitleRightImg(boolean isShow) {
            this.showTitleRightImg = isShow;
            notifyChange();
        }

        public void setLeftButton2Title(String backTitle) {
            this.backTitle = backTitle;
            notifyChange();
        }

        public void setShowLeftButton2(boolean isShow) {
            this.showLeftButton2 = isShow;
            notifyChange();
        }

        public void setShowLeftButton1(boolean isShow) {
            this.showLeftButton1 = isShow;
            notifyChange();
        }

        public void setShowTitle(boolean showTitle) {
            this.showTitle = showTitle;
            notifyChange();
        }

        public void setLoadingText(String loadingText) {
            this.loadingText = loadingText;
            notifyChange();
        }

        public void setNoDataText(String noDataText) {
            this.noDataText = noDataText;
            notifyChange();
        }

        public void setTitle(String title) {
            this.title = title;
            notifyChange();
        }

        public void setShowLoading(boolean showLoading) {
            this.showLoading = showLoading;
            notifyChange();
        }

        public void setShowNoData(boolean showNoData) {
            this.showNoData = showNoData;
            notifyChange();
        }

        public void setShowRightButton1(boolean isShow) {
            this.showRightButton1 = isShow;
            notifyChange();
        }

        public void setShowRightButton2(boolean isShow) {
            this.showRightButton2 = isShow;
            notifyChange();
        }

        public void setShowRightButton3(boolean isShow) {
            this.showRightButton3 = isShow;
            notifyChange();
        }

        public void setShowRightButton4(boolean isShow) {
            this.showRightButton4 = isShow;
            notifyChange();
        }

        public void setShowLlEditText(boolean isShow) {
            this.showLlEditText = isShow;
            notifyChange();
        }

        @Parcel
        public static class HeaderButton {
            public
            @DrawableRes
            int drawableRes;

            public HeaderButton() {
            }

            public HeaderButton(int drawableRes) {
                this.drawableRes = drawableRes;
            }
        }
    }
}
