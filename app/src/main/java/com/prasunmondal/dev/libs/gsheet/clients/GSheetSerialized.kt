package com.prasunmondal.dev.libs.gsheet.clients

import com.prasunmondal.dev.libs.gsheet.clients.responseCaching.APIRequestsTemplates

open class GSheetSerialized<T>: APIRequestsTemplates<T> {

    constructor(
        scriptURL: String,
        sheetURL: String,
        tabname: String,
        query: String? = null,
        classTypeForResponseParsing: Class<T>,
        appendInServer: Boolean,
        appendInLocal: Boolean,
        getEmptyListIfNoResultsFoundInServer: Boolean = false,
        cacheTag: String = "default"
    ) : super(scriptURL, sheetURL, tabname, query, classTypeForResponseParsing, appendInServer, appendInLocal, getEmptyListIfNoResultsFoundInServer, cacheTag)


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