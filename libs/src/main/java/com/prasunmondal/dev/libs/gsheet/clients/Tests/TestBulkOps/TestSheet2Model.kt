package com.prasunmondal.dev.libs.gsheet.clients.Tests.TestBulkOps

import com.prasunmondal.dev.libs.gsheet.clients.GSheetSerialized
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.dev.libs.gsheet.clients.responseCaching.APIRequestsTemplates

object TestSheet2Model : GSheetSerialized<ModelInsertObject>(
    ProjectConfig.dBServerScriptURL,
    ProjectConfig.DB_SHEET_ID,
    "TestSheet2",
    query = null,
    classTypeForResponseParsing = ModelInsertObject::class.java,
    appendInServer = true,
    appendInLocal = true
) {

}