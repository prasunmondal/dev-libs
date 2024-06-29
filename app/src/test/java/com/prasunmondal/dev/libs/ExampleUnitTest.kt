package com.prasunmondal.dev.libs

import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        LogMe.log("Prasun running the unit tests here.")
        assertEquals(4, 2 + 2)
    }

    @Test
    fun addition_isCorrect2() {
        LogMe.log("Prasun running the unit tests here - 2.")
        assertEquals(4, 2 + 2)
    }
}