package com.prasunmondal.dev.libs.reflections.code.stacktrace

import com.prasunmondal.dev.libs.logs.LogConfigurations

object GetStacktraceDetails {

    fun className(caller: StackTraceElement): String {
        val className = caller.className
        return className.substring(className.lastIndexOf('.') + 1).substringBefore('$')
    }

    fun getLineNumber(caller: StackTraceElement): Int {
        return caller.lineNumber
    }

    fun getMethodName(caller: StackTraceElement): String {
        var methodName = caller.methodName
        if (methodName == "<init>") {
            methodName = LogConfigurations.CONSTRUCTOR_INDICATOR
        }
        return methodName
    }
}