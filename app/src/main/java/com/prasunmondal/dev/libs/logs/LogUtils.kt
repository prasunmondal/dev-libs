package com.prasunmondal.libs.logs

import com.prasunmondal.dev.libs.logs.LogConfigurations
import com.prasunmondal.libs.logs.instant.terminal.LogMe
import com.prasunmondal.libs.reflections.code.stacktrace.GetStacktraceDetails

object LogUtils {
    val caller: StackTraceElement
        get() {
            val stackTraceElements = Thread.currentThread().stackTrace
            return stackTraceElements[getCallerIndex(stackTraceElements)]
        }

    @JvmStatic
    fun getCallerIndex(stackTraceElements: Array<StackTraceElement>): Int {
        var i: Int
        var enteredLogMe = false
        i = 0
        while (i < stackTraceElements.size) {
            if (stackTraceElements[i].className.startsWith(LogMe::class.java.getPackage().name)) {
                enteredLogMe = true
            }
            if (enteredLogMe && !stackTraceElements[i].className.startsWith(LogMe::class.java.getPackage().name)) {
                break
            }
            i++
        }
        return i
    }

    @JvmStatic
    val prefix: String
        get() {
            val caller = caller
            return LogConfigurations.APP_LOGS_PREFIX + GetStacktraceDetails.className(caller) + ":" + GetStacktraceDetails.getLineNumber(
                caller
            ) + "|" + GetStacktraceDetails.getMethodName(caller) + "(): "
        }

    @JvmStatic
    fun putPrefix(str: String): String {
        var str = str
        val prefix = prefix
        str = str.replace(
            "\n", """
     
     $prefix
     """.trimIndent()
        )
        return str
    }

    fun putOffset(str: String): String {
        var str = str
        val lineOffset = lineOffset()
        str = str.replace(
            "\n", """
     
     $lineOffset
     """.trimIndent()
        )
        str = lineOffset + str
        return str
    }

    fun lineOffset(): String {
        if (!LogConfigurations.IS_LINE_OFFSET_ENABLED) {
            return ""
        }
        val str = StringBuilder()
        val logDepth = getdepth()
        for (i in 0 until logDepth) {
            str.append(LogConfigurations.OFFSET_STRING_UNIT)
        }
        return str.toString()
    }

    fun getdepth(): Int {
        val stackTraceElements = Thread.currentThread().stackTrace
        var i = getCallerIndex(stackTraceElements)
        var j = 0
        while (i < stackTraceElements.size) {
            if (stackTraceElements[i].toString().startsWith("android.")) {
                return j
            } else {
                j++
            }
            i++
        }
        return j
    }
}
