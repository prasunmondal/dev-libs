package com.prasunmondal.dev.libs.gsheet.clients

import com.prasunmondal.dev.libs.gsheet.caching.createApis.InsertAPIsTemplate
import com.prasunmondal.dev.libs.gsheet.caching.deleteApis.DeleteAPIsTemplate
import com.prasunmondal.dev.libs.gsheet.caching.readApis.ReadAPIsTemplate

open class GSheetSerialized<T>(
    override var scriptURL: String,
    override var sheetURL: String,
    override var tabname: String,
    override var query: String? = null,
    override var classTypeForResponseParsing: Class<T>,
    override var appendInServer: Boolean,
    override var appendInLocal: Boolean,
    override var getEmptyListIfEmpty: Boolean = false,
    override var cacheTag: String = "default"
) : ReadAPIsTemplate<T>, DeleteAPIsTemplate<T>, InsertAPIsTemplate<T> {

//    fun saveToLocal(dataObject: Any?, cacheKey: String? = getFilterName()) {
//        var finalCacheKey = cacheKey
//        if(cacheKey == null) {
//            finalCacheKey = getFilterName()
//        }
//        LogMe.log("Expensive Operation - saving data to local: $finalCacheKey")
//        if (finalCacheKey == null) {
//            finalCacheKey = getFilterName()
//        }
//        if (dataObject == null) {
//            CentralCache.put(finalCacheKey, dataObject)
//            return
//        }
//
//        val dataToSave = if (appendInLocal) {
//            var dataList = get() as ArrayList
//            dataList.addAll(arrayListOf(dataObject as T))
//            dataList = filterResults(dataList)
//            dataList = sortResults(dataList)
//            dataList
//        } else {
//            dataObject
//        }
//        CentralCache.put(finalCacheKey, dataToSave)
//    }
}