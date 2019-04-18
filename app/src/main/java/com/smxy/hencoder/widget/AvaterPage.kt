package com.smxy.hencoder.widget

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.smxy.hencoder.R

class AvaterPage: View {
    lateinit var mCamera: Camera
    var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    lateinit var mBitmap:Bitmap
    var IMAGE_WIDTH = 600F
    val PADDING = 200F
    var mRotation:Float = 0.0f

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context,attr:AttributeSet) : super(context,attr) {
        mCamera = Camera()
        mCamera.setLocation(0F, 0F, -10 * Resources.getSystem().displayMetrics.density)
        mCamera.rotateX(45F)

        val animator = ValueAnimator.ofFloat(0F, 360F)
        animator.addUpdateListener {
            mRotation = it.animatedValue as Float
            invalidate()
        }
        animator.duration = 3000
        animator.startDelay = 2000
        animator.repeatMode = ValueAnimator.REVERSE
        animator.repeatCount = ValueAnimator.INFINITE
        animator.start()


    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        IMAGE_WIDTH = width - PADDING * 2
        mBitmap = getAvatar(IMAGE_WIDTH)
    }

    fun getAvatar(width:Float):Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.demo,options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width.toInt()
        return BitmapFactory.decodeResource(resources, R.mipmap.demo,options)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val translateX = PADDING + IMAGE_WIDTH / 2
        val translateY = PADDING + IMAGE_WIDTH / 2

        canvas.save()
        canvas.translate(translateX, translateY)
        canvas.rotate(-mRotation)
        canvas.clipRect(-IMAGE_WIDTH,-IMAGE_WIDTH,IMAGE_WIDTH,0F)
        canvas.rotate(mRotation)
        canvas.translate(-translateX, -translateY)
        canvas.drawBitmap(mBitmap,PADDING,PADDING,mPaint)
        canvas.restore()

        canvas.save()
        canvas.translate(translateX, translateY)
        canvas.rotate(-mRotation)
        mCamera.applyToCanvas(canvas)
        canvas.clipRect(-IMAGE_WIDTH,0F,IMAGE_WIDTH,IMAGE_WIDTH)
        canvas.rotate(mRotation)
        canvas.translate(-translateX,-translateY)
        canvas.drawBitmap(mBitmap,PADDING,PADDING,mPaint)
        canvas.restore()
    }

}