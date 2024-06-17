package com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.ReadAPIs
import com.prasunmondal.dev.libs.gsheet.clients.responseCaching.APIRequestsTemplates
import org.json.JSONObject

class GSheetFetchAll<T> : ReadAPIs<T>() {
    override var opCode = "FETCH_ALL"

    override fun getJSON(): JSONObject {
        val postDataParams = JSONObject()
        postDataParams.put("opCode", opCode)
        postDataParams.put("sheetId", this.sheetId)
        postDataParams.put("tabName", this.tabName)
        return postDataParams
    }

    override fun <T> defaultInitialize(
        request: APIRequests,
        reqValues: APIRequestsTemplates<T>
    ): APIRequests {
        var request_ = request as ReadAPIs<T>
        super.defaultInitialize(request, reqValues)
        request_.opCode = opCode
        return request
    }
}