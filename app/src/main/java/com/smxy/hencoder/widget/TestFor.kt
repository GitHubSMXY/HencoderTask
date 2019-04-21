package com.smxy.hencoder.widget

class TestFor {
    internal var mText =
        "public static float adjustTvTextSize(TextView tv, int maxWidth, String text) {" + "public static float adjustTvTextSize(TextView tv, int maxWidth, String text) {"

    internal fun test() {
        var count = 0
        while (count < mText.length) {
            count += mText.length
            count++
        }
    }
}
