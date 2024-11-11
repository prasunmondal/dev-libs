package com.prasunmondal.dev.libs.errorHandling

import android.content.Context
import com.prasunmondal.dev.libs.date.DateUtils
import com.prasunmondal.dev.libs.device.DeviceUtils
import java.io.Serializable

class ErrorModel(context: Context, e: Exception): Serializable {
    private var timestamp: String = DateUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss")
    private var deviceId: String = DeviceUtils.getUniqueID(context)
    private var message: String = e.message.toString()
    private var stacktrace: String = getPrettyStackTrace(e)
    private var localizedMessage: String = e.localizedMessage.toString()

    fun getPrettyStackTrace(e: Throwable): String {
        val prettyMessage = StringBuilder()
        prettyMessage.append(e.toString()).append(":\n")
        for (element in e.stackTrace) {
            prettyMessage.append("    at ")
                .append(element.className).append(".")
                .append(element.methodName)
                .append("(")
                .append(element.fileName).append(":")
                .append(element.lineNumber)
                .append(")")
                .append("")
        }
        return prettyMessage.toString()
    }
}