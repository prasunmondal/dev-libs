package com.prasunmondal.dev.libs.gsheet.clients.APIRequests

import com.prasunmondal.dev.libs.StringUtils.StringUtils
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import org.json.JSONObject

abstract class APIRequest : GScript {
    private var uId: String = setUId()
    open var opCode: String = ""
    open var cacheUpdateOperation: ((APIRequest) -> Unit)? = null

    abstract fun getJSON(): JSONObject

    fun getUId(): String {
        return uId
    }

    fun setUId(uId: String = ""): String {
        if (uId.isBlank()) {
            this.uId = StringUtils.generateUniqueString()
        } else {
            this.uId = uId
        }
        return this.uId
    }

    open fun prepareResponse(
        requestObj: APIRequest,
        receivedResponseObj: APIResponse,
        buildingResponseObj: APIResponse? = null
    ): APIResponse {
        var buildingResponseObj_ = buildingResponseObj
        if (buildingResponseObj_ == null)
            buildingResponseObj_ = APIResponse()
        buildingResponseObj_.affectedRows = receivedResponseObj.affectedRows
        buildingResponseObj_.statusCode = receivedResponseObj.statusCode
        buildingResponseObj_.content = receivedResponseObj.content
        buildingResponseObj_.opId = receivedResponseObj.opId
        buildingResponseObj_.logs = receivedResponseObj.logs
        return buildingResponseObj_
    }
}