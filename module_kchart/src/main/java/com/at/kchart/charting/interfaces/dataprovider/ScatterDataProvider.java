package com.at.kchart.charting.interfaces.dataprovider;

import com.at.kchart.charting.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
