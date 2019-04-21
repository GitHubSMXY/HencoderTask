package com.smxy.hencoder.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.smxy.hencoder.R

class RoundImageView : View {

    var mBitmap:Bitmap
    val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val BITMAP_WIDTH = 300F
    val BITMAP_LEFT = 100F
    val BITMAP_TOP = 100F
    val mXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet){
        mBitmap = getBitmap(BITMAP_WIDTH)
        mPaint.strokeWidth = 6F
        mPaint.color = 0xff8bb256.toInt()
    }



    override fun onDraw(canvas: Canvas) {
        mPaint.style = Paint.Style.FILL
        val saveLayer =
            canvas.saveLayer(BITMAP_LEFT, BITMAP_TOP, BITMAP_LEFT + mBitmap.width, BITMAP_TOP + mBitmap.height, mPaint)
        canvas.drawCircle(BITMAP_LEFT + mBitmap.width / 2, BITMAP_TOP + mBitmap.height / 2,mBitmap.width/2F, mPaint)
        mPaint.xfermode = mXfermode
        canvas.drawBitmap(mBitmap, BITMAP_LEFT, BITMAP_TOP, mPaint)
        mPaint.xfermode = null
        canvas.restoreToCount(saveLayer)
        mPaint.style = Paint.Style.STROKE
        canvas.drawCircle(BITMAP_LEFT + mBitmap.width / 2, BITMAP_TOP + mBitmap.height / 2,mBitmap.width/2F, mPaint)
    }

    fun getBitmap(width:Float): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.demo,options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width.toInt()
        return BitmapFactory.decodeResource(resources, R.mipmap.demo,options)
    }
}