package com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData

import com.prasunmondal.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.libs.gsheet.clients.APIRequests.DeleteAPIs.GSheetDeleteAll
import com.prasunmondal.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchAll
import com.prasunmondal.libs.gsheet.clients.GScript
import com.prasunmondal.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.libs.gsheet.clients.Tests.ProjectConfig

class FetchAllTest {
    constructor() {
        test()
    }

    fun test() {
        val e = GSheetDeleteAll()
        e.setUId("test-e.234")
        e.sheetId(ProjectConfig.DB_SHEET_ID)
        e.tabName("TestSheet1")
        GScript.addRequest(e)

        val f = GSheetInsertObject()
        f.setUId("test-t218625")
        f.sheetId(ProjectConfig.DB_SHEET_ID)
        f.tabName("TestSheet1")
        f.setDataObject(ModelInsertObject("Prasun", "Mondal"))
        GScript.addRequest(f)

        val d = GSheetInsertObject()
        d.setUId("test-d218625")
        d.sheetId(ProjectConfig.DB_SHEET_ID)
        d.tabName("TestSheet1")
        d.setDataObject(ModelInsertObject("Dona", "Mondal"))
        GScript.addRequest(d)

        val t = GSheetFetchAll<ModelInsertObject>()
        t.setUId("test-ti35uy2t")
        t.sheetId(ProjectConfig.DB_SHEET_ID)
        t.tabName("TestSheet1")
        t.classTypeForResponseParsing = ModelInsertObject::class.java
        GScript.addRequest(t)

        val w = GSheetFetchAll<ModelInsertObject>()
        w.setUId("test-w245ueyt")
        w.sheetId(ProjectConfig.DB_SHEET_ID)
        w.tabName("TestSheet1")
        w.classTypeForResponseParsing = ModelInsertObject::class.java
        GScript.addRequest(w)

        val responses = GScript.execute(ProjectConfig.dBServerScriptURL)

        if (!(responses["test-ti35uy2t"]!!.statusCode == 200
                    && responses["test-w245ueyt"]!!.statusCode == 200)
        ) {
            throw AssertionError()
        }
    }
}