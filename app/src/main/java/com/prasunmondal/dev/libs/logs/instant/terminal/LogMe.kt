package com.prasunmondal.dev.libs.logs.instant.terminal

import com.prasunmondal.dev.libs.logs.LogConfigurations
import com.prasunmondal.dev.libs.logs.instant.sheets.LogToSheet
import com.prasunmondal.dev.libs.logs.instant.terminal.LogExceptions
import com.prasunmondal.dev.libs.contexts.AppContexts.Companion.get
import com.prasunmondal.dev.libs.logs.LogUtils

object LogMe : LogExceptions() {
    var obj: LogMe? = null

    init {
        obj = LogMe
    }

    fun log(msg: Int) {
        log("" + msg)
    }

    @JvmStatic
    fun log(msg: String?) {
        var msg = msg
        if (msg == null) {
            msg = "<null string detected in log>"
        }
        log(msg, true, false)
    }

    @JvmStatic
    fun log(msg: String, postLogToSheet: Boolean) {
        log(msg, true, postLogToSheet)
    }

    fun toSheet(msg: String) {
        log(msg, true, true)
    }

    fun startMethod() {
        log(LogConfigurations.METHOD_START_INDICATOR)
    }

    fun endMethod() {
        log(LogConfigurations.METHOD_END_INDICATOR)
    }

    @JvmStatic
    fun log(msg: String, addPrefix: Boolean, postLogToSheet: Boolean) {
        var str: String = msg
        if (addPrefix) {
            val prefixString = LogUtils.prefix
            str = prefixString + msg.replace(
                "\n", """
     
     $prefixString
     """.trimIndent()
            )
        }
        str = LogUtils.putOffset(str)
        println(str)
        if (postLogToSheet) {
            LogToSheet.logs.post(str, get())
        }
    }
}
