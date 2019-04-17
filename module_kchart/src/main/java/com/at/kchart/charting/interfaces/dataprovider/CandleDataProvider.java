package com.at.kchart.charting.interfaces.dataprovider;

import com.at.kchart.charting.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
