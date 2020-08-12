
/**
 * @author yolo.huang
 * @date 2020/8/11
 */
@file:JvmName("Converter")
package com.qihoo.kids.allinonedemo.bindadapter

import kotlin.math.round

fun fromIntToString(time:Int):String{
    return if(time<600){
        String.format("%.1f", time / 10.0)
    }else{
        val min = (time/10)/60
        val seconds = (time/10)%60
        String.format("%d:%02d",min,seconds)
    }
}

fun cleanStringToSecond(time: String):Int{
    val filteredValue = time.replace(Regex("""[^\d:.]"""),"")
    if(filteredValue.isEmpty()) return 0
    val elements: List<Int> = filteredValue.split(":").map { it -> round(it.toDouble()).toInt() }

    var result: Int
    return when {
        elements.size > 2 -> 0
        elements.size > 1 -> {
            result = elements[0] * 60
            result += elements[1]
            result * 10
        }
        else -> elements[0] * 10
    }
}
