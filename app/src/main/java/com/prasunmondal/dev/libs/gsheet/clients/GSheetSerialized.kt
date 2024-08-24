package com.prasunmondal.dev.libs.gsheet.clients

import android.content.Context
import com.prasunmondal.dev.libs.gsheet.caching.ExecutionOperations
//import com.prasunmondal.dev.libs.gsheet.caching.createApis.InsertAPIsTemplate
import com.prasunmondal.dev.libs.gsheet.caching.createApis.InsertObjectTemplate
import com.prasunmondal.dev.libs.gsheet.caching.deleteApis.DeleteAPIsTemplate
import com.prasunmondal.dev.libs.gsheet.caching.readApis.FetchAllTemplate
//import com.prasunmondal.dev.libs.gsheet.caching.readApis.ReadAPIsTemplate
//import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.post.serializable.PostObjectResponse
import java.util.function.Consumer

open class GSheetSerialized<T: Any>(
    var context: Context,
    var scriptURL: String,
    var sheetURL: String,
    var tabname: String,
    var classTypeForResponseParsing: Class<T>,
    var appendInServer: Boolean,
    var appendInLocal: Boolean,
    var query: String? = null,
    var shallCacheData: Boolean = true,
    var cacheTag: String? = null,
    var onCompletion: Consumer<PostObjectResponse>? = null,
    var filter: ClientFilter<T>? = null,
    var sort: ClientSort<T>? = null,
) {
    fun fetchAll(): FetchAllTemplate<T> {
        return FetchAllTemplate<T>(sheetURL, tabname, query, classTypeForResponseParsing,
            appendInServer, appendInLocal, cacheTag, shallCacheData, context, filter, sort)
    }

    fun insert(obj: T): InsertObjectTemplate<T> {
        return InsertObjectTemplate<T>(  sheetURL, tabname, query, classTypeForResponseParsing,
          appendInServer, appendInLocal, cacheTag, shallCacheData, context, filter, sort, obj )
    }

    fun deleteAll(): ExecutionOperations<T> {
        return DeleteAPIsTemplate<T>(  sheetURL , tabname, query, classTypeForResponseParsing,
         appendInServer, appendInLocal, cacheTag, shallCacheData, context, filter, sort)
    }
}