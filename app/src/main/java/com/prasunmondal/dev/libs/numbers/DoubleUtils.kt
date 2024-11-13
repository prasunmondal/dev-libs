package com.prasunmondal.dev.libs.numbers

import java.math.BigDecimal
import java.math.RoundingMode

interface DoubleUtils {

    fun getDoubleOrN(input: String, digitsAfterDecimal: Int = -1, n: Double): Double {
        return try {
            roundOff(input.toDouble(), digitsAfterDecimal)
        } catch (e: Exception) {
            n
        }
    }

    fun getDoubleOrBlank(input: String, digitsAfterDecimal: Int = -1): String {
        val t = getDoubleOrZero(input)
        if (t == 0.0)
            return ""
        return roundOff(t, digitsAfterDecimal).toString()
    }

    fun getDoubleOrZero(input: String, digitsAfterDecimal: Int = -1): Double {
        return getDoubleOrN(input, digitsAfterDecimal, 0.0)
    }

    fun roundOff(number: Double, digitsAfterDecimal: Int = -1): Double {
        if(number.isNaN())
            return 0.0
        if(digitsAfterDecimal < 0)
            return number
        return BigDecimal(number).setScale(digitsAfterDecimal, RoundingMode.HALF_UP).toDouble()
    }
}