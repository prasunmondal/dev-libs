package com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData.FetchAll

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.GSheetSerialized
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.dev.libs.gsheet.clients.Tests.TestBulkOps.TestSheet1Model
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

class FetchAllBySorting {

    constructor() {
        resetData()
        val t = TestSheet1Model.fetchAll(false)
        val t1 = FetchAllBySortingModel.fetchAll(false)
        LogMe.log("Start here...")
        LogMe.log(t)
        LogMe.log(t1)
    }

    fun resetData() {
        GScript.clearAll()
        val deleteRequest = FetchAllBySortingModel.prepareDeleteAllRequest()
        GScript.addRequest(deleteRequest)

        val obj1 = ModelInsertObject("FetchAllBySorting1", "FetchAllBySorting2")
        val addData1 = FetchAllBySortingModel.prepareInsertObjRequest(obj1)
        GScript.addRequest(addData1)
        val obj2 = ModelInsertObject("FetchAllBySorting3", "FetchAllBySorting4")
        val addData2 = FetchAllBySortingModel.prepareInsertObjRequest(obj2)
        GScript.addRequest(addData2)
        val obj3 = ModelInsertObject("FetchAllBySorting5", "FetchAllBySorting6")
        val addData3 = FetchAllBySortingModel.prepareInsertObjRequest(obj3)
        GScript.addRequest(addData3)
    }
}


object FetchAllBySortingModel: GSheetSerialized<ModelInsertObject>(
    ProjectConfig.dBServerScriptURL,
    ProjectConfig.DB_SHEET_ID,
    "TestSheet1",
    classTypeForResponseParsing = ModelInsertObject::class.java,
    appendInServer = true,
    appendInLocal = true,
    sort = { list: List<ModelInsertObject> -> list.filter { it.name == "Prasun" } }
) {
    fun customFetchRequest(): APIRequests {
        val request = prepareFetchByAndConditionRequest()
        request.conditionAnd("name", "Prasun")
        return request
    }
}