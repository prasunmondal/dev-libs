package com.prasunmondal.dev.libs.gsheet

import android.content.Context
import com.prasunmondal.dev.libs.caching.CentralCacheObj
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequest
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequestsQueue
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.ReadAPIs
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.ReadResponse
import com.prasunmondal.dev.libs.gsheet.clients.PostCall
import com.prasunmondal.dev.libs.gsheet.clients.responseCaching.ResponseCache
import com.prasunmondal.dev.libs.gsheet.metrics.GSheetMetrics
import com.prasunmondal.dev.libs.gsheet.post.serializable.PostObjectResponse
import com.prasunmondal.dev.libs.jsons.JsonParser
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.util.function.Consumer

class GScriptUtils {

    companion object {
        fun getCombinedJson(requestList: MutableMap<String, APIRequest>): String {
            val jsonList = mutableListOf<JSONObject>()
            requestList.forEach { (uid, apiCall) ->
                val requestJson = apiCall.getJSON()
                requestJson.put("opId", uid)
                jsonList.add(requestJson)
            }
            val jsonObjectArray = jsonList.toTypedArray()

            val jsonArray = JSONArray()
            for (jsonObject in jsonObjectArray) {
                jsonArray.put(jsonObject)
            }
            return "operations=$jsonArray"
        }

        fun isResponseCached(context: Context, apiRequest: APIRequest): Boolean {
//            Filter the calls that can be served from cache.
            if (apiRequest is ReadAPIs<*>) {
                val isAvailable = CentralCacheObj.centralCache.isAvailable(
                    context,
                    apiRequest.getCacheKey(),
                    apiRequest.useCache, false
                )
                LogMe.log("GScript.execute::shallFetch: ${!isAvailable} - ${apiRequest.getCacheKey()}")
                return isAvailable
            }
            LogMe.log("GScript.execute::shallFetch: true - forced using flag useCache")
            return false
        }

        fun postExecute(onCompletion: Consumer<PostObjectResponse>?, response: String) {
            if (onCompletion == null)
                return
            val responseObj = PostObjectResponse(response)
            onCompletion.accept(responseObj)
        }

        fun parseJsonTesponses(
            responseJsonList: List<JSONObject>,
            filteredCalls: MutableMap<String, APIRequest>
        ): MutableMap<String, APIResponse> {
            val map: MutableMap<String, APIResponse> = mutableMapOf()
            for (apiResponse in responseJsonList) {
                val responseOpId = apiResponse.get("opId").toString()
                val requestObj = filteredCalls[responseOpId]
                map[responseOpId] = APIResponse.parseToAPIResponse(apiResponse)
                var preparedResponse =
                    requestObj!!.prepareResponse(requestObj, map[responseOpId]!!, null)
                map[responseOpId] = preparedResponse
                // Enable caching of responses only for read APIs
                if (requestObj is ReadAPIs<*>) {
                    preparedResponse =
                        requestObj.prepareResponse(requestObj, map[responseOpId]!!, null)
                    map[responseOpId] = preparedResponse
                    ResponseCache.saveToLocal(requestObj, preparedResponse as ReadResponse<*>)
                }
            }
            return map
        }

        fun executeRequestsList(
            apiRequestQueue: APIRequestsQueue,
            scriptURL: String
        ): MutableMap<String, APIResponse> {

            GSheetMetrics.callCounter++
            GSheetMetrics.requestsQueued = GSheetMetrics.requestsQueued + apiRequestQueue.getQueue().size

            val scriptUrl = URL(scriptURL)
            executeAllCacheUpdateOperations(apiRequestQueue)
            val filteredCalls = filterOutCallsWhoseResultsAreAlreadyCached(apiRequestQueue)
            GSheetMetrics.requestsProcessed = GSheetMetrics.requestsProcessed + filteredCalls.size

            if (filteredCalls.isEmpty()) return mutableMapOf()

            val finalRequestJSON = getCombinedJson(filteredCalls)
            val postCall = PostCall(scriptUrl, finalRequestJSON) { } // response -> GScriptUtils.postExecute(response)
            val response = postCall.execute().get()

            val responseJsonList = JsonParser.convertJsonArrayStringToJsonObjList(response)
            val responseParsedList =
                parseJsonTesponses(responseJsonList, filteredCalls)
            return responseParsedList
        }

        private fun filterOutCallsWhoseResultsAreAlreadyCached(apiRequestQueue: APIRequestsQueue): MutableMap<String, APIRequest> {
            return apiRequestQueue.getQueue().filter { (key, apiRequest) ->
                !isResponseCached(
                    apiRequest.context,
                    apiRequest
                )
            } as MutableMap
        }

        private fun executeAllCacheUpdateOperations(apiRequestQueue: APIRequestsQueue) {
            apiRequestQueue.getQueue().forEach {
                it.value.cacheUpdateOperation?.let { it1 -> it1(it.value) }
            }
        }
    }
}
