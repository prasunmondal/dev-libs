package com.prasunmondal.dev.libs.gsheet.clients.Tests.TestBulkOps

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchByAndCondition
import com.prasunmondal.dev.libs.gsheet.clients.GSheetSerialized
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.dev.libs.gsheet.clients.responseCaching.APIRequestsTemplates

object TestSheet1Model : GSheetSerialized<ModelInsertObject>(
    ProjectConfig.dBServerScriptURL,
    ProjectConfig.DB_SHEET_ID,
    "TestSheet1",
    query = null,
    classTypeForResponseParsing = ModelInsertObject::class.java,
    appendInServer = true,
    appendInLocal = true
) {

    fun customFetchRequest(): APIRequests {
        val request = GSheetFetchByAndCondition<ModelInsertObject>()
        request.defaultInitialize(request, this)
        request.conditionAnd("name", "Prasun")
        return request
    }
}