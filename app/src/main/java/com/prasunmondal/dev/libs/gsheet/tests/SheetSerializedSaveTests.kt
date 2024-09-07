package com.prasunmondal.dev.libs.gsheet.tests

import com.prasunmondal.dev.libs.StringUtils.StringUtils
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData.FetchAll.FetchAllBySortingModelNoFilter
import com.prasunmondal.dev.libs.gsheet.metrics.GSheetMetrics
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

class SheetSerializedSaveTests {

    constructor() {
        resetSheet()
        insertObjectsBySave()
    }

    fun insertObjectsBySave() {
        LogMe.startMethod()
        FetchAllBySortingModelNoFilter.deleteAll().execute()
        FetchAllBySortingModelNoFilter.insert(ModelInsertObject("obj1Name", "obj1Title")).execute()

        val numberOfServerCallMadeAtStart = GSheetMetrics.callCounter
        LogMe.log("Calls made at start: $numberOfServerCallMadeAtStart")

        val objList: MutableList<ModelInsertObject> = mutableListOf()
        val obj1Name = StringUtils.generateUniqueString()
        val obj1Title = StringUtils.generateUniqueString()
        val obj2Name = StringUtils.generateUniqueString()
        val obj2Title = StringUtils.generateUniqueString()
        val obj3Name = StringUtils.generateUniqueString()
        val obj3Title = StringUtils.generateUniqueString()

        objList.add(ModelInsertObject(obj1Name, obj1Title))
        objList.add(ModelInsertObject(obj2Name, obj2Title))
        objList.add(ModelInsertObject(obj3Name, obj3Title))
        LogMe.log("list of objects created")

        FetchAllBySortingModelNoFilter.save(objList).execute()
        val numberOfServerCallMadeB4 = GSheetMetrics.callCounter
        LogMe.log("Calls made before fetch: $numberOfServerCallMadeB4")

        val newlyFetchedValue = FetchAllBySortingModelNoFilter.fetchAll().execute()
        val numberOfServerCallMadeAfter = GSheetMetrics.callCounter
        LogMe.log("Calls made before fetch: $numberOfServerCallMadeAfter")

        if(newlyFetchedValue.get(0).name == obj1Name
            && newlyFetchedValue.get(0).title == obj1Title
            && newlyFetchedValue.get(1).name == obj2Name
            && newlyFetchedValue.get(1).title == obj2Title
            && newlyFetchedValue.get(2).name == obj3Name
            && newlyFetchedValue.get(2).title == obj3Title
            && newlyFetchedValue.size == 3
            && numberOfServerCallMadeB4 == numberOfServerCallMadeAfter) {
            LogMe.log("Successful")
        } else {
            LogMe.log("Failed")
            throw Exception("Test Failed")
        }
    }

    fun resetSheet() {

    }
}