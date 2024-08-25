package com.prasunmondal.dev.libs.gsheet.clients.APIRequests

import com.prasunmondal.dev.libs.StringUtils.StringUtils
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig

class APIRequestsQueue {
    private val listofAPIRequest = mutableMapOf<String, APIRequests>()


    fun addRequest(request:APIRequests){
        listofAPIRequest[StringUtils.generateUniqueString()] = request
    }

    fun addRequest(uId: String,request: APIRequests){
        listofAPIRequest[uId] = request
    }

    fun execute(){
        GScript.execute(listofAPIRequest, ProjectConfig.dBServerScriptURL,true)
    }
}