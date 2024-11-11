package com.prasunmondal.dev.libs.errorHandling

import android.content.Context
import com.prasunmondal.dev.libs.contexts.AppContexts
import com.prasunmondal.dev.libs.gsheet.ContextWrapper
import com.prasunmondal.dev.libs.gsheet.clients.GSheetSerialized
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

object ErrorHandler: GSheetSerialized<ErrorModel> (
    context = ContextWrapper(AppContexts.get()),
    sheetId = "",
    tabName = "errors",
    modelClass = ErrorModel::class.java
) {
    fun registerErrorLogger(context: Context, sheetId: String, tabname: String) {
        Thread.setDefaultUncaughtExceptionHandler { thread, e ->
            try {
                LogMe.log("Logging error to sheet")
                val errorObj = ErrorModel(context, e as Exception)
                val t = ErrorHandler.insert(errorObj)
                t.context = context
                t.sheetId = sheetId
                t.tabname = tabname
                t.execute()
                throw e
            } catch (e: Exception) {
                LogMe.log("Failed to write error to server")
                LogMe.log(e.message)
                LogMe.log(e.stackTraceToString())
            }
        }
    }
}