package com.prasunmondal.dev.libs.gsheet.caching.createApis

import com.prasunmondal.dev.libs.gsheet.caching.CachingUtils
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.GSheetSerialized

interface InsertObjectTemplate<T>: RequestTemplatesInterface<T>, CachingUtils<T> {
    fun prepareInsertObjRequest(obj: T): GSheetInsertObject {
        val request = GSheetInsertObject()
        request.sheetId = sheetURL
        request.tabName = tabname
        request.setDataObject(obj as Any)
        return request
    }
    fun prepareInsertObjRequest(obj: List<T>): List<GSheetInsertObject> {
        val requestsList: MutableList<GSheetInsertObject> = mutableListOf()
        obj.forEach {
            requestsList.add(prepareInsertObjRequest(it))
        }
        return requestsList
    }

    fun queueInsertObj(obj: T) {
        val listOfReqs = prepareInsertObjRequest(obj)
        GScript.addRequest(listOfReqs)
        saveToLocal(this as GSheetSerialized<T>, listOf(obj))
    }
    fun queueInsertObj(obj: List<T>) {
        val listOfReqs = prepareInsertObjRequest(obj)
        GScript.addRequest(listOfReqs)
        saveToLocal(this as GSheetSerialized<T>, obj)
    }

    fun insertObject(obj: T): APIResponse {
        val t = prepareInsertObjRequest(obj).execute(scriptURL)
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
            prevList = obj1.getMultiple(context, obj1.prepareFetchAllRequest(), true) as MutableList<T>
        }
        prevList.addAll(obj)
        obj1.saveToCache(getCacheKey(), prevList)
    }
}