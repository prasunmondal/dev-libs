package com.prasunmondal.dev.libs.gsheet.clients.APIRequests.DeleteAPIs

import org.json.JSONObject

class GSheetDeleteByAndCondition : DeleteAPIs() {
    private var conditionAndColumn = ""
    private var conditionAndValue = ""
    override fun getJSON(): JSONObject {
        val postDataParams = JSONObject()
        postDataParams.put("opCode", "DELETE_CONDITIONAL_AND")
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
}