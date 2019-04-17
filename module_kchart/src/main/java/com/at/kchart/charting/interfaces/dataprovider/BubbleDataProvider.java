package com.at.kchart.charting.interfaces.dataprovider;

import com.at.kchart.charting.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
