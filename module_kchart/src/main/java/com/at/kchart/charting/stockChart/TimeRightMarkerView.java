package com.at.kchart.charting.stockChart;

import android.content.Context;
import android.widget.TextView;

import com.at.arouter.kchart.R;
import com.at.kchart.charting.components.MarkerView;
import com.at.kchart.charting.data.Entry;
import com.at.kchart.charting.highlight.Highlight;
import com.at.kchart.charting.utils.MPPointF;

import java.text.DecimalFormat;

public class TimeRightMarkerView extends MarkerView {
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    private TextView markerTv;
    private float num;
    private DecimalFormat mFormat;

    public TimeRightMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mFormat = new DecimalFormat("#0.00");
        markerTv = (TextView) findViewById(R.id.marker_tv);
        markerTv.setTextSize(10);

    }

    public void setData(float num) {
        this.num = num;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        markerTv.setText(mFormat.format(num * 100) + "%");
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

}
