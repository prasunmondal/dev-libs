package com.prasunmondal.dev.libs.gsheet.clients

import com.prasunmondal.dev.libs.gsheet.ContextWrapper
import com.prasunmondal.dev.libs.gsheet.caching.createApis.InsertObjectTemplate
import com.prasunmondal.dev.libs.gsheet.caching.deleteApis.DeleteAPIsTemplate
import com.prasunmondal.dev.libs.gsheet.caching.readApis.FetchAllTemplate
import com.prasunmondal.dev.libs.gsheet.post.serializable.PostObjectResponse
import java.util.function.Consumer

open class GSheetSerialized<T : Any>(
    var context: ContextWrapper,
    var scriptURL: String,
    var sheetId: String,
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

//    val _context = context as Context

    fun fetchAll(): FetchAllTemplate<T> {
        return FetchAllTemplate(
            context.get, sheetId, tabName, query, classTypeForResponseParsing,
            appendInServer, appendInLocal, cacheTag, shallCacheData, filter, sort)
    }

    fun insert(obj: T): InsertObjectTemplate<T> {
        return InsertObjectTemplate(
            context.get, sheetId, tabName, query, classTypeForResponseParsing,
            appendInServer, appendInLocal, cacheTag, shallCacheData, filter, sort, obj)
    }

//    fun insert(obj: List<T>, context: Context = AppContexts.get()): InsertObjectTemplate<T> {
//        return InsertObjectTemplate(
//            sheetURL, tabName, query, classTypeForResponseParsing,
//            appendInServer, appendInLocal, cacheTag, shallCacheData, context, filter, sort, obj)
//    }

    fun deleteAll(): DeleteAPIsTemplate<T> {
        return DeleteAPIsTemplate(
            context.get, sheetId, tabName, query, classTypeForResponseParsing,
            appendInServer, appendInLocal, cacheTag, shallCacheData, filter, sort)
    }
}