package com.prasunmondal.dev.libs.gsheet.clients.Tests.TestBulkOps

import com.prasunmondal.dev.libs.contexts.AppContexts
import com.prasunmondal.dev.libs.gsheet.clients.GSheetSerialized
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig

object TestSheet1Model : GSheetSerialized<ModelInsertObject>(
//    AppContexts.get(),
    ProjectConfig.dBServerScriptURL,
    ProjectConfig.DB_SHEET_ID,
    "TestSheet1",
    classTypeForResponseParsing = ModelInsertObject::class.java,
    appendInServer = true,
    appendInLocal = true
) {
//    fun customFetchRequest(): APIRequests {
//        val request = prepareFetchByAndConditionRequest()
//        request.conditionAnd("name", "Prasun")
//        return request
//    }
}