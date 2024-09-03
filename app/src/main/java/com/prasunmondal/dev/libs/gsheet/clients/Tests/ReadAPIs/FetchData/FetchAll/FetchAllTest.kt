package com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData.FetchAll

import com.prasunmondal.dev.libs.contexts.AppContexts
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.DeleteAPIs.GSheetDeleteAll
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchAll
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig

class FetchAllTest {
    constructor() {
        FetchAllBySorting()
//        test()
    }

    fun testByCallingClassesGetMethod() {
//        TestSheet1Model.fetchAll()
    }

    fun test() {
        val e = GSheetDeleteAll(AppContexts.get())
        e.setUId("test-e.234")
        e.sheetId(ProjectConfig.DB_SHEET_ID)
        e.tabName("TestSheet1")
        GScript.addRequest(e)

        val f = GSheetInsertObject(AppContexts.get())
        f.setUId("test-t218625")
        f.sheetId(ProjectConfig.DB_SHEET_ID)
        f.tabName("TestSheet1")
        f.setDataObject(ModelInsertObject("Prasun", "Mondal"))
        GScript.addRequest(f)

        val d = GSheetInsertObject(AppContexts.get())
        d.setUId("test-d218625")
        d.sheetId(ProjectConfig.DB_SHEET_ID)
        d.tabName("TestSheet1")
        d.setDataObject(ModelInsertObject("Dona", "Mondal"))
        GScript.addRequest(d)

        val t = GSheetFetchAll<ModelInsertObject>(AppContexts.get())
        t.setUId("test-ti35uy2t")
        t.sheetId(ProjectConfig.DB_SHEET_ID)
        t.tabName("TestSheet1")
        t.classTypeForResponseParsing = ModelInsertObject::class.java
        GScript.addRequest(t)

        val w = GSheetFetchAll<ModelInsertObject>(AppContexts.get())
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