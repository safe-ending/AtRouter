package com.at.kchart.charting.interfaces.dataprovider;

import com.at.kchart.charting.components.YAxis.AxisDependency;
import com.at.kchart.charting.data.BarLineScatterCandleBubbleData;
import com.at.kchart.charting.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);

    boolean isInverted(AxisDependency axis);

    float getLowestVisibleX();

    float getHighestVisibleX();

    @Override
    BarLineScatterCandleBubbleData getData();
}
