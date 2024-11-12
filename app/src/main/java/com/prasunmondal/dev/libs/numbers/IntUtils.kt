package com.prasunmondal.dev.libs.numbers

interface IntUtils {
    fun getIntOrZero(input: String?): Int {
        return getIntOrN(input, 0)
    }

    fun getIntOrN(input: String?, n: Int): Int {
        return try {
            input!!.toInt()
        } catch (e: Exception) {
            n
        }
    }

    fun getIntOrBlank(input: String): String {
        val t = getIntOrN(input, 0)
        return if (t == 0) "" else t.toString()
    }
}