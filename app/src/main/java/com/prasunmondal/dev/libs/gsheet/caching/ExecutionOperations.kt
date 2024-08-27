package com.prasunmondal.dev.libs.gsheet.caching

import com.prasunmondal.dev.libs.caching.CentralCache
import com.prasunmondal.dev.libs.caching.CentralCacheObj
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequestsQueue
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.ReadResponse
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig

interface ExecutionOperations<T> :GSheetCaching<T>  {

    fun execute(useCache: Boolean = true): List<T> {
        var resultsFromCache: T? = null

        // Try to get from cache if useCache = true
        if(useCache) {
            resultsFromCache =
                CentralCacheObj.centralCache.get<T>(context, getCacheKey(), useCache)
        }

        // If no results found, try to get it from server
        if (resultsFromCache != null) {
            return resultsFromCache as List<T>
        } else {
            val responseObj =
                prepareRequest().executeOne(ProjectConfig.dBServerScriptURL, prepareRequest())

            if (responseObj is ReadResponse<*>) {
                return responseObj.parsedResponse as List<T>
            }
            return listOf()
        }
    }

    fun queue(){
        GScript.addRequest(prepareRequest())
    }

    fun queue(requestQueue1: APIRequestsQueue){
        requestQueue1.addRequest(prepareRequest())
    }

    fun getRequestObj(): APIRequests {
        return prepareRequest()
    }

    fun prepareRequest():APIRequests
}