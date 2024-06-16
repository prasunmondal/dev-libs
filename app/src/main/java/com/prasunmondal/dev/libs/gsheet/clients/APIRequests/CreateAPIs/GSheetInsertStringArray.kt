package com.prasunmondal.libs.gsheet.clients.APIRequests.CreateAPIs

import com.tech4bytes.extrack.DB.clients.ListUtils
import org.json.JSONObject

class GSheetInsertStringArray : CreateAPIs() {

    private var dataSequence: MutableList<String> = mutableListOf()
    override fun getJSON(): JSONObject {
        val postDataParams = JSONObject()
        postDataParams.put("opCode", "INSERT_DATA_SEQUENCE")
        postDataParams.put("sheetId", this.sheetId)
        postDataParams.put("tabName", this.tabName)
        postDataParams.put("dataValue", "[${ListUtils.getCSV(this.dataSequence)}]")
        return postDataParams
    }

    fun dataObject(dataSequence: String) {
        this.dataSequence.add(dataSequence)
    }

    fun dataObject(dataSequence: List<String>) {
        this.dataSequence = dataSequence as MutableList<String>
    }
}