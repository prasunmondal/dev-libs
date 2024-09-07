package com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequest
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.ReadResponse
import com.prasunmondal.dev.libs.gsheet.clients.ClientFilter
import com.prasunmondal.dev.libs.gsheet.clients.ClientSort
import com.prasunmondal.dev.libs.gsheet.clients.responseCaching.ResponseCache
import com.prasunmondal.dev.libs.jsons.JsonParser

abstract class ReadAPIs<T> : APIRequest(), ResponseCache {
    lateinit var sheetId: String
    lateinit var tabName: String
    lateinit var data: String
    open lateinit var modelClass: Class<T>
    var cacheData: Boolean = true
    var filter: ClientFilter<T>? = null
    var sort: ClientSort<T>? = null
    var useCache: Boolean = true

    fun sheetId(sheetId: String) {
        this.sheetId = sheetId
    }

    fun tabName(tabName: String) {
        this.tabName = tabName
    }

    override fun getCacheKey(): String {
        var cacheKey = "${this.sheetId}\\${this.tabName}\\${getJSON()}"

        if(this.filter != null) {
            cacheKey += "\\<localOp:${this.filter!!.filterName}>"
        }
        if(this.sort != null) {
            cacheKey += "\\<localOp:${this.sort!!.sortName}>"
        }
        return cacheKey
    }

    fun cacheUpdateOperation() {
        // No Action Required.
    }

    override fun prepareResponse(
        requestObj: APIRequest,
        receivedResponseObj: APIResponse,
        buildingResponseObj: APIResponse?
    ): ReadResponse<T> {
        var buildingResponseObj_ =
            (if (buildingResponseObj == null)
                super.prepareResponse(requestObj, receivedResponseObj, ReadResponse<T>())
            else
                super.prepareResponse(requestObj, receivedResponseObj, buildingResponseObj)
                    ) as ReadResponse<T>

        buildingResponseObj_.sheetId = this.sheetId
        buildingResponseObj_.tabName = this.tabName
        buildingResponseObj_.parsedResponse = JsonParser.convertJsonArrayStringToJavaObjList(
            receivedResponseObj.content,
            (requestObj as ReadAPIs<T>).modelClass
        )
        
        if(requestObj.filter!=null)
            buildingResponseObj_.parsedResponse = requestObj.filter!!.filter!!(buildingResponseObj_.parsedResponse)
        if(requestObj.sort!=null)
            buildingResponseObj_.parsedResponse = requestObj.sort!!.sort!!(buildingResponseObj_.parsedResponse)

        return buildingResponseObj_
    }
}