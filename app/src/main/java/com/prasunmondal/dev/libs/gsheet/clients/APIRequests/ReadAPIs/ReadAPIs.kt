package com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.ReadResponse
import com.prasunmondal.dev.libs.gsheet.clients.responseCaching.APIRequestsTemplates
import com.prasunmondal.dev.libs.gsheet.clients.responseCaching.ResponseCache
import com.prasunmondal.dev.libs.gsheet.serializer.parsers.Parser

abstract class ReadAPIs<T> : APIRequests(), ResponseCache {
    lateinit var sheetId: String
    lateinit var tabName: String
    lateinit var data: String
    open lateinit var classTypeForResponseParsing: Class<T>
    var cacheData: Boolean = true
    var filter: (APIResponse) -> APIResponse = { apiResponse -> apiResponse }
    var sorting: (APIResponse) -> APIResponse = { apiResponse -> apiResponse }

    fun sheetId(sheetId: String) {
        this.sheetId = sheetId
    }

    fun tabName(tabName: String) {
        this.tabName = tabName
    }

    override fun getCacheKey(): String {
        return "${this.sheetId}\\${this.tabName}\\${getJSON()}"
    }

//    override fun <T> defaultInitialize(
//        request: APIRequests,
//        reqValues: APIRequestsTemplates<T>
//    ): APIRequests {
//        var request_ = request as ReadAPIs<T>
//        super.defaultInitialize(request, reqValues)
//        request_.sheetId = reqValues.sheetURL
//        request_.tabName = reqValues.tabname
//        request_.classTypeForResponseParsing = reqValues.classTypeForResponseParsing
//        request_.cacheData = cacheData
//        return request
//    }

    override fun prepareResponse(
        requestObj: APIRequests,
        receivedResponseObj: APIResponse,
        buildingResponseObj: APIResponse?
    ): ReadResponse<T> {
        var buildingResponseObj_ =
            (if (buildingResponseObj == null)
                super.prepareResponse(requestObj, receivedResponseObj, ReadResponse<T>())
            else
                super.prepareResponse(requestObj, receivedResponseObj, buildingResponseObj)
                    ) as ReadResponse<T>

//        super.prepareResponse(requestObj, receivedResponseObj, buildingResponseObj)
        buildingResponseObj_.sheetId = this.sheetId
        buildingResponseObj_.tabName = this.tabName
        buildingResponseObj_.parsedResponse = Parser.convertJsonArrayStringToJavaObjList(
            receivedResponseObj.content,
            (requestObj as ReadAPIs<T>).classTypeForResponseParsing
        ) as List<T>
        return buildingResponseObj_
    }
}