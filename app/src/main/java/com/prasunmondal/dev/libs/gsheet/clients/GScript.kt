package com.prasunmondal.dev.libs.gsheet.clients

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.ReadAPIs
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.ReadResponse
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.dev.libs.gsheet.clients.responseCaching.ResponseCache
import com.prasunmondal.dev.libs.gsheet.exceptions.GScriptDuplicateUIDException
import com.prasunmondal.dev.libs.gsheet.metrics.GSheetMetrics
import com.prasunmondal.dev.libs.gsheet.post.serializable.PostObjectResponse
import com.prasunmondal.dev.libs.jsons.JsonParser
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable
import java.net.URL
import java.util.UUID
import java.util.function.Consumer

interface GScript : Serializable {
    var scriptURL: String
        get() = ProjectConfig.dBServerScriptURL
        set(value) = TODO()

    //    var json: JSONObject
    var onCompletion: Consumer<PostObjectResponse>?
        get() = null
        set(value) = TODO()


    // TODO: add direct execution
    fun execute(scriptURL: String, useCache: Boolean = true): APIResponse {
        val apiRequest = this as APIRequests
        val instantCalls: MutableMap<String, APIRequests> = mutableMapOf()
        var uId = generateUniqueString()
        instantCalls[uId] = apiRequest
        val response = execute(instantCalls, scriptURL, useCache)
        return response[uId]!!
    }

