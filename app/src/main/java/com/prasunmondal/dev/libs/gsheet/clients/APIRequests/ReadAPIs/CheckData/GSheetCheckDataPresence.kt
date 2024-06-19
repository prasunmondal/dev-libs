package com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.CheckData

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.ReadAPIs
import org.json.JSONObject

// TODO: fix - returns 500 when sheet is empty
class GSheetCheckDataPresence : ReadAPIs<CheckResult>() {
    private var keys = ""
    private var values = ""
    override var classTypeForResponseParsing = CheckResult::class.java
    override var opCode = "IS_PRESENT_CONDITIONAL_AND"

    fun keys(keys: String) {
        this.keys = keys
    }

    fun values(values: String) {
        this.values = values
    }

    override fun getJSON(): JSONObject {
        val postDataParams = JSONObject()
        postDataParams.put("opCode", opCode)
        postDataParams.put("sheetId", this.sheetId)
        postDataParams.put("tabName", this.tabName)
        postDataParams.put("dataColumn", keys)
        postDataParams.put("dataValue", values)
        return postDataParams
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