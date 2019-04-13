package com.smxy.hencoder.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.smxy.hencoder.R

/**
 * @author huangkangqiang
 * @name ScaleView
 * @description
 * @date 2019/4/13
 */
class ScaleView2 : View {
    private val WRAP_WIDTH = 400
    private val WRAP_HEIGHT = 400

    private var mPaint: Paint = Paint()
    private val DASH_WIDTH = 20F
    private val LINE_WIDTH = 2F
    private val MAX_SCALE = 10
    private lateinit var mRect: RectF
    private var MAX_PROGRESS = 100
    private var mProgress = 0
    private var SWEEP_ANGLE = 240F
    private var SCALE_WIDTH = 4
    private var POINT_LENGTH = 100
    private var POINT_WIDTH = 4

    private val START_ANGLE: Float = 360 - (90 + SWEEP_ANGLE / 2)

    private lateinit var dashPathEffect: PathDashPathEffect

    private var mArcLength:Float = 0.0f


    constructor(mContext: Context, attr: AttributeSet) : super(mContext, attr) {
        val typedArray = mContext.obtainStyledAttributes(attr, R.styleable.ScaleView, 0, 0)
        mProgress = typedArray.getInt(R.styleable.ScaleView_sv_progress, 0)
        MAX_PROGRESS = typedArray.getInt(R.styleable.ScaleView_sv_max, 100)
        SWEEP_ANGLE = typedArray.getFloat(R.styleable.ScaleView_sv_sweep_angle, 240F)
        POINT_LENGTH = typedArray.getDimensionPixelSize(R.styleable.ScaleView_sv_pointer_length, 100)
        POINT_WIDTH = typedArray.getDimensionPixelSize(R.styleable.ScaleView_sv_pointer_width, 4)
        SCALE_WIDTH = typedArray.getDimensionPixelSize(R.styleable.ScaleView_sv_scale_width, 4)
        typedArray.recycle()
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
        mRect = RectF(0F, 0F, minSize, minSize)


        //长度
        val path = Path()
        path.addArc(mRect.left ,
            mRect.top,
            mRect.right ,
            mRect.bottom,
            START_ANGLE,
            SWEEP_ANGLE)

        val pathMeasure = PathMeasure(path, false)
        mArcLength = pathMeasure.length

        val dash = Path()
        dash.addRect(0F, 0F, SCALE_WIDTH.toFloat(), DASH_WIDTH, Path.Direction.CCW)
        dashPathEffect = PathDashPathEffect(dash, (mArcLength - SCALE_WIDTH) / MAX_SCALE, 0F, PathDashPathEffect.Style.ROTATE)
    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeCap = Paint.Cap.BUTT

        mPaint.strokeWidth = LINE_WIDTH
        mPaint.pathEffect = null
        //draw outline
        canvas.drawArc(
            mRect.left + LINE_WIDTH / 2,
            mRect.top + LINE_WIDTH / 2,
            mRect.right - LINE_WIDTH / 2,
            mRect.bottom - LINE_WIDTH / 2,
            START_ANGLE,
            SWEEP_ANGLE,
            false,
            mPaint
        )

        //draw scale
        mPaint.strokeWidth = DASH_WIDTH

        //dotted line setting
        mPaint.pathEffect = dashPathEffect
        canvas.drawArc(
            mRect.left ,
            mRect.top,
            mRect.right,
            mRect.bottom,
            START_ANGLE,
            SWEEP_ANGLE,
            false,
            mPaint
        )

        //draw pointer
        mPaint.pathEffect = null
        mPaint.strokeWidth = POINT_WIDTH.toFloat()
        val angle = START_ANGLE + mProgress * SWEEP_ANGLE / MAX_PROGRESS
        mPaint.strokeCap = Paint.Cap.ROUND
        canvas.drawLine(
            mRect.centerX(),
            mRect.centerY(),
            (mRect.centerX() + Math.cos(Math.PI / 180 * angle) * POINT_LENGTH).toFloat(),
            (mRect.centerY() + Math.sin(Math.PI / 180 * angle) * POINT_LENGTH).toFloat(),
            mPaint
        )
    }
}