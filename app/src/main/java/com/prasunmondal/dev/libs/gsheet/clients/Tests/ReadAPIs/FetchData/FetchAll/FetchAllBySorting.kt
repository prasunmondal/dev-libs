package com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData.FetchAll

import com.prasunmondal.dev.libs.contexts.AppContexts
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.ClientFilter
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.GSheetSerialized
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.dev.libs.gsheet.clients.Tests.TestBulkOps.TestSheet1Model
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

class FetchAllBySorting {

    init {
        resetData()
        LogMe.log("Starting Data fetch - 1.")
        val t = TestSheet1Model.fetch(false)
        LogMe.log("Completed Data fetch - 1.")
        LogMe.log("Starting Data fetch - 2.")
        val t1 = FetchAllBySortingModel.fetch(false)
        LogMe.log("Completed Data fetch - 2.")
        LogMe.log("Logging the data - 1.")
        LogMe.log(t)
        LogMe.log("Logging the data - 2.")
        LogMe.log(t1)
    }

    fun resetData() {
        LogMe.log("Resetting Data")
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
        GScript.execute(ProjectConfig.dBServerScriptURL)
        LogMe.log("Reset Completed.")
    }
}

object FetchAllBySortingModel : GSheetSerialized<ModelInsertObject>(
    AppContexts.get(),
    ProjectConfig.dBServerScriptURL,
    ProjectConfig.DB_SHEET_ID,
    "TestSheet1",
    classTypeForResponseParsing = ModelInsertObject::class.java,
    appendInServer = true,
    appendInLocal = true,
    filter = ClientFilter("filterWithNamePrasun", { list: List<ModelInsertObject> -> list.filter { it.name == "Prasun" } })
) {
    fun customFetchRequest(): APIRequests {
        val request = prepareFetchByAndConditionRequest()
        request.conditionAnd("name", "Prasun")
        return request
    }
}