package com.prasunmondal.dev.libs.gsheet.caching

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig

interface ExecutionOperations<T> :GSheetCaching<T>  {

    fun execute(){
        GScript.execute(GScript.calls,ProjectConfig.dBServerScriptURL,true)
    }

    fun queue(){
    var preparedRequest = prepareRequest()
        GScript.addRequest(preparedRequest)
    }

    fun getRequest(): APIRequests {
        return prepareRequest()
    }

    fun prepareRequest():APIRequests
    
}