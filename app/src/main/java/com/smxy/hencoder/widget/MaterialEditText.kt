package com.smxy.hencoder.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.smxy.hencoder.R
import com.smxy.hencoder.util.Util

/**
 * @author huangkangqiang
 * @name MaterialEditText
 * @description
 * @date 2019/4/29
 */
class MaterialEditText : AppCompatEditText {
    private val S_TEXT_SIZE = Util.dip2px(12F)
    private val S_TEXT_MARGIN = Util.dip2px(8F)
    private val S_VERTICAL_OFFSET = Util.dip2px(22F)
    private val S_HORIZONTAL_OFFSET = Util.dip2px(5F)

    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mTitle: String
    private var mIsDisplayTitle: Boolean = false
    private val mTitleDisappearTop = S_VERTICAL_OFFSET + Util.dip2px(20F)

    private var mObjectAnimator: ObjectAnimator = ObjectAnimator.ofFloat(
        this,
        "animValue",
        0F, 1F
    )

    var animValue: Float = S_VERTICAL_OFFSET
        get() = field
        set(value) {
            field = value
            mPaint.alpha = (value * 0xff).toInt()
            invalidate()
        }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MaterialEditText, 0, 0)
        val string = typedArray.getString(R.styleable.MaterialEditText_met_title)
        mTitle = if (string.isEmpty()) "" else string
        typedArray.recycle()

        setPadding(paddingLeft, (paddingTop + S_TEXT_SIZE + S_TEXT_MARGIN).toInt(), paddingRight, paddingBottom)

        mObjectAnimator.duration = 200

        mPaint.textSize = S_TEXT_SIZE
        mPaint.color = 0xff629755.toInt()

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val isDisplayTitle = text.toString().isNotEmpty()
                if (mIsDisplayTitle != isDisplayTitle) {
                    mIsDisplayTitle = isDisplayTitle

                    if (mIsDisplayTitle) mObjectAnimator.start() else mObjectAnimator.reverse()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        animValue = if (mIsDisplayTitle) 1F else 0F
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(mTitle, S_HORIZONTAL_OFFSET, mTitleDisappearTop - Util.dip2px(20F) * animValue, mPaint)
    }
}