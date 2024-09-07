package com.prasunmondal.dev.libs.gsheet.clients.APIRequests

import com.prasunmondal.dev.libs.StringUtils.StringUtils
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.dev.libs.gsheet.exceptions.GScriptDuplicateUIDException

class APIRequestsQueue {
    private val listofAPIRequest = mutableMapOf<String, APIRequest>()

    fun getQueue(): MutableMap<String, APIRequest> {
        return listofAPIRequest
    }

    fun addRequest(request: APIRequest) {
        listofAPIRequest[StringUtils.generateUniqueString()] = request
    }

    fun execute() {
        GScript.execute(this, ProjectConfig.dBServerScriptURL, true)
    }

    fun addRequest(uid: String, request: APIRequest) {
        if (listofAPIRequest.containsKey(uid)) {
            throw GScriptDuplicateUIDException()
        }
        listofAPIRequest[uid] = request
    }

    fun clearList() {
        listofAPIRequest.clear()
    }
}