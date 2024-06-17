package com.prasunmondal.dev.libs.gsheet.clients.Tests.CreateAPIs

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertUniqueObject
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig

class InsertUniqueObjectTest {

    constructor() {
        test()
    }

    fun test() {
        val t = GSheetInsertUniqueObject()
        t.setUId("test-t7569286")
        t.sheetId(ProjectConfig.DB_SHEET_ID)
        t.tabName("TestSheet1")
        t.setDataObject(ModelInsertObject("dgbnv", "skjbvkj"))
        t.uniqueColumn("name")
        GScript.addRequest(t)

        val w = GSheetInsertUniqueObject()
        w.setUId("test-wi2u5ytf")
        w.sheetId(ProjectConfig.DB_SHEET_ID)
        w.tabName("Sheet3")
        w.setDataObject(ModelInsertObject("Dona", "Mondal"))
        w.uniqueColumn("name")
        GScript.addRequest(w)

        val responses = GScript.execute(ProjectConfig.dBServerScriptURL)

        if (!(responses["test-t7569286"]!!.statusCode == 200
                    && responses["test-wi2u5ytf"]!!.statusCode == 400)
        ) {
            throw AssertionError()
        }
    }
}