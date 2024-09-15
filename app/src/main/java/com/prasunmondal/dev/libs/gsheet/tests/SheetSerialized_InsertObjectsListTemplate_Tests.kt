package com.prasunmondal.dev.libs.gsheet.tests

import com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData.FetchAll.FetchAllBySortingModelNoFilter
import com.prasunmondal.dev.libs.gsheet.metrics.GSheetMetrics
import com.prasunmondal.dev.libs.gsheet.metrics.GSheetMetricsState
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

class SheetSerialized_InsertObjectsListTemplate_Tests {
    init {
        insertListOfObjects()
        insertObjectsOneByOne()
    }

    private fun insertListOfObjects() {
        val numberOfSaveObj = 3
        LogMe.startMethod()
        val initialListInServer = GSheetTestUtils.resetSheetToHaveNDataRows(1)

        val numberOfServerCallMadeAtStart = GSheetMetrics.callCounter
        LogMe.log("Calls made at start: $numberOfServerCallMadeAtStart")

        val generatedList = GSheetTestUtils.createListOfObjectByRandomValues(numberOfSaveObj)

        val metricsB4Save = GSheetMetricsState.getState()
        FetchAllBySortingModelNoFilter.insert(generatedList).execute()
        val metricsAfterSave = GSheetMetricsState.getState()
        val fetchedList = FetchAllBySortingModelNoFilter.fetchAll().execute()
        val metricsAfterFetch = GSheetMetricsState.getState()
        val serverList = initialListInServer + generatedList
        if(GSheetTestUtils.areIdentical(serverList, fetchedList)
            && fetchedList.size == numberOfSaveObj + 1
            && GSheetTestUtils.isMetricsExpected(metricsB4Save, metricsAfterSave, 1, 2,2)
            && GSheetTestUtils.isMetricsExpected(metricsAfterSave, metricsAfterFetch, 0, 0,0)) {
            LogMe.log("Successful")
        } else {
            LogMe.log("Failed")
            throw Exception("Test Failed")
        }
    }

    private fun insertObjectsOneByOne() {
        val numberOfSaveObj = 1
        LogMe.startMethod()
        val initialListInServer = GSheetTestUtils.resetSheetToHaveNDataRows(3)

        val numberOfServerCallMadeAtStart = GSheetMetrics.callCounter
        LogMe.log("Calls made at start: $numberOfServerCallMadeAtStart")

        val generatedList = GSheetTestUtils.createListOfObjectByRandomValues(numberOfSaveObj)[0]

        val metricsB4Save = GSheetMetricsState.getState()
        FetchAllBySortingModelNoFilter.insert(generatedList).execute()
        val metricsAfterSave = GSheetMetricsState.getState()
        val fetchedList = FetchAllBySortingModelNoFilter.fetchAll().execute()
        val metricsAfterFetch = GSheetMetricsState.getState()
        val serverList = initialListInServer + generatedList
        if(GSheetTestUtils.areIdentical(serverList, fetchedList)
            && fetchedList.size == numberOfSaveObj + 3
            && GSheetTestUtils.isMetricsExpected(metricsB4Save, metricsAfterSave, 1, 1+numberOfSaveObj,1+numberOfSaveObj)
            && GSheetTestUtils.isMetricsExpected(metricsAfterSave, metricsAfterFetch, 0, 0,0)) {
            LogMe.log("Successful")
        } else {
            LogMe.log("Failed")
            throw Exception("Test Failed")
        }
    }
}