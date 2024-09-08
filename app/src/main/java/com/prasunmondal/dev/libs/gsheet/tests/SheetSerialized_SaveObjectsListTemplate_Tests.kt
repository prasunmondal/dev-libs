package com.prasunmondal.dev.libs.gsheet.tests

import com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData.FetchAll.FetchAllBySortingModelNoFilter
import com.prasunmondal.dev.libs.gsheet.metrics.GSheetMetrics
import com.prasunmondal.dev.libs.gsheet.metrics.GSheetMetricsState
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

class SheetSerialized_SaveObjectsListTemplate_Tests {

    constructor() {
        saveListOfObjects()
        saveObjectsOneByOne()
    }

    fun saveListOfObjects() {
        val numberOfSaveObj = 3
        LogMe.startMethod()
        GSheetTestUtils.resetSheetToHaveOneDataRow()

        val numberOfServerCallMadeAtStart = GSheetMetrics.callCounter
        LogMe.log("Calls made at start: $numberOfServerCallMadeAtStart")

        val generatedList = GSheetTestUtils.createListOfObjectByRandomValues(numberOfSaveObj)

        val metricsB4Save = GSheetMetricsState.getState()
        FetchAllBySortingModelNoFilter.save(generatedList).execute()
        val metricsAfterSave = GSheetMetricsState.getState()
        val fetchedList = FetchAllBySortingModelNoFilter.fetchAll().execute()
        val metricsAfterFetch = GSheetMetricsState.getState()

        if(GSheetTestUtils.areIdentical(generatedList, fetchedList)
            && fetchedList.size == numberOfSaveObj
            && GSheetTestUtils.isMetricsExpected(metricsB4Save, metricsAfterSave, 1, 2+numberOfSaveObj,2+numberOfSaveObj)) {
            LogMe.log("Successful")
        } else {
            LogMe.log("Failed")
            throw Exception("Test Failed")
        }
    }

    fun saveObjectsOneByOne() {
        LogMe.startMethod()
        val previousPresentData = GSheetTestUtils.resetSheetToHaveOneDataRow()

        GSheetTestUtils.createObjectByRandomValues()

        val numberOfServerCallMadeAtStart = GSheetMetrics.callCounter
        LogMe.log("Calls made at start: $numberOfServerCallMadeAtStart")

        val obj1 = GSheetTestUtils.createObjectByRandomValues()
        FetchAllBySortingModelNoFilter.save(obj1).execute()
        val numberOfServerCallMadeB4 = GSheetMetrics.callCounter
        LogMe.log("Calls made before fetch: $numberOfServerCallMadeB4")

        val fetchedList = FetchAllBySortingModelNoFilter.fetchAll().execute()
        val numberOfServerCallMadeAfter = GSheetMetrics.callCounter
        LogMe.log("Calls made before fetch: $numberOfServerCallMadeAfter")

        if(GSheetTestUtils.areIdentical(listOf(obj1), fetchedList)
            && fetchedList.size == 1
            && numberOfServerCallMadeB4 == numberOfServerCallMadeAfter) {
            LogMe.log("Successful")
        } else {
            LogMe.log("Failed")
            throw Exception("Test Failed")
        }
    }
}