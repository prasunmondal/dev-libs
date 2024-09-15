package com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs

import android.content.Context
import com.google.gson.Gson
import com.prasunmondal.dev.libs.gsheet.post.serializable.PostObjectResponse
import org.json.JSONObject
import java.util.function.Consumer

class GSheetInsertObjects(override var context: Context) : CreateAPIs() {
    private lateinit var dataObject: Any
    override var onCompletion: Consumer<PostObjectResponse>? = null
    fun setDataObject(dataObject: Any): GSheetInsertObjects {
        this.dataObject = dataObject
        return this
    }

    override fun getJSON(): JSONObject {
        val postDataParams = JSONObject()
        postDataParams.put("opCode", "INSERT_OBJECTS")
        postDataParams.put("sheetId", this.sheetId)
        postDataParams.put("tabName", this.tabName)
        postDataParams.put("objectData", Gson().toJson(this.dataObject))
        return postDataParams
    }
}