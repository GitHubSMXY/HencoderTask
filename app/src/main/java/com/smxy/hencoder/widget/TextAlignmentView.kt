package com.smxy.hencoder.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.smxy.hencoder.util.Util

class TextAlignmentView : View {
    var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val RADIUS: Int = Util.dip2px(150F)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.strokeWidth = 30F
        mPaint.style = Paint.Style.STROKE
        mPaint.color = 0xff94a4ad.toInt()
        canvas.drawCircle(width / 2F, height / 2F, RADIUS.toFloat(), mPaint)

        mPaint.color = 0xffeb5280.toInt()
        canvas.drawArc(
            width / 2F - RADIUS,
            height / 2F - RADIUS,
            width / 2F + RADIUS,
            height / 2F + RADIUS,
            270F,
            225F,
            false,
            mPaint
        )

        mPaint.color = 0xffff0000.toInt()
        mPaint.strokeWidth = 1F
        //参考线
        canvas.drawLine(0F,height/2F,width.toFloat(),height/2F,mPaint)

        canvas.drawLine(width/2F,0F,width/2F,height.toFloat(),mPaint)

        mPaint.color = 0xffeb5280.toInt()
        mPaint.style = Paint.Style.FILL
        mPaint.textSize = Util.dip2px(100F).toFloat()

//        var bound = Rect()
//        mPaint.getTextBounds("Logo", 0, "Logo".length, bound)
//        canvas.drawText("Logo",width/2F ,height/2F - bound.height()/2F,mPaint)

        val offset = (mPaint.fontMetrics.ascent+mPaint.fontMetrics.descent)/2
        canvas.drawText("Logo|",width/2F,height/2F - offset,mPaint)


        //辅助线
        var lineOffSet = 0F
        mPaint.strokeWidth = 1F

        mPaint.color = 0xffff0000.toInt()
        lineOffSet = mPaint.fontMetrics.top - offset
        canvas.drawLine(0F, height / 2F + lineOffSet, width.toFloat(), height / 2F + lineOffSet, mPaint)

        mPaint.color = 0xff00ff00.toInt()
        lineOffSet = mPaint.fontMetrics.ascent- offset
        canvas.drawLine(0F, height / 2F + lineOffSet, width.toFloat(), height / 2F + lineOffSet, mPaint)

        mPaint.color = 0xff0000ff.toInt()
        lineOffSet = mPaint.fontMetrics.descent- offset
        canvas.drawLine(0F, height / 2F + lineOffSet, width.toFloat(), height / 2F + lineOffSet, mPaint)

        mPaint.color = 0xffff00ff.toInt()
        lineOffSet = mPaint.fontMetrics.bottom- offset
        canvas.drawLine(0F, height / 2F + lineOffSet, width.toFloat(), height / 2F + lineOffSet, mPaint)
    }
}