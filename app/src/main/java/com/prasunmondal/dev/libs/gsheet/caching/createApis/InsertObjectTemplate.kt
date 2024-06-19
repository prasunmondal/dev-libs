package com.prasunmondal.dev.libs.gsheet.caching.createApis

import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.responseCaching.CachingUtils

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
    }
    fun queueInsertObj(obj: List<T>) {
        val listOfReqs = prepareInsertObjRequest(obj)
        GScript.addRequest(listOfReqs)
    }

    fun insertObject(obj: T): APIResponse {
        return prepareInsertObjRequest(obj).execute(scriptURL)
    }

//     TODO: insert multiple insertions
//    fun insertObjects(obj: List<T>): APIResponse {
//        return APIResponse()
//    }
}