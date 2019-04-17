package com.at.kchart.charting.stockChart;

import android.content.Context;
import android.widget.TextView;

import com.at.arouter.kchart.R;
import com.at.kchart.charting.components.MarkerView;
import com.at.kchart.charting.data.Entry;
import com.at.kchart.charting.highlight.Highlight;
import com.at.kchart.charting.utils.MPPointF;
import com.at.kchart.charting.utils.NumberUtils;

public class LeftMarkerView extends MarkerView {
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *手指十字线的左边框的字
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    private TextView markerTv;
    private float num;
    private int precision;

    public LeftMarkerView(Context context, int layoutResource, int precision) {
        super(context, layoutResource);
        this.precision = precision;
        markerTv = (TextView) findViewById(R.id.marker_tv);
        markerTv.setTextSize(10);
    }

    public void setData(float num) {
        this.num = num;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        markerTv.setText(NumberUtils.keepPrecisionR(num, precision));
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
