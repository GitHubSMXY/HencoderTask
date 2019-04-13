package com.smxy.hencoder.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * @author huangkangqiang
 * @name PieGraphView
 * @description 饼图
 * @date 2019/4/13
 */
class PieGraphView : View {
    companion object {
        private val WRAP_WIDTH = 400
        private val WRAP_HEIGHT = 400
        private val PULL_WIDTH = 10F
    }

    private var mPaint: Paint = Paint()
    private val mData = intArrayOf(45, 45, 45, 45, 45, 45, 45, 45)

    private val COLORS = intArrayOf(
        0xffff8e01.toInt(),
        0xff009788.toInt(),
        0xffc2175b.toInt(),
        0xff2879fe.toInt(),
        0xffff8e01.toInt(),
        0xff009788.toInt(),
        0xffc2175b.toInt(),
        0xff2879fe.toInt()
    )
    private var mTranslateX: Float = 0.0f
    private var mTranslateY: Float = 0.0f
    private lateinit var mRect: RectF

    private var mTouchDownX: Float = 0.0f
    private var mTouchDownY: Float = 0.0f

    private var mFocusDataIndex = 0


    constructor(mContext: Context, attrs: AttributeSet) : super(mContext, attrs) {
        mPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val minSpecSize = Math.min(widthSpecSize, heightSpecSize)

        if (widthSpecMode == View.MeasureSpec.AT_MOST && heightSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(WRAP_WIDTH, WRAP_HEIGHT)
        } else if (widthSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(WRAP_WIDTH, minSpecSize)
        } else if (heightSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(minSpecSize, WRAP_HEIGHT)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val minSize = Math.min(width.toFloat(), height.toFloat())
        val PADDING = 10F
        mRect = RectF(PADDING, PADDING, minSize - PADDING, minSize - PADDING)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mTouchDownX = event.x
                mTouchDownY = event.y
                //计算是否落在区域
                val degrees = Math.toDegrees(Math.atan(((mTouchDownY - mRect.centerY()) / (mTouchDownX - mRect.centerX())).toDouble()))
                Log.d("AA", "degree:" + degrees)
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var startAngle = 0F
        var endAngle: Float
        var startNum = 0
        for (index in mData.indices) {
            startNum += mData[index]
            endAngle = startNum * 360F / mData.sum()
            if (index == mFocusDataIndex) {
                canvas.save()
                mTranslateX = Math.cos(Math.toRadians(((startAngle + endAngle) / 2).toDouble())).toFloat() * PULL_WIDTH
                mTranslateY = Math.sin(Math.toRadians(((startAngle + endAngle) / 2).toDouble())).toFloat() * PULL_WIDTH
                canvas.translate(mTranslateX, mTranslateY)
            }
            mPaint.color = COLORS[index]
            canvas.drawArc(mRect, startAngle, endAngle - startAngle, true, mPaint)
            startAngle = endAngle
            if (index == mFocusDataIndex) {
                canvas.restore()
            }
        }
    }

}