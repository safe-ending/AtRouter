package com.at.kchart.charting.renderer.scatter;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.at.kchart.charting.utils.ViewPortHandler;
import com.at.kchart.charting.interfaces.datasets.IScatterDataSet;
import com.at.kchart.charting.utils.Utils;

/**
 * Created by wajdic on 15/06/2016.
 * Created at Time 09:08
 */
public class XShapeRenderer implements IShapeRenderer {


    @Override
    public void renderShape(Canvas c, IScatterDataSet dataSet, ViewPortHandler viewPortHandler,
                            float posX, float posY, Paint renderPaint) {

        final float shapeHalf = dataSet.getScatterShapeSize() / 2f;

        renderPaint.setStyle(Paint.Style.STROKE);
        renderPaint.setStrokeWidth(Utils.convertDpToPixel(1f));

        c.drawLine(
                posX - shapeHalf,
                posY - shapeHalf,
                posX + shapeHalf,
                posY + shapeHalf,
                renderPaint);
        c.drawLine(
                posX + shapeHalf,
                posY - shapeHalf,
                posX - shapeHalf,
                posY + shapeHalf,
                renderPaint);

    }

}