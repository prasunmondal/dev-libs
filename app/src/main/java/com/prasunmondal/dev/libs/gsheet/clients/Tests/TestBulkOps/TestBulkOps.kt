package com.prasunmondal.dev.libs.gsheet.clients.Tests.TestBulkOps

import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

class TestBulkOps {
    init {
        test()
    }

    fun test() {
        LogMe.startMethod()
//        GScript.addRequest(TestSheet1Model.prepareFetchAllRequest())
//        GScript.addRequest(TestSheet1Model.customFetchRequest())
////        GScript.addRequest(TestSheet2Model.prepareFetchAllRequest())
//        GScript.execute(ProjectConfig.dBServerScriptURL)
        LogMe.log(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ")
        TestSheet1Model.fetchAll()
        TestSheet1Model.fetchAll()
        TestSheet1Model.fetchAll()
        TestSheet1Model.fetchAll()

//        TestSheet1Model.insertObject(ModelInsertObject("swagata","jerry"))
//        var t = TestSheet1Model.fetchAll(false)
//        t.forEach {
//            LogMe.log(t.toString())
//        }
//        t = TestSheet1Model.fetchAll()
//        t.forEach {
//            LogMe.log(t.toString())
//        }

    }
}