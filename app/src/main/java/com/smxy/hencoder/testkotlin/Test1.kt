package com.smxy.hencoder.testkotlin

/**
 * @author huangkangqiang
 * @name Test
 * @description
 * @date 2019/4/30
 */
class Test {
    private fun main() {
        var isBoolean = true
        if (System.currentTimeMillis() == 11L) {
            isBoolean = false
        }
        val test = if (isBoolean) 1 else 2
    }
}
