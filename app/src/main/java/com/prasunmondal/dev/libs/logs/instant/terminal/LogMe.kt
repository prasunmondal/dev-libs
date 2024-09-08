package com.prasunmondal.dev.libs.logs.instant.terminal

import com.prasunmondal.dev.libs.contexts.AppContexts
import com.prasunmondal.dev.libs.device.DeviceUtils
import com.prasunmondal.dev.libs.logs.LogConfigurations
import com.prasunmondal.dev.libs.logs.LogUtils
import com.prasunmondal.dev.libs.logs.instant.sheets.LogToSheet

object LogMe : LogExceptions() {
    private var obj: LogMe? = null

    init {
        obj = LogMe
    }

    fun log(msg: Int) {
        if(!logEnabled()) return
        log("" + msg)
    }

    fun <T> log(list: List<T>) {
        if(!logEnabled()) return
        list.forEach {
            log(it.toString())
        }
    }

    @JvmStatic
    fun log(msg: String?) {
        if(!logEnabled()) return

        var msg_ = msg
        if (msg_ == null) {
            msg_ = "<null string detected in log>"
        }
        log(msg_, true, false)
    }

    @JvmStatic
    fun log(msg: String, postLogToSheet: Boolean) {
        if(!logEnabled()) return
        log(msg, true, postLogToSheet)
    }

    fun toSheet(msg: String) {
        if(!logEnabled()) return
        log(msg, true, true)
    }

    fun startMethod() {
        if(!logEnabled()) return
        log(LogConfigurations.METHOD_START_INDICATOR)
    }

    fun endMethod() {
        if(!logEnabled()) return
        log(LogConfigurations.METHOD_END_INDICATOR)
    }

    @JvmStatic
    fun log(msg: String, addPrefix: Boolean, postLogToSheet: Boolean) {
        if(!logEnabled()) return
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
            LogToSheet.logs.post(str, AppContexts.get())
        }
    }

    fun logEnabled(): Boolean {
        return DeviceUtils.isEmulator
    }
}
