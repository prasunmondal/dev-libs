package com.prasunmondal.dev.libs.gsheet.tests

import com.prasunmondal.dev.libs.StringUtils.StringUtils
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData.FetchAll.FetchAllBySortingModelNoFilter

class GSheetTestUtils {

    companion object {

        fun resetSheetToHaveNoData() {
            FetchAllBySortingModelNoFilter.deleteAll().execute()
        }
        fun resetSheetToHaveOneDataRow(): List<ModelInsertObject> {
            FetchAllBySortingModelNoFilter.deleteAll().execute()
            return createListOfObjectByRandomValues(1)
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
    }
}