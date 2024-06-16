package com.prasunmondal.libs.gsheet.clients.APIRequests.CreateAPIs

import com.prasunmondal.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.libs.gsheet.clients.APIResponses.APIResponse
import com.prasunmondal.libs.gsheet.clients.APIResponses.CreateResponse

abstract class CreateAPIs : APIRequests() {
    lateinit var sheetId: String
    lateinit var tabName: String
    lateinit var data: String
    var appendInServer: Boolean = true

    fun sheetId(sheetId: String) {
        this.sheetId = sheetId
    }

    fun tabName(tabName: String) {
        this.tabName = tabName
    }

    override fun prepareResponse(
        requestObj: APIRequests,
        receivedResponseObj: APIResponse,
        buildingResponseObj: APIResponse?
    ): APIResponse {
        var buildingResponseObj_ = buildingResponseObj as CreateResponse?
        if (buildingResponseObj_ == null)
            buildingResponseObj_ = CreateResponse()

        super.prepareResponse(requestObj, receivedResponseObj, buildingResponseObj)
        buildingResponseObj_.sheetId = this.sheetId
        buildingResponseObj_.tabName = this.tabName
        return buildingResponseObj_
    }
}