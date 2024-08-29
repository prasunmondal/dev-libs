package com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData.FetchAll

import com.prasunmondal.dev.libs.contexts.AppContexts
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequestsQueue
import com.prasunmondal.dev.libs.gsheet.clients.ClientFilter
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.GSheetSerialized
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

class FetchAllBySorting {

    init {
        resetData()
        LogMe.log("Starting Data fetch - 1.")

        val t = FetchAllBySortingModel.fetchAll().execute()

        LogMe.log("Completed Data fetch - 1.")
        LogMe.log("Starting Data fetch - 2.")
        val r = FetchAllBySortingModel.fetchAll().execute()
        LogMe.log("Completed Data fetch - 2.")
        t.forEach { item ->
            LogMe.log("Fetched.........")
            (LogMe.log((item).name))
            (LogMe.log((item).title))
        }

//        LogMe.log("Starting Data fetch - 2.")
//        val t1 = FetchAllBySortingModel.fetch(false)
//        LogMe.log("Completed Data fetch - 2.")
//        LogMe.log("Logging the data - 1.")
//        LogMe.log(t)
//        LogMe.log("Logging the data - 2.")
//        LogMe.log(t1)
    }

    fun resetData() {
        LogMe.log("Resetting Data")
        GScript.clearAll()
//        val deleteRequest = FetchAllBySortingModel.deleteAll().prepareRequest()
//        GScript.addRequest(deleteRequest)
        FetchAllBySortingModel.deleteAll().execute()
        val obj1 = ModelInsertObject("FetchAllBySorting1", "FetchAllBySorting2")
        val obj2 = ModelInsertObject("FetchAllBySorting3", "FetchAllBySorting4")
        val obj3 = ModelInsertObject("FetchAllBySorting5", "FetchAllBySorting6")
        val obj4 = ModelInsertObject("Prasun", "Mondal1")
        val obj5 = ModelInsertObject("Prasun", "Mondal2")

        var requestQueue1=APIRequestsQueue()
        FetchAllBySortingModel.insert(obj1).queue(requestQueue1)
        FetchAllBySortingModel.insert(obj2).queue()
        FetchAllBySortingModel.insert(obj3).queue()
        FetchAllBySortingModel.insert(obj4).queue()
        FetchAllBySortingModel.insert(obj5).execute()
        GScript.execute(ProjectConfig.dBServerScriptURL)

        var request=FetchAllBySortingModel.insert(obj1).getRequestObj()
        requestQueue1.addRequest(request)
        requestQueue1.addRequest("id6",request)
        requestQueue1.execute()
        LogMe.log("Reset Completed.")
    }
}

object FetchAllBySortingModel: GSheetSerialized<ModelInsertObject>(
    AppContexts.get(),
    ProjectConfig.dBServerScriptURL,
    ProjectConfig.DB_SHEET_ID,
    "TestSheet1",
    classTypeForResponseParsing = ModelInsertObject::class.java,
    appendInServer = true,
    appendInLocal = true,
    filter = ClientFilter("filterWithNamePrasun", { list: List<ModelInsertObject> -> list.filter { it.name == "Prasun" } })
)