package com.prasunmondal.dev.libs.gsheet.clients.APIRequests.DeleteAPIs

import org.json.JSONObject

class GSheetDeleteAll : DeleteAPIs() {
    override fun getJSON(): JSONObject {
        val postDataParams = JSONObject()
        postDataParams.put("opCode", "DELETE_ALL")
        postDataParams.put("sheetId", this.sheetId)
        postDataParams.put("tabName", this.tabName)
        return postDataParams
    }
}