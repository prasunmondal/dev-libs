package com.prasunmondal.dev.libs.gsheet.caching

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequestsQueue
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.ReadAPIs
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.ReadResponse
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig

interface ExecutionOperations<T> :GSheetCaching<T>, CachingUtils<T> {

    fun execute(useCache: Boolean = true): List<T> {
        if(isCachingEnabledForThisRequest(prepareRequest())) {
            // If the request is ReadAPI, look into the cache
            return get(context, prepareRequest() as ReadAPIs<T>, useCache)
        }
        else {
            // If the request is not ReadAPI, execute directly
            val responseObj =
                prepareRequest().executeOne(ProjectConfig.dBServerScriptURL, prepareRequest())

            if (responseObj is ReadResponse<*>) {
                return responseObj.parsedResponse as List<T>
            }
            return listOf()
        }
    }

    fun isCachingEnabledForThisRequest(request: APIRequests): Boolean {
        return request is ReadAPIs<*>
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