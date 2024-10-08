package com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData

import com.prasunmondal.dev.libs.contexts.AppContexts
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchByOrCondition
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

class FetchByOrConditionTest {
    constructor() {
        test()
    }

    fun test() {
        val t = GSheetFetchByOrCondition<ModelInsertObject>(AppContexts.get())
        t.setUId("test-tiu2t4t")
        t.sheetId(ProjectConfig.DB_SHEET_ID)
        t.tabName("TestSheet1")
        t.conditionOr("name", "Swagata")
        t.modelClass = ModelInsertObject::class.java
        GScript.addRequest(t)

        val w = GSheetFetchByOrCondition<ModelInsertObject>(AppContexts.get())
        w.setUId("test-wiew7triq")
        w.sheetId(ProjectConfig.DB_SHEET_ID)
        w.tabName("TestSheet1")
        w.conditionOr("name", "Mondal")
        w.modelClass = ModelInsertObject::class.java
        GScript.addRequest(w)

        val r = GSheetFetchByOrCondition<ModelInsertObject>(AppContexts.get())
        r.setUId("test-r2654643")
        r.sheetId(ProjectConfig.DB_SHEET_ID)
        r.tabName("TestSheet1")
        r.conditionOr("name", "Prasun")
        r.modelClass = ModelInsertObject::class.java
        GScript.addRequest(r)

        // TODO - Fix multiple AND conditions
        val z = GSheetFetchByOrCondition<ModelInsertObject>(AppContexts.get())
        z.setUId("test-z5634243")
        z.sheetId(ProjectConfig.DB_SHEET_ID)
        z.tabName("TestSheet1")
        z.conditionOr("name", "Prasun")
        z.modelClass = ModelInsertObject::class.java
        GScript.addRequest(z)

        val responses = GScript.execute(ProjectConfig.dBServerScriptURL)
        responses.forEach { key, value ->
            LogMe.log(value.toString())
        }

        if (!(responses["test-tiu2t4t"]!!.statusCode == 200
                    && responses["test-wiew7triq"]!!.statusCode == 204
                    && responses["test-r2654643"]!!.statusCode == 204)
        ) {
            throw AssertionError()
        }
    }
}