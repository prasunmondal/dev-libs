package com.prasunmondal.dev.libs.gsheet.clients.APIRequests

import com.prasunmondal.dev.libs.StringUtils.StringUtils
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ReadAPIs.FetchData.FetchAll.FetchAllBySortingModel

class APIRequestsQueue {
    val listofAPIRequest = mutableMapOf<String, APIRequests>()


    fun addRequest(request:APIRequests){
        listofAPIRequest.put(StringUtils.generateUniqueString(),request)
    }

    fun execute(){
        GScript.execute(listofAPIRequest, ProjectConfig.dBServerScriptURL,true)
    }
}