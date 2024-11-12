package com.prasunmondal.dev.libs.numbers

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

interface DoubleUtils {

    fun getDoubleOrN(input: String, n: Double): Double {
        return try {
            input.toDouble()
        } catch (e: Exception) {
            n
        }
    }

    fun getDoubleOrZero(input: String): Double {
        return getDoubleOrN(input, 0.0)
    }

    fun getDoubleOrBlank(input: String): String {
        val t = getDoubleOrZero(input)
        if (t == 0.0)
            return ""
        return t.toString()
    }

    fun getDoubleOrZero(input: String, roundOffPattern: String): Double {
        val number = getDoubleOrZero(input)
        return try {
            val df = DecimalFormat(roundOffPattern)
            df.roundingMode = RoundingMode.FLOOR
            df.format(number).toDouble()
        } catch (e: Exception) {
            number
        }
    }

    fun roundOff(number: Double, digitsAfterDecimal: Int): Double {
        return BigDecimal(number).setScale(digitsAfterDecimal, RoundingMode.HALF_UP).toDouble()
    }
}