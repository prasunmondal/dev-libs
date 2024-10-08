package com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData

import android.content.Context
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.ReadAPIs
import org.json.JSONObject

class GSheetFetchByOrCondition<T>(override var context: Context) : ReadAPIs<T>() {
    override var opCode = "FETCH_BY_CONDITION_OR"
    private var conditionOrColumn = ""
    private var conditionOrValue = ""


    override fun getJSON(): JSONObject {
        val postDataParams = JSONObject()
        postDataParams.put("opCode", opCode)
        postDataParams.put("sheetId", this.sheetId)
        postDataParams.put("tabName", this.tabName)
        postDataParams.put("dataColumn", this.conditionOrColumn)
        postDataParams.put("dataValue", this.conditionOrValue)
        return postDataParams
    }

    fun conditionOr(conditionColumn: String, conditionValue: String) {
        if (conditionColumn.isEmpty() || conditionValue.isEmpty())
            return

        if (this.conditionOrColumn.isNotEmpty()) {
            this.conditionOrColumn += ","
            this.conditionOrValue += ","
        }
        this.conditionOrColumn += conditionColumn
        this.conditionOrValue += conditionValue
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