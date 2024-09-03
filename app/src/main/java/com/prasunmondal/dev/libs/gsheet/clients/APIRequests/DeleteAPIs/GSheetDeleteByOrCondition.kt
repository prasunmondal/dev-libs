package com.prasunmondal.dev.libs.gsheet.clients.APIRequests.DeleteAPIs

import android.content.Context
import org.json.JSONObject

class GSheetDeleteByOrCondition(override var context: Context) : DeleteAPIs() {
    private var conditionOrColumn = ""
    private var conditionOrValue = ""
    override fun getJSON(): JSONObject {
        val postDataParams = JSONObject()
        postDataParams.put("opCode", "DELETE_CONDITIONAL_OR")
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
}