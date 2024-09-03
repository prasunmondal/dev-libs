package com.prasunmondal.dev.libs.gsheet.clients.Tests.DeleteAPITests

import com.prasunmondal.dev.libs.contexts.AppContexts
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.DeleteAPIs.GSheetDeleteByOrCondition
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig

class DeleteByOrConditionTest {
    constructor() {
        test()
    }

    fun test() {
        val a = GSheetInsertObject(AppContexts.get())
        a.setUId("test-a-3456")
        a.sheetId(ProjectConfig.DB_SHEET_ID)
        a.tabName("TestSheet1")
        a.setDataObject(ModelInsertObject("Swagata", "Sarkar"))
        GScript.addRequest(a)

        val t = GSheetDeleteByOrCondition(AppContexts.get())
        t.setUId("test-ti87686")
        t.sheetId(ProjectConfig.DB_SHEET_ID)
        t.tabName("TestSheet1")
        t.conditionOr("name", "Swagata")
        t.conditionOr("title", "Sarkar")
        GScript.addRequest(t)

        val responses = GScript.execute(ProjectConfig.dBServerScriptURL)

        if (!(responses["test-a-3456"]!!.statusCode == 200
                    && responses["test-ti87686"]!!.statusCode == 200)
        ) {
            throw AssertionError()
        }
    }
}