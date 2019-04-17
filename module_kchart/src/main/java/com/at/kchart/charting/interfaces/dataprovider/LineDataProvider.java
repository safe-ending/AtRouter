package com.at.kchart.charting.interfaces.dataprovider;

import com.at.kchart.charting.components.YAxis;
import com.at.kchart.charting.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
