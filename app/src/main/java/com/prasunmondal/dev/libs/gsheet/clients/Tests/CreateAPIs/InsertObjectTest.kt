package com.prasunmondal.dev.libs.gsheet.clients.Tests.CreateAPIs

import com.prasunmondal.dev.libs.TestUtils.TestUtils
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.DeleteAPIs.GSheetDeleteAll
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig

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

        if (!(TestUtils.assert(responses["test-e.234"]!!.statusCode / 100, 2)
                    && TestUtils.assert(responses["test-t218625"]!!.statusCode, 200)
                    && TestUtils.assert(responses["test-d218625"]!!.statusCode, 200)
                    && TestUtils.assert(responses["test-w8324545"]!!.statusCode, 500))
        ) {
            throw AssertionError()
        }
    }
}