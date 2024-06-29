package com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.ReadAPIs
import org.json.JSONObject

class GSheetFetchByQuery<T> : ReadAPIs<T>() {
    override var opCode = "FETCH_BY_QUERY"
    lateinit var query: String

    override fun getJSON(): JSONObject {
        val postDataParams = JSONObject()
        postDataParams.put("opCode", opCode)
        postDataParams.put("sheetId", this.sheetId)
        postDataParams.put("tabName", this.tabName)
        postDataParams.put("query", this.query)
        return postDataParams
    }

    fun query(query: String) {
        this.query = query
    }

//    override fun <T> defaultInitialize(
//        request: APIRequests,
//        reqValues: APIRequestsTemplates<T>
//    ): APIRequests {
//        var request_ = request as ReadAPIs<CheckResult>
//        super.defaultInitialize(request, reqValues)
//        request_.opCode = opCode
//        return request
//    }
}