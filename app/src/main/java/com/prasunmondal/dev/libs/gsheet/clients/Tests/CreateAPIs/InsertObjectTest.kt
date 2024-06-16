package com.prasunmondal.libs.gsheet.clients.Tests.CreateAPIs

import com.prasunmondal.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.libs.gsheet.clients.APIRequests.DeleteAPIs.GSheetDeleteAll
import com.prasunmondal.libs.gsheet.clients.GScript
import com.prasunmondal.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.libs.logs.instant.terminal.LogMe

class InsertObjectTest {

    constructor() {
        test()
    }

    fun test() {
        val e = GSheetDeleteAll()
        e.setUId("test-e.234")
        e.sheetId(ProjectConfig.DB_SHEET_ID)
        e.tabName("TestSheet1")
        GScript.addRequest(e)

        val t = GSheetInsertObject()
        t.setUId("test-t218625")
        t.sheetId(ProjectConfig.DB_SHEET_ID)
        t.tabName("TestSheet1")
        t.setDataObject(ModelInsertObject("Prasun", "Mondal"))
        GScript.addRequest(t)

        val d = GSheetInsertObject()
        d.setUId("test-d218625")
        d.sheetId(ProjectConfig.DB_SHEET_ID)
        d.tabName("TestSheet1")
        d.setDataObject(ModelInsertObject("Dona", "Mondal"))
        GScript.addRequest(d)

        // Should fail due to incorrect Sheet Name
        val w = GSheetInsertObject()
        w.setUId("test-w8324545")
        w.sheetId(ProjectConfig.DB_SHEET_ID)
        w.tabName("Sheet3")
        w.setDataObject(ModelInsertObject("Prasun", "Mondal"))
        GScript.addRequest(w)

        val responses = GScript.execute(ProjectConfig.dBServerScriptURL)

        LogMe.log((responses["test-e.234"]!!.statusCode % 100 == 2).toString())
        if (!(responses["test-e.234"]!!.statusCode / 100 == 2
                    && responses["test-t218625"]!!.statusCode == 200
                    && responses["test-d218625"]!!.statusCode == 200
                    && responses["test-w8324545"]!!.statusCode == 500)
        ) {
            throw AssertionError()
        }
    }
}