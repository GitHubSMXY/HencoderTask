package com.smxy.hencoder.util

import com.smxy.hencoder.MyApplication

class Util {
    companion object {
        fun dip2px(dpValue: Float): Float {
            val scale = MyApplication.context.getResources().getDisplayMetrics().density
            return dpValue * scale + 0.5f
        }
    }
}