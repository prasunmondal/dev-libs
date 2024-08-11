package com.prasunmondal.dev.libs.TestUtils

import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

class TestUtils {

    companion object {

        fun assert(expectedValue: Int, actualValue: Int, log: Boolean = true): Boolean {
            LogMe.log("Expected Value: $expectedValue, ActualValue: $actualValue")
            return expectedValue == actualValue
        }
    }
}