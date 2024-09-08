package com.prasunmondal.dev.libs.gsheet.clients.APIRequests

import com.prasunmondal.dev.libs.StringUtils.StringUtils
import com.prasunmondal.dev.libs.gsheet.GScriptUtils
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.dev.libs.gsheet.exceptions.GScriptDuplicateUIDException

class APIRequestsQueue {
    private val listofAPIRequest = mutableMapOf<String, APIRequest>()

    fun getQueue(): MutableMap<String, APIRequest> {
        return listofAPIRequest
    }

    fun addRequest(request: APIRequest): String {
        val uId = StringUtils.generateUniqueString()
        listofAPIRequest[uId] = request
        return uId
    }

    fun addRequest(requests: List<APIRequest>) {
        requests.forEach {
            addRequest(it)
        }
    }

    fun execute() {
        GScriptUtils.executeRequestsList(this, ProjectConfig.dBServerScriptURL)
    }

    fun addRequest(uid: String, request: APIRequest): String {
        var uIdToBeUsed = request.getUId()
        if(uid.isNotBlank())
            uIdToBeUsed = uid

        if(uIdToBeUsed.isBlank())
            uIdToBeUsed = request.getUId()

        if(uIdToBeUsed.isBlank())
            uIdToBeUsed = StringUtils.generateUniqueString()

        if (listofAPIRequest.containsKey(uIdToBeUsed)) {
            throw GScriptDuplicateUIDException()
        }
        listofAPIRequest[uIdToBeUsed] = request
        return uid
    }

    fun clearList() {
        listofAPIRequest.clear()
    }
}