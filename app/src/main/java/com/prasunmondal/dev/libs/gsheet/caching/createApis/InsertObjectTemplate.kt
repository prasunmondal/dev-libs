package com.prasunmondal.dev.libs.gsheet.caching.createApis

import com.prasunmondal.dev.libs.gsheet.caching.CachingUtils
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.GSheetSerialized

interface InsertObjectTemplate<T : Any>: RequestTemplatesInterface<T>, CachingUtils<T> {

    var data :T
    override fun prepareRequest(): GSheetInsertObject {
        val request = GSheetInsertObject()
        request.sheetId = sheetURL
        request.tabName = tabname
        request.setDataObject(data as Any)
        return request
    }
//    fun prepareInsertObjRequest(obj: List<T>): List<GSheetInsertObject> {
//        val requestsList: MutableList<GSheetInsertObject> = mutableListOf()
//        obj.forEach {
//            requestsList.add(prepareRequest(it))
//        }
//        return requestsList
//    }

    fun queueInsertObj(obj: T) {
        val listOfReqs = prepareRequest()
        GScript.addRequest(listOfReqs)
        saveToLocal(this as GSheetSerialized<T>, listOf(obj))
    }
    fun queueInsertObj(obj: List<T>) {
        val listOfReqs = prepareRequest()
        GScript.addRequest(listOfReqs)
        saveToLocal(this as GSheetSerialized<T>, obj)
    }

    fun insertObject(obj: T): APIResponse {
        data=obj
        val t = prepareRequest().execute(scriptURL)
        saveToLocal(this as GSheetSerialized<T>, listOf(obj), appendInLocal)
        return t
    }

//     TODO: insert multiple insertions
//    fun insertObjects(obj: List<T>): APIResponse {
//        return APIResponse()
//    }

    fun saveToLocal(obj1: GSheetSerialized<T>, obj: List<T>, append: Boolean = appendInLocal) {
        var prevList = mutableListOf<T>()
        if(appendInLocal) {
            prevList = obj1.getMultiple(context, obj1.prepareRequest(), true) as MutableList<T>
        }
        prevList.addAll(obj)
        obj1.saveToCache(getCacheKey(), prevList)
    }
}