    fun executeOne(
        scriptURL: String,
        apiRequest: APIRequests,
        useCache: Boolean = true
    ): APIResponse {
        val apiRequest = this as APIRequests
        val instantCalls: MutableMap<String, APIRequests> = mutableMapOf()
        var uId = generateUniqueString()
        instantCalls[uId] = apiRequest
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
        var calls = mutableMapOf<String, APIRequests>()
        fun addRequest(apiCall: APIRequests?): String? {
            if (apiCall == null)
                return null

            val uid = apiCall.getUId()
            addRequest(uid, apiCall)
            return uid
        }

        fun addRequest(apiCallsList: List<APIRequests>) {
            apiCallsList.forEach {
                addRequest(it)
            }
        }

        fun addRequest(uid: String, apiCall: APIRequests) {
            if (calls.containsKey(uid)) {
                throw GScriptDuplicateUIDException()
            }
            calls[uid] = apiCall
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
            val responseList = execute(calls, scriptURL, useCache)
            calls.clear()
            return responseList
        }

        fun removeCallsWhoseResponsesAreCached(apiRequest: APIRequests): Boolean {
//            Enable the below code to filter the calls that are already cached.
//            return !ResponseCache.isCached(apiRequest)
            return true
        }

        fun execute(
            calls: MutableMap<String, APIRequests>,
            scriptURL: String,
            useCache: Boolean = true
        ): MutableMap<String, APIResponse> {

            GSheetMetrics.callCounter++

            val scriptUrl = URL(scriptURL)
            val filteredCalls =
                calls.filter { (key, apiRequest) -> removeCallsWhoseResponsesAreCached(apiRequest) } as MutableMap

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

            // TODO
            // concurrent exception
            // Process: com.prasunmondal.dev.libs, PID: 10488
            //                                                                                                    java.lang.RuntimeException: Unable to start activity ComponentInfo{com.prasunmondal.dev.libs/com.prasunmondal.tests.libs.MainActivity}: java.util.concurrent.ExecutionException: java.io.IOException: unexpected end of stream on com.android.okhttp.Address@be0f57bb
            //                                                                                                    	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3645)
            //                                                                                                    	at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3782)
            //                                                                                                    	at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:101)
            //                                                                                                    	at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:135)
            //                                                                                                    	at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:95)
            //                                                                                                    	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2307)
            //                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:106)
            //                                                                                                    	at android.os.Looper.loopOnce(Looper.java:201)
            //                                                                                                    	at android.os.Looper.loop(Looper.java:288)
            //                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:7872)
            //                                                                                                    	at java.lang.reflect.Method.invoke(Native Method)
            //                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:548)
            //                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:936)
            //                                                                                                    Caused by: java.util.concurrent.ExecutionException: java.io.IOException: unexpected end of stream on com.android.okhttp.Address@be0f57bb
            //                                                                                                    	at java.util.concurrent.FutureTask.report(FutureTask.java:122)
            //                                                                                                    	at java.util.concurrent.FutureTask.get(FutureTask.java:191)
            //                                                                                                    	at android.os.AsyncTask.get(AsyncTask.java:618)
            //                                                                                                    	at com.prasunmondal.dev.libs.gsheet.clients.GScript$Companion.execute(GScript.kt:138)
            //                                                                                                    	at com.prasunmondal.dev.libs.gsheet.clients.GScript$Companion.execute(GScript.kt:102)
            //                                                                                                    	at com.prasunmondal.dev.libs.gsheet.clients.GScript$Companion.execute$default(GScript.kt:101)
            //                                                                                                    	at com.prasunmondal.dev.libs.gsheet.clients.Tests.CreateAPIs.InsertObjectTest.test(InsertObjectTest.kt:45)
            //                                                                                                    	at com.prasunmondal.dev.libs.gsheet.clients.Tests.CreateAPIs.InsertObjectTest.<init>(InsertObjectTest.kt:13)
            //                                                                                                    	at com.prasunmondal.dev.libs.gsheet.clients.Tests.Test$Companion.start(Test.kt:25)
            //                                                                                                    	at com.prasunmondal.tests.libs.MainActivity.testAll(MainActivity.kt:22)
            //                                                                                                    	at com.prasunmondal.tests.libs.MainActivity.onCreate(MainActivity.kt:17)
            //                                                                                                    	at android.app.Activity.performCreate(Activity.java:8305)
            //                                                                                                    	at android.app.Activity.performCreate(Activity.java:8284)
            //                                                                                                    	at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1417)
            //                                                                                                    	at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3626)
            //                                                                                                    	at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3782) 
            //                                                                                                    	at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:101) 
            //                                                                                                    	at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:135) 
            //                                                                                                    	at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:95) 
            //                                                                                                    	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2307) 
            //                                                                                                    	at android.os.Handler.dispatchMessage(Handler.java:106) 
            //                                                                                                    	at android.os.Looper.loopOnce(Looper.java:201) 
            //                                                                                                    	at android.os.Looper.loop(Looper.java:288) 
            //                                                                                                    	at android.app.ActivityThread.main(ActivityThread.java:7872) 
            //                                                                                                    	at java.lang.reflect.Method.invoke(Native Method) 
            //                                                                                                    	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:548) 
            //                                                                                                    	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:936) 
            //                                                                                                    Caused by: java.io.IOException: unexpected end of stream on com.android.okhttp.Address@be0f57bb
            //                                                                                                    	at com.android.okhttp.internal.http.Http1xStream.readResponse(Http1xStream.java:203)
            //                                                                                                    	at com.android.okhttp.internal.http.Http1xStream.readResponseHeaders(Http1xStream.java:129)
            //                                                                                                    	at com.android.okhttp.internal.http.HttpEngine.readNetworkResponse(HttpEngine.java:750)
            //                                                                                                    	at com.android.okhttp.internal.http.HttpEngine.readResponse(HttpEngine.java:622)
            //                                                                                                    	at com.android.okhttp.internal.huc.HttpURLConnectionImpl.execute(HttpURLConnectionImpl.java:475)
            //                                                                                                    	at com.android.okhttp.internal.huc.HttpURLConnectionImpl.getResponse(HttpURLConnectionImpl.java:411)
            //                                                                                                    	at com.android.okhttp.internal.huc.HttpURLConnectionImpl.getResponseCode(HttpURLConnectionImpl.java:542)
            //                                                                                                    	at com.android.okhttp.internal.huc.DelegatingHttpsURLConnection.getResponseCode(DelegatingHttpsURLConnection.java:106)
            //                                                                                                    	at com.android.okhttp.internal.huc.HttpsURLConnectionImpl.getResponseCode(HttpsURLConnectionImpl.java:30)
            //                                                                                                    	at com.prasunmondal.dev.libs.gsheet.clients.ExecutePostCallsString.doInBackground(ExecutePostCallsString.kt:40)
            //                                                                                                    	at com.prasunmondal.dev.libs.gsheet.clients.ExecutePostCallsString.doInBackground(ExecutePostCallsString.kt:17)
            //                                                                                                    	at android.os.AsyncTask$3.call(AsyncTask.java:394)
            //                                                                                                    	at java.util.concurrent.FutureTask.run(FutureTask.java:264)
            //                                                                                                    	at android.os.AsyncTask$SerialExecutor$1.run(AsyncTask.java:305)
            //2024-08-11 20:53:13.906 10488-10488 AndroidRuntime          com.prasunmondal.dev.libs            E  	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1137)
            //                                                                                                    	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:637)
            //                                                                                                    	at java.lang.Thread.run(Thread.java:1012)
            //                                                                                                    Caused by: java.io.EOFException: \n not found: size=0 content=...
            //                                                                                                    	at com.android.okhttp.okio.RealBufferedSource.readUtf8LineStrict(RealBufferedSource.java:202)
            //                                                                                                    	at com.android.okhttp.internal.http.Http1xStream.readResponse(Http1xStream.java:188)
            //                                                                                                    	... 16 more
            val response2 = d.execute().get()

            LogMe.log("response2: $response2")

            val apiResponsesList = JsonParser.convertJsonArrayStringToJsonObjList(response2)
            val map: MutableMap<String, APIResponse> = mutableMapOf()
            for (apiResponse in apiResponsesList) {
                val responseOpId = apiResponse.get("opId").toString()
                val requestObj = filteredCalls[responseOpId]
                map[responseOpId] = APIResponse.parseToAPIResponse(apiResponse)
                val preparedResponse =
                    requestObj!!.prepareResponse(requestObj, map[responseOpId]!!, null)
                map[responseOpId] = preparedResponse
                // Enable caching of responses only for read APIs
                if (requestObj is ReadAPIs<*>) {
//                    var parsedResponse = (preparedResponse as ReadResponse<*>).parsedResponse
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
            calls.clear()
        }
    }
}