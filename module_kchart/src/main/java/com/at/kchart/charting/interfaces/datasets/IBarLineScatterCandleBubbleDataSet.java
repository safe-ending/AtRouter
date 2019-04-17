package com.at.kchart.charting.interfaces.datasets;

import com.at.kchart.charting.data.Entry;

/**
 * Created by philipp on 21/10/15.
 */
public interface IBarLineScatterCandleBubbleDataSet<T extends Entry> extends IDataSet<T> {

    /**
     * Returns the color that is used for drawing the highlight indicators.
     *
     * @return
     */
    int getHighLightColor();

    /**
     * Returns the line-width in which highlight lines are to be drawn.
     *
     * @return
     */
    float getHighlightLineWidth();
}
