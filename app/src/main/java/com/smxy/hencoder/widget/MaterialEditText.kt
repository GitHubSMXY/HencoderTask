package com.smxy.hencoder.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import com.smxy.hencoder.R
import com.smxy.hencoder.util.Util

/**
 * @author huangkangqiang
 * @name MaterialEditText
 * @description
 * @date 2019/4/29
 */
class MaterialEditText : AppCompatEditText {
    val S_TEXT_SIZE = Util.dip2px(12F)
    val S_TEXT_MARGIN = Util.dip2px(8F)
    val S_VERTICAL_OFFSET = Util.dip2px(22F)
    val S_HORIZONTAL_OFFSET = Util.dip2px(5F)


    var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mTitle: String
    private var mIsDisplayTitle:Boolean = false

    var titleTop: Float = S_VERTICAL_OFFSET
        get() = field
        set(value) {
            field = value
            invalidate()
        }


    fun getAnimator(isFloatDown:Boolean):ObjectAnimator {
        val floatDown = S_VERTICAL_OFFSET + Util.dip2px(20F)

        val objectAnimator = ObjectAnimator.ofFloat(
            this,
            "titleTop",
            if (isFloatDown) S_VERTICAL_OFFSET else floatDown,
            if (!isFloatDown) S_VERTICAL_OFFSET else floatDown
        )
        objectAnimator.duration = 500

        return objectAnimator
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MaterialEditText, 0, 0)
        val string = typedArray.getString(R.styleable.MaterialEditText_met_title)
        if (string != null) {
            mTitle = string
        } else {
            mTitle = ""
        }

        typedArray.recycle()




        mPaint.textSize = S_TEXT_SIZE
        mPaint.color = 0xff629755.toInt()
        setPadding(paddingLeft, (paddingTop + S_TEXT_SIZE + S_TEXT_MARGIN).toInt(), paddingRight, paddingBottom)

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val isDispTitle = text.toString().isNotEmpty()
                if (mIsDisplayTitle != isDispTitle) {
                    mIsDisplayTitle = isDispTitle
                    changeTitle()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("aax", s.toString() + "-start:" + start + "-beford:" + before + "-count:" + count)
            }
        })
        changeTitle()
    }

    fun changeTitle() {
        val animator = getAnimator(mIsDisplayTitle)
        animator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(mTitle, S_HORIZONTAL_OFFSET, titleTop, mPaint)
    }
}