package com.smxy.hencoder.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * @author huangkangqiang
 * @name TagLayout
 * @description
 * @date 2019/5/10
 */
class TagLayout(context: Context, attributeSet: AttributeSet) : ViewGroup(context, attributeSet) {
    val mChildBounds = ArrayList<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        var widthUsed = 0
        var heightUsed = 0
        var lineWidthUsed = 0
        var lineHeightUsed = 0
        for (i in 0..(childCount-1)) {
            val child = getChildAt(i)

            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)

            if (widthMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + child.measuredWidth > widthSize) {
                lineWidthUsed = 0
                heightUsed += lineHeightUsed
                lineHeightUsed = 0
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }


            lateinit var rect:Rect
            if (i <= mChildBounds.size) {
                rect = Rect()
                mChildBounds.add(rect)
            } else {
                rect = mChildBounds[i]
            }

            rect.set(lineWidthUsed, heightUsed, lineWidthUsed + child.measuredWidth, heightUsed + child.measuredHeight)
            lineWidthUsed += child.measuredWidth
            widthUsed = Math.max(lineWidthUsed, widthUsed)
            lineHeightUsed = Math.max(lineHeightUsed,child.measuredHeight)
        }
        val myWidthMeasure = widthUsed
        val myHeightMeasure = Math.max(heightUsed, lineHeightUsed)


        setMeasuredDimension(myWidthMeasure, myHeightMeasure)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0..(childCount-1)) {
            val view = getChildAt(i)
            val rect = mChildBounds[i]
            view.layout(rect.left, rect.top, rect.right, rect.bottom)
        }
    }
}