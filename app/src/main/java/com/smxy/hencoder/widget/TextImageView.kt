package com.smxy.hencoder.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.smxy.hencoder.R
import com.smxy.hencoder.util.Util

class TextImageView : View {

    var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val mText = "When Pharaoh came to Jerusalem to speak with Solomon, " +
            "I was called to stand behind KingSolomon. " +
            "I was listening to their conversation when finally they came to a point. " +
            "Solomon had made an alliance or a so-called treaty of peace by marrying Pharaoh’s daughter. " +
            "I was at their wedding too. " +
            "I soon overheard about how King Solomon was making a temple to the one and only God above. " +
            "I was sent to work at the masonry, " +
            "because I was once a stone mason " +
            "and they are short of masons so they are pulling everybody " +
            "with rock carving experience to them asonry. " +
            "At least I don’t have to worked like those labour slaves have to. " +
            "They are worked until they are almost dead. It looks like they never stop working. " +
            "I feel that those labour."

    var mBitmap: Bitmap

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        mPaint.color = 0xff000000.toInt()
        mPaint.textSize = 32F
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.demo,options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        mBitmap = BitmapFactory.decodeResource(resources, R.mipmap.demo,options)
    }

     var mTextArray: FloatArray = FloatArray(1)
    val bitmapTop = Util.dip2px(30F).toFloat()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        val bitmapLeft = (width - mBitmap.width).toFloat()
        canvas.drawBitmap(mBitmap, bitmapLeft, bitmapTop, mPaint)

        var endCount: Int
        var line = 0
        var startCount = 0
        var textRowWidth: Float
        var spacing:Float
        while (startCount < mText.length) {
            line++
            spacing = mPaint.fontSpacing * line

            if (spacing > bitmapTop && spacing < bitmapTop + mBitmap.height) {
                textRowWidth = (width - mBitmap.width).toFloat()
            } else {
                textRowWidth = width.toFloat()
            }


            endCount = mPaint.breakText(mText,startCount,mText.length,true,textRowWidth,mTextArray)
            canvas.drawText(mText,startCount,startCount + endCount,0F,spacing,mPaint)
            startCount += endCount
        }
    }
}