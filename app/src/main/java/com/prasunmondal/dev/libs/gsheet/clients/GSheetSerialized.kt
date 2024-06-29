package com.prasunmondal.dev.libs.gsheet.clients

import android.content.Context
import android.util.Log
import com.prasunmondal.dev.libs.contexts.AppContexts
import com.prasunmondal.dev.libs.gsheet.caching.createApis.InsertAPIsTemplate
import com.prasunmondal.dev.libs.gsheet.caching.deleteApis.DeleteAPIsTemplate
import com.prasunmondal.dev.libs.gsheet.caching.readApis.ReadAPIsTemplate
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse
import com.prasunmondal.dev.libs.gsheet.post.serializable.PostObjectResponse
import org.json.JSONObject
import java.util.function.Consumer

open class GSheetSerialized<T>(
    override var scriptURL: String,
    override var sheetURL: String,
    override var tabname: String,
    override var classTypeForResponseParsing: Class<T>,
    override var appendInServer: Boolean,
    override var appendInLocal: Boolean,
    override var query: String? = null,
    override var shallCacheData: Boolean = true,
    override var context: Context = AppContexts.get(),
    override var cacheTag: String? = null,
    override var onCompletion: Consumer<PostObjectResponse>? = null
) : ReadAPIsTemplate<T>, DeleteAPIsTemplate<T>, InsertAPIsTemplate<T> {

    fun <T> parseToObject(jsonString: JSONObject): APIResponse {
        Log.e("parsing to object ", jsonString.toString())
        var result = APIResponse()
        result.opId = jsonString.getString("opId")
        result.affectedRows = try {
            (jsonString.getString("affectedRows")).toInt()
        } catch (e: Exception) {
            0
        }
        result.statusCode = try {
            (jsonString.getString("statusCode")).toInt()
        } catch (e: Exception) {
            0
        }
        result.content = jsonString.getString("content")
        result.logs = jsonString.getString("logs")
        return result
    }

//    fun saveToLocal(dataObject: Any?, cacheKey: String? = getFilterName()) {
//        var finalCacheKey = cacheKey
//        if(cacheKey == null) {
//            finalCacheKey = getFilterName()
//        }
//        LogMe.log("Expensive Operation - saving data to local: $finalCacheKey")
//        if (finalCacheKey == null) {
//            finalCacheKey = getFilterName()
//        }
//        if (dataObject == null) {
//            CentralCache.put(finalCacheKey, dataObject)
//            return
//        }
//
//        val dataToSave = if (appendInLocal) {
//            var dataList = get() as ArrayList
//            dataList.addAll(arrayListOf(dataObject as T))
//            dataList = filterResults(dataList)
//            dataList = sortResults(dataList)
//            dataList
//        } else {
//            dataObject
//        }
//        CentralCache.put(finalCacheKey, dataToSave)
//    }
}