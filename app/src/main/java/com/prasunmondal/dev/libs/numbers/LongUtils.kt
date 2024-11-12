package com.prasunmondal.dev.libs.numbers

interface LongUtils {
    fun getLongOrZero(input: String?): Long {
        return getLongOrN(input, 0)
    }

    fun getLongOrN(input: String?, n: Long): Long {
        return try {
            input!!.toLong()
        } catch (e: Exception) {
            n
        }
    }

    fun getLongOrBlank(input: String): String {
        val t = getLongOrN(input, 0)
        return if (t == 0L) "" else t.toString()
    }
}