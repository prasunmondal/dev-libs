package com.prasunmondal.dev.libs.gsheet.tests

import com.prasunmondal.dev.libs.StringUtils.StringUtils
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData.FetchAll.FetchAllBySortingModelNoFilter
import com.prasunmondal.dev.libs.gsheet.metrics.GSheetMetricsState
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

class GSheetTestUtils {

    companion object {

        fun resetSheetToHaveNoData() {
            FetchAllBySortingModelNoFilter.deleteAll().execute()
        }
        fun resetSheetToHaveOneDataRow(): List<ModelInsertObject> {
            FetchAllBySortingModelNoFilter.deleteAll().execute()
            val list = createListOfObjectByRandomValues(1)
            FetchAllBySortingModelNoFilter.save(list).execute()
            return list
        }

        fun createObjectByRandomValues(): ModelInsertObject {
            return ModelInsertObject(StringUtils.generateUniqueString(), StringUtils.generateUniqueString())
        }

        fun createListOfObjectByRandomValues(numberOfObjs: Int): List<ModelInsertObject> {
            var list: MutableList<ModelInsertObject> = mutableListOf()
            for (i in 1..numberOfObjs) {
                list.add(ModelInsertObject(StringUtils.generateUniqueString(), StringUtils.generateUniqueString()))
            }
            return list
        }

        fun areIdentical(list1: List<ModelInsertObject>, list2: List<ModelInsertObject>, ignoreOrder: Boolean = false): Boolean {
            val result: Boolean = if(ignoreOrder) {
                list1.sortedBy { it.name }.zip(list2.sortedBy { it.name }).all { (p1, p2) ->
                    p1.name == p2.name && p1.title == p2.title }
            } else {
                list1.size == list2.size && list1.zip(list2).all { (p1, p2) ->
                    p1.name == p2.name && p1.title == p2.title }
            }
            return result
        }

        fun isMetricsExpected(initial: GSheetMetricsState, final: GSheetMetricsState, incrementInCallCounter: Int, incrementInRequestsQueued: Int, incrementInRequestsProcessed: Int, metricsName: String = ""): Boolean {
//            LogMe.log("Expected $metricsName Metrics $initial + $increment = $final".replace("  "," "))
//            LogMe.log("Actual $metricsName Metrics $initial + ${final - initial} = $final".replace("  "," "))

            LogMe.log("Call Counter:: Expected: ${initial.callCounter} + $incrementInCallCounter = ${initial.callCounter + incrementInCallCounter}, Actual: ${initial.callCounter} + $incrementInCallCounter = ${final.callCounter}")
            LogMe.log("Requests Queued:: Expected: ${initial.requestsQueued} + $incrementInCallCounter = ${initial.requestsQueued + incrementInCallCounter}, Actual: ${initial.requestsQueued} + $incrementInCallCounter = ${final.requestsQueued}")
            LogMe.log("Requests Processed:: Expected: ${initial.requestsProcessed} + $incrementInCallCounter = ${initial.requestsProcessed + incrementInCallCounter}, Actual: ${initial.requestsProcessed} + $incrementInCallCounter = ${final.requestsProcessed}")
            val r1 = (initial.callCounter + incrementInCallCounter) == final.callCounter
            val r2 = (initial.requestsQueued + incrementInRequestsQueued) == final.requestsQueued
            val r3 = (initial.requestsProcessed + incrementInRequestsProcessed) == final.requestsProcessed
            return r1 && r2 && r3
        }
    }
}