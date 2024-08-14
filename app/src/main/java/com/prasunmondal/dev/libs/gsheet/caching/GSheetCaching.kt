package com.prasunmondal.dev.libs.gsheet.caching

import android.content.Context
import com.prasunmondal.dev.libs.caching.CentralCacheObj
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse
import com.prasunmondal.dev.libs.gsheet.clients.ClientFilter
import com.prasunmondal.dev.libs.gsheet.clients.ClientSort
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.jsons.JsonParser
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

interface GSheetCaching<T> : GScript {

    var sheetURL: String
    var tabname: String
    var query: String?
    var classTypeForResponseParsing: Class<T>
    var appendInServer: Boolean
    var appendInLocal: Boolean
    var cacheTag: String?
    var shallCacheData: Boolean
    var context: Any
    var filter: ClientFilter<T>?
    var sort: ClientSort<T>?

    fun getFromServer(shallCacheData: Boolean = true): List<T> {
        val apiResponse = (this as APIRequests).execute(scriptURL)
        val parsedResponse = parseResponse(apiResponse)
        if (shallCacheData) {
            saveResponse(parsedResponse)
        }
        return parsedResponse
    }

    fun parseResponse(apiResponse: APIResponse): List<T> {
        return JsonParser.convertJsonArrayStringToJavaObjList(
            apiResponse.content,
            classTypeForResponseParsing
        )
    }

    fun saveResponse(listOfObj: List<T>) {
        LogMe.log("Expensive Operation - saving data to local: ${getCacheKey()}")
        CentralCacheObj.centralCache.put(getCacheKey(), listOfObj, false)
    }

    fun getResponseFromCache(useCache: Boolean = false): Any? {
        return arrayListOf(
            CentralCacheObj.centralCache.get<T>(
                context as Context,
                getCacheKey(),
                useCache,
                false
            )
        )
    }

    fun getResponseFromCacheOrServer(useCache: Boolean = true): List<T> {
        if (CentralCacheObj.centralCache.isAvailable(context as Context, getCacheKey())) {
            return getResponseFromCache(useCache) as List<T>
        } else {
            return getFromServer(shallCacheData)
        }
    }

    fun getCacheKey(): String
}