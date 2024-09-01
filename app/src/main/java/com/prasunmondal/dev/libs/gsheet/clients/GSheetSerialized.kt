package com.prasunmondal.dev.libs.gsheet.clients

import android.content.Context
import com.prasunmondal.dev.libs.contexts.AppContexts
import com.prasunmondal.dev.libs.gsheet.caching.createApis.InsertObjectTemplate
import com.prasunmondal.dev.libs.gsheet.caching.deleteApis.DeleteAPIsTemplate
import com.prasunmondal.dev.libs.gsheet.caching.readApis.FetchAllTemplate
import com.prasunmondal.dev.libs.gsheet.post.serializable.PostObjectResponse
import java.util.function.Consumer

open class GSheetSerialized<T : Any>(
    var scriptURL: String,
    var sheetURL: String,
    var tabName: String,
    var classTypeForResponseParsing: Class<T>,
    var appendInServer: Boolean,
    var appendInLocal: Boolean,
    var query: String? = null,
    var shallCacheData: Boolean = true,
    var cacheTag: String? = null,
    var onCompletion: Consumer<PostObjectResponse>? = null,
    var filter: ClientFilter<T>? = null,
    var sort: ClientSort<T>? = null
) {

    fun fetchAll(context: Context = AppContexts.get()): FetchAllTemplate<T> {
        return FetchAllTemplate(
            sheetURL, tabName, query, classTypeForResponseParsing,
            appendInServer, appendInLocal, cacheTag, shallCacheData, context, filter, sort)
    }

    fun insert(obj: T, context: Context = AppContexts.get()): InsertObjectTemplate<T> {
        return InsertObjectTemplate(
            sheetURL, tabName, query, classTypeForResponseParsing,
            appendInServer, appendInLocal, cacheTag, shallCacheData, context, filter, sort, obj)
    }

    fun deleteAll(context: Context = AppContexts.get()): DeleteAPIsTemplate<T> {
        return DeleteAPIsTemplate(
            sheetURL, tabName, query, classTypeForResponseParsing,
            appendInServer, appendInLocal, cacheTag, shallCacheData, context, filter, sort)
    }
}