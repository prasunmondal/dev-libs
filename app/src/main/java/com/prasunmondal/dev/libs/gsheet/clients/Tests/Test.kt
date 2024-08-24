package com.prasunmondal.dev.libs.gsheet.clients.Tests

import com.prasunmondal.dev.libs.gsheet.clients.Tests.CreateAPIs.InsertObjectTest
import com.prasunmondal.dev.libs.gsheet.clients.Tests.DeleteAPITests.DeleteAllTest
import com.prasunmondal.dev.libs.gsheet.clients.Tests.DeleteAPITests.DeleteByAndConditionTest
import com.prasunmondal.dev.libs.gsheet.clients.Tests.DeleteAPITests.DeleteByOrConditionTest
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.CheckData.CheckDataPresenceTest
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData.FetchAll.FetchAllTest
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData.FetchByAndConditionTest
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData.FetchByOrConditionTest
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData.FetchByQueryTest
import com.prasunmondal.dev.libs.gsheet.clients.Tests.TestBulkOps.TestBulkOps

class Test {

    companion object {

        fun start() {
//            testClearObjects()
//            testInsertObject()
//            testGetObjects()
//            testMultiple()

//        New     New     New     New     New     New     New     New     New
//            InsertObjectTest()
//            InsertUniqueObjectTest()
//            InsertStringArrayTest()
            FetchAllTest()
            FetchByQueryTest()
            FetchByAndConditionTest()
            FetchByOrConditionTest()
            DeleteAllTest()
            DeleteByAndConditionTest()
            DeleteByOrConditionTest()
            CheckDataPresenceTest()

            TestBulkOps()
//        }

//        fun testInsertObject() {
//            ModelInsertClass.saveToServer(ModelInsertObject("Prasun", "Mondal"))
//        }
//
//        fun testClearObjects() {
//            ModelInsertClass.deleteDataFromServer()
//        }
//
//        fun testGetObjects() {
//            val list = ModelInsertClass.get(false)
//            list.forEach {
//                LogMe.log(it.toString())
//            }
//        }

//        fun testMultiple() {
//            GScript.addRequest(ModelInsertClass.getGetRequest(false))
//            GScript.execute(ProjectConfig.dBServerScriptURL)
//        }
        }
    }
}