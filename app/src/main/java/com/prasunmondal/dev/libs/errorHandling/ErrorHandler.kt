package com.prasunmondal.dev.libs.errorHandling

import android.content.Context
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.CreateAPIs
import com.prasunmondal.dev.libs.gsheet.clients.GSheetSerialized

class ErrorHandler: GSheetSerialized<ErrorModel>(
    context = null!!,
    scriptURL = "",
    sheetId = "",
    tabName = "appConstants",
    modelClass = ErrorModel::class.java
){
    fun reportError(context: Context, sheetId: String, tabName: String, error: Throwable) {
        val str = """
                    < EXCEPTION START >
                    Exception: $error
                    Message: ${error.message}
                    --------------- Stacktrace ---------------
                    ${getStackTrace(error as Exception)}< EXCEPTION END >
                    """.trimIndent()

        val requestObj = insert(ErrorModel(str)).getRequestObj()[0]
        requestObj.context = context
        (requestObj as CreateAPIs).sheetId = sheetId
        requestObj.tabName = tabName
        requestObj.execute()
    }

    private fun getStackTrace(ex: Exception): String? {
        val sb = StringBuilder()
        val st = ex.stackTrace
        sb.append(ex.javaClass.name).append(": ").append(ex.message).append("\n")
        for (stackTraceElement in st) {
            sb.append("\t at ").append(stackTraceElement.toString()).append("\n")
        }
        return sb.toString()
    }
}