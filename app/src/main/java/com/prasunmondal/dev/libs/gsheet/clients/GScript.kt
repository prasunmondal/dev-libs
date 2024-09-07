package com.prasunmondal.dev.libs.gsheet.clients

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequest
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequestsQueue
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.dev.libs.gsheet.post.serializable.PostObjectResponse
import android.content.Context
import com.prasunmondal.dev.libs.StringUtils.StringUtils
import com.prasunmondal.dev.libs.gsheet.GScriptUtils
import java.util.function.Consumer

interface GScript {

    var context: Context

    var onCompletion: Consumer<PostObjectResponse>?
        get() = null
        set(value) = TODO()

    fun setPostCompletion(onCompletion: Consumer<PostObjectResponse>?) {
        this.onCompletion = onCompletion
    }

    fun getScriptURL(): String {
        return ProjectConfig.dBServerScriptURL
    }
    // execute()
    // executeQueue(queue = defaultQueue)
    // executeOne()


    fun executeQueue(): MutableMap<String, APIResponse> {
        return Companion.execute(ProjectConfig.dBServerScriptURL)
    }
    // TODO: add direct execution
    fun execute(scriptURL: String = ProjectConfig.dBServerScriptURL): APIResponse {
        val apiRequest = this as APIRequest
        val instantCalls = APIRequestsQueue()
        val uId = StringUtils.generateUniqueString()
        instantCalls.addRequest(uId, apiRequest)
        val response = GScriptUtils.executeRequestsList(instantCalls, scriptURL)
        return response[uId]!!
    }

    fun executeOne(): APIResponse {
        val apiRequest = this as APIRequest
        val instantCalls = APIRequestsQueue()
        val uId = StringUtils.generateUniqueString()
        instantCalls.addRequest(uId, apiRequest)
        val response = GScriptUtils.executeRequestsList(instantCalls, getScriptURL())
//        if(response.isEmpty())
//            return APIResponse()
        return response[uId]!!
    }

    companion object {
        private var defaultQueue = APIRequestsQueue()
        fun addRequest(apiCall: APIRequest): String {
            return defaultQueue.addRequest(apiCall)
        }

        fun addRequests(apiCallsList: List<APIRequest>) {
            return defaultQueue.addRequest(apiCallsList)
        }

        fun execute(scriptURL: String): MutableMap<String, APIResponse> {
            val responseList = GScriptUtils.executeRequestsList(defaultQueue, scriptURL)
            defaultQueue.clearList()
            return responseList
        }

        fun execute(): MutableMap<String, APIResponse> {
            return execute(ProjectConfig.dBServerScriptURL)
        }

        fun clearDefaultRequestQueue() {
            defaultQueue.clearList()
        }
    }
}
