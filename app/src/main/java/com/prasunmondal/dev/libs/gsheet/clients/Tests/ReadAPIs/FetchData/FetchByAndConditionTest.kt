package com.prasunmondal.libs.gsheet.clients.Tests.ReadAPIs.FetchData

import com.prasunmondal.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchByAndCondition
import com.prasunmondal.libs.gsheet.clients.GScript
import com.prasunmondal.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.libs.logs.instant.terminal.LogMe

class FetchByAndConditionTest {
    constructor() {
        test()
    }

    fun test() {
        val a = GSheetInsertObject()
        a.setUId("test-a-3456")
        a.sheetId(ProjectConfig.DB_SHEET_ID)
        a.tabName("TestSheet1")
        a.setDataObject(ModelInsertObject("Swagata", "Mondal"))
        GScript.addRequest(a)

        val b = GSheetInsertObject()
        b.setUId("test-a-3458")
        b.sheetId(ProjectConfig.DB_SHEET_ID)
        b.tabName("TestSheet1")
        b.setDataObject(ModelInsertObject("Dona", "Mondal"))
        GScript.addRequest(b)

        val t = GSheetFetchByAndCondition<ModelInsertObject>()
        t.setUId("test-tiu2t4t")
        t.sheetId(ProjectConfig.DB_SHEET_ID)
        t.tabName("TestSheet1")
        t.conditionAnd("name", "Swagata")
        t.classTypeForResponseParsing = ModelInsertObject::class.java
        GScript.addRequest(t)

        val w = GSheetFetchByAndCondition<ModelInsertObject>()
        w.setUId("test-wiew7triq")
        w.sheetId(ProjectConfig.DB_SHEET_ID)
        w.tabName("TestSheet1")
        w.conditionAnd("name", "Mondal")
        w.classTypeForResponseParsing = ModelInsertObject::class.java
        GScript.addRequest(w)

        val r = GSheetFetchByAndCondition<ModelInsertObject>()
        r.setUId("test-r2654643")
        r.sheetId(ProjectConfig.DB_SHEET_ID)
        r.tabName("TestSheet1")
        r.conditionAnd("name", "Prasun")
        r.classTypeForResponseParsing = ModelInsertObject::class.java
        GScript.addRequest(r)

        // TODO - Fix multiple AND conditions
        val z = GSheetFetchByAndCondition<ModelInsertObject>()
        z.setUId("test-z5634243")
        z.sheetId(ProjectConfig.DB_SHEET_ID)
        z.tabName("TestSheet1")
        z.conditionAnd("name", "Prasun")
        z.classTypeForResponseParsing = ModelInsertObject::class.java
        GScript.addRequest(z)

        val responses = GScript.execute(ProjectConfig.dBServerScriptURL)
        responses.forEach { key, value ->
            LogMe.log(value.toString())
        }

        if (!(responses["test-tiu2t4t"]!!.statusCode == 200
                    && responses["test-wiew7triq"]!!.statusCode == 204
                    && responses["test-r2654643"]!!.statusCode == 200)
        ) {
            throw AssertionError()
        }
    }
}