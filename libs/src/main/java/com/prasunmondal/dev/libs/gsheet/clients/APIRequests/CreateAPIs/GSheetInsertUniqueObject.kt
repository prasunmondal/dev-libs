package com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs

import com.google.gson.Gson
import org.json.JSONObject

class GSheetInsertUniqueObject : CreateAPIs() {
    private lateinit var dataObject: Any
    private var uniqueColumn = "";

    fun setDataObject(dataObject: Any) {
        this.dataObject = dataObject
    }

    fun uniqueColumn(uniqueColumn: String) {
        if (this.uniqueColumn != "")
            this.uniqueColumn += ","
        this.uniqueColumn += uniqueColumn
    }

    override fun getJSON(): JSONObject {
        val postDataParams = JSONObject()
        postDataParams.put("opCode", "INSERT_OBJECT_UNIQUE")
        postDataParams.put("sheetId", this.sheetId)
        postDataParams.put("tabName", this.tabName)
        postDataParams.put("objectData", Gson().toJson(this.dataObject))
        postDataParams.put("uniqueCol", this.uniqueColumn)
        return postDataParams
    }
}