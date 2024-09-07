package com.prasunmondal.dev.libs.gsheet.caching

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequest
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequestsQueue
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.ReadAPIs
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.ReadResponse
import com.prasunmondal.dev.libs.gsheet.clients.GScript

interface ExecutionOperations<T> : GSheetCaching<T>, CachingUtils<T> {

    fun execute(): List<T> {

        // If there are more than 1 request, results are not returned
        if(prepareRequest().size > 1) {
            val requestQueue = APIRequestsQueue()
            requestQueue.addRequest(prepareRequest())
            requestQueue.execute()
            return listOf()
        }

        // if only one request is available
        val request = prepareRequest()[0]

        if(request is ReadAPIs<*>) {
            // if read request:
            // - search in cache, or file, or server
            // - return the parsed list of objects
            return get(request as ReadAPIs<T>)
        } else {
            // if not read (i.e update, create or delete) operation:
            // delete the existing related data first
            // make the call
            // return empty list.

            // Delete the cached objects
            deleteCacheObjects(context, "$sheetId\\$tabname")
            val responseObj = prepareRequest()[0].executeOne()
            if (responseObj is ReadResponse<*>) {
                return responseObj.parsedResponse as List<T>
            }
            return listOf()
        }
    }

    fun isCachingEnabledForThisRequest(request: List<APIRequest>): Boolean {
        return request is ReadAPIs<*>
    }

    fun queue() {
        GScript.addRequests(prepareRequest())
    }

    fun queue(requestQueue1: APIRequestsQueue) {
        requestQueue1.addRequest(prepareRequest())
    }

    fun getRequestObj(): List<APIRequest> {
        return prepareRequest()
    }

    fun prepareRequest(): List<APIRequest>
}