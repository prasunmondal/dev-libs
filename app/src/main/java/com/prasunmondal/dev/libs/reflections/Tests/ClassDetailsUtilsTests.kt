package com.prasunmondal.libs.reflections.Tests

import com.prasunmondal.libs.logs.instant.terminal.LogMe
import com.prasunmondal.libs.reflections.code.current.ClassDetailsUtils

class ClassDetailsUtilsTests {

    constructor() {
        test()
    }

    private fun test() {
        getClassNameTest1()
        getCallerClassTests1()
        getCallerClassTests2()
    }

    fun getClassNameTest1() {
//        val className = ClassDetailsUtils.getClassName(ClassDetailsUtilsTests)
//        LogMe.log(className)
    }

    fun getCallerClassTests1() {
        val className = ClassDetailsUtils.getCaller()
        LogMe.log(className)
        assert("com.prasunmondal.libs.reflections.Tests.ClassDetailsUtilsTests" == className)
    }

    companion object {

        fun getCallerClassTests2() {
            val className = ClassDetailsUtils.getCaller()
            LogMe.log(className)
            assert("com.prasunmondal.libs.reflections.Tests.ClassDetailsUtilsTests" == className)
        }
    }
}