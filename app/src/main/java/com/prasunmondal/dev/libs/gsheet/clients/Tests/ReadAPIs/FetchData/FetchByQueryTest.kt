package com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchByQuery
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig

class FetchByQueryTest {
    constructor() {
        test()
    }

    fun test() {
        val t = GSheetFetchByQuery<ModelInsertObject>()
        t.setUId("test-83567t")
        t.sheetId(ProjectConfig.DB_SHEET_ID)
        t.tabName("TestSheet1")
        t.query("=QUERY(IMPORTRANGE(\"https://docs.google.com/spreadsheets/d/1p3v4SgXPfB70YjCXCOj57BdLrDiFBoynt7yIWPQ8WmI\",\"sheet2!A1:Az\"), \"select *\")")
        t.classTypeForResponseParsing = ModelInsertObject::class.java
        GScript.addRequest(t)

        val responses = GScript.execute(ProjectConfig.dBServerScriptURL)

        if (!(responses["test-83567t"]!!.statusCode == 200
                    && responses["test-83567t"]!!.statusCode == 200)
        ) {
            throw AssertionError()
        }
    }
}