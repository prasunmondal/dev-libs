package com.prasunmondal.dev.libs.gsheet.clients

import com.prasunmondal.dev.libs.gsheet.ContextKeeper
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequestsQueue
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.ReadAPIs
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.ReadResponse
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.dev.libs.gsheet.clients.responseCaching.ResponseCache
import com.prasunmondal.dev.libs.gsheet.metrics.GSheetMetrics
import com.prasunmondal.dev.libs.gsheet.post.serializable.PostObjectResponse
import com.prasunmondal.dev.libs.jsons.JsonParser
import android.content.Context
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable
import java.net.URL
import java.util.UUID
import java.util.function.Consumer

interface GScript : Serializable {

    var context: Context
    var scriptURL: String
        get() = ProjectConfig.dBServerScriptURL
        set(value) = TODO()

    var onCompletion: Consumer<PostObjectResponse>?
        get() = null
        set(value) = TODO()


    // TODO: add direct execution
    fun execute(scriptURL: String, useCache: Boolean = true): APIResponse {
        val apiRequest = this as APIRequests
        val instantCalls = APIRequestsQueue()
        val uId = generateUniqueString()
        instantCalls.addRequest(uId, apiRequest)
        val response = execute(instantCalls, scriptURL, useCache)
        return response[uId]!!
    }

    fun executeOne(scriptURL: String, apiRequest: APIRequests, useCache: Boolean = true): APIResponse {
        val apiRequest = this as APIRequests
        val instantCalls = APIRequestsQueue()
        val uId = generateUniqueString()
        instantCalls.addRequest(uId, apiRequest)
        val response = execute(instantCalls, scriptURL, useCache)
        return response[uId]!!
    }

    fun postExecute(response: String) {
        if (onCompletion == null)
            return
        val responseObj = PostObjectResponse(response)
        onCompletion!!.accept(responseObj)
    }

    fun setPostCompletion(onCompletion: Consumer<PostObjectResponse>?) {
        this.onCompletion = onCompletion
    }

    companion object {
        var defaultQueue = APIRequestsQueue()
        fun addRequest(apiCall: APIRequests?): String? {
            if (apiCall == null)
                return null

            val uid = apiCall.getUId()
            defaultQueue.addRequest(uid, apiCall)
            return uid
        }

        fun addRequest(apiCallsList: List<APIRequests>) {
            apiCallsList.forEach {
                addRequest(it)
            }
        }

        fun getCombinedJson(requestList: MutableMap<String, APIRequests>): Array<JSONObject> {
            val jsonArray = mutableListOf<JSONObject>()
            requestList.forEach { (uid, apiCall) ->
                val requestJson = apiCall.getJSON()
                requestJson.put("opId", uid)
                jsonArray.add(requestJson)
            }
            return jsonArray.toTypedArray()
        }

        fun execute(scriptURL: String, useCache: Boolean = true): MutableMap<String, APIResponse> {
            val responseList = execute(defaultQueue, scriptURL, useCache)
            defaultQueue.getQueue().clear()
            return responseList
        }

        fun removeCallsWhoseResponsesAreCached(apiRequest: APIRequests): Boolean {
//            Enable the below code to filter the calls that are already cached.
//            return !ResponseCache.isCached(apiRequest)
            return true
        }

        fun execute(
            apiRequestQueue: APIRequestsQueue,
            scriptURL: String,
            useCache: Boolean = true
        ): MutableMap<String, APIResponse> {

            GSheetMetrics.callCounter++

            val scriptUrl = URL(scriptURL)
            val filteredCalls =
                apiRequestQueue.getQueue().filter { (key, apiRequest) -> removeCallsWhoseResponsesAreCached(apiRequest) } as MutableMap

            if (filteredCalls.isEmpty()) return mutableMapOf()

            val jsonObjectArray = getCombinedJson(filteredCalls)

            val jsonArray = JSONArray()
            for (jsonObject in jsonObjectArray) {
                jsonArray.put(jsonObject)
            }
            val finalRequestJSON = "operations=$jsonArray"
            val d = ExecutePostCallsString(
                scriptUrl,
                finalRequestJSON
            ) { }//response -> postExecute(response) }
            val response2 = d.execute().get()

            LogMe.log("response2: $response2")

            val apiResponsesList = JsonParser.convertJsonArrayStringToJsonObjList(response2)
            val map: MutableMap<String, APIResponse> = mutableMapOf()
            for (apiResponse in apiResponsesList) {
                val responseOpId = apiResponse.get("opId").toString()
                val requestObj = filteredCalls[responseOpId]
                map[responseOpId] = APIResponse.parseToAPIResponse(apiResponse)
                var preparedResponse = requestObj!!.prepareResponse(requestObj, map[responseOpId]!!, null)
                map[responseOpId] = preparedResponse
                // Enable caching of responses only for read APIs
                if (requestObj is ReadAPIs<*>) {
                    preparedResponse = requestObj.prepareResponse(requestObj, map[responseOpId]!!, null)
                    map[responseOpId] = preparedResponse
//                    var parsedResponse =parsedResponse(preparedResponse as ReadResponse<*>).parsedResponse
//                    parsedResponse = requestObj.filter(parsedResponse)
//                    parsedResponse = requestObj.sort(parsedResponse)
//                    preparedResponse.parsedResponse = parsedResponse
                    ResponseCache.saveToLocal(requestObj, preparedResponse as ReadResponse<*>)
                }
            }
            return map
        }

        fun generateUniqueString(): String {
            val currentTimeMillis = System.currentTimeMillis()
            return UUID.randomUUID().toString() + "-" + currentTimeMillis
        }

        fun clearAll() {
            defaultQueue.clearList()
        }
    }
}
