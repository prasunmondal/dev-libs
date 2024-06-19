package com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.CheckData.CheckResult
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.ReadAPIs
import org.json.JSONObject

class GSheetFetchByAndCondition<T> : ReadAPIs<T>() {
    override var opCode = "FETCH_BY_CONDITION_AND"
    private var conditionAndColumn = ""
    private var conditionAndValue = ""

    override fun getJSON(): JSONObject {
        val postDataParams = JSONObject()
        postDataParams.put("opCode", opCode)
        postDataParams.put("sheetId", this.sheetId)
        postDataParams.put("tabName", this.tabName)
        postDataParams.put("dataColumn", this.conditionAndColumn)
        postDataParams.put("dataValue", this.conditionAndValue)
        return postDataParams
    }

    fun conditionAnd(conditionColumn: String, conditionValue: String) {
        if (conditionColumn.isEmpty() || conditionValue.isEmpty())
            return

        if (this.conditionAndColumn.isNotEmpty()) {
            this.conditionAndColumn += ","
            this.conditionAndValue += ","
        }
        this.conditionAndColumn += conditionColumn
        this.conditionAndValue += conditionValue
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