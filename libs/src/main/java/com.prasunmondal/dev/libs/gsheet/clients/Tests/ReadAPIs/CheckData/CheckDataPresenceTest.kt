package com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.CheckData

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.CheckData.GSheetCheckDataPresence
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig

class CheckDataPresenceTest {
    init {
        test()
    }

    fun test() {
        val f = GSheetInsertObject()
        f.setUId("test-t218625")
        f.sheetId(ProjectConfig.DB_SHEET_ID)
        f.tabName("TestSheet1")
        f.setDataObject(ModelInsertObject("Prasun", "Mondal"))
        GScript.addRequest(f)

        val g = GSheetInsertObject()
        g.setUId("test-t21t525")
        g.sheetId(ProjectConfig.DB_SHEET_ID)
        g.tabName("TestSheet1")
        g.setDataObject(ModelInsertObject("Dona", "Mondal"))
        GScript.addRequest(g)

        val t = GSheetCheckDataPresence()
        t.setUId("test-t34674")
        t.sheetId(ProjectConfig.DB_SHEET_ID)
        t.tabName("TestSheet1")
        t.keys("name")
        t.values("Dona")
        GScript.addRequest(t)

        val w = GSheetCheckDataPresence()
        w.setUId("test-w23456")
        w.sheetId(ProjectConfig.DB_SHEET_ID)
        w.tabName("TestSheet1")
        w.keys("name")
        w.values("NotPresent")
        GScript.addRequest(w)

        val responses = GScript.execute(ProjectConfig.dBServerScriptURL)

        if (!(responses["test-t34674"]!!.statusCode == 200
                    && responses["test-w23456"]!!.statusCode == 204)
        ) {
            throw AssertionError()
        }
    }
}