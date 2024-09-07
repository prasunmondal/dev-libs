package com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs

import com.prasunmondal.dev.libs.caching.CentralCacheObj
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequest
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.CreateResponse
import java.util.function.Consumer
import kotlin.reflect.KFunction

abstract class CreateAPIs : APIRequest() {
    lateinit var sheetId: String
    lateinit var tabName: String
    lateinit var data: String
    var appendInServer: Boolean = true
    override var cacheUpdateOperation: ((APIRequest) -> Unit)? = ::cacheUpdateOperation

    fun sheetId(sheetId: String) {
        this.sheetId = sheetId
    }

    fun tabName(tabName: String) {
        this.tabName = tabName
    }

    override fun prepareResponse(
        requestObj: APIRequest,
        receivedResponseObj: APIResponse,
        buildingResponseObj: APIResponse?
    ): APIResponse {
        var buildingResponseObj_ = buildingResponseObj as CreateResponse?
        if (buildingResponseObj_ == null)
            buildingResponseObj_ = CreateResponse()

        super.prepareResponse(requestObj, receivedResponseObj, buildingResponseObj_)
        buildingResponseObj_.sheetId = this.sheetId
        buildingResponseObj_.tabName = this.tabName
        return buildingResponseObj_
    }

    fun cacheUpdateOperation(request: APIRequest) {
        CentralCacheObj.centralCache.removeCacheObjectsWhereKeyStartsWith(context, "$sheetId//$tabName")
    }
}