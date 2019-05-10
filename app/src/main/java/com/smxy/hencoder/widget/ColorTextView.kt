package com.smxy.hencoder.widget

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.smxy.hencoder.util.Util

/**
 * @author huangkangqiang
 * @name ColorTextView
 * @description
 * @date 2019/5/10
 */
class ColorTextView : AppCompatTextView {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        textSize = arrayOf(Util.dip2px(6F), Util.dip2px(12F), Util.dip2px(18F)).random()
        text = arrayOf("北京", "上海", "广州", "深圳").random()
        setBackgroundColor(arrayOf(0xffefc20d,0xff8e43ac,0xff16a086).random().toInt())
    }
}