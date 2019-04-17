package com.at.kchart.charting.stockChart;

import com.at.kchart.charting.animation.ChartAnimator;
import com.at.kchart.charting.charts.CombinedChart;
import com.at.kchart.charting.renderer.CandleStickChartRenderer;
import com.at.kchart.charting.renderer.CombinedChartRenderer;
import com.at.kchart.charting.renderer.LineChartRenderer;
import com.at.kchart.charting.renderer.ScatterChartRenderer;
import com.at.kchart.charting.utils.ViewPortHandler;
import com.at.kchart.charting.renderer.BubbleChartRenderer;

/**
 * Created by ly on 2018/3/15.
 */

public class MyCombinedChartRenderer extends CombinedChartRenderer {

    public MyCombinedChartRenderer(CombinedChart chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    public void createRenderers() {

        mRenderers.clear();

        CombinedChart chart = (CombinedChart) mChart.get();
        if (chart == null) {
            return;
        }

        CombinedChart.DrawOrder[] orders = chart.getDrawOrder();

        for (CombinedChart.DrawOrder order : orders) {

            switch (order) {
                case BAR:
                    if (chart.getBarData() != null) {
                        mRenderers.add(new TimeBarChartRenderer(chart, mAnimator, mViewPortHandler));
                    }
                    break;
                case BUBBLE:
                    if (chart.getBubbleData() != null) {
                        mRenderers.add(new BubbleChartRenderer(chart, mAnimator, mViewPortHandler));
                    }
                    break;
                case LINE:
                    if (chart.getLineData() != null) {
                        mRenderers.add(new LineChartRenderer(chart, mAnimator, mViewPortHandler));
                    }
                    break;
                case CANDLE:
                    if (chart.getCandleData() != null) {
                        mRenderers.add(new CandleStickChartRenderer(chart, mAnimator, mViewPortHandler));
                    }
                    break;
                case SCATTER:
                    if (chart.getScatterData() != null) {
                        mRenderers.add(new ScatterChartRenderer(chart, mAnimator, mViewPortHandler));
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
