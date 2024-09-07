package com.prasunmondal.dev.libs.gsheet.clients

import com.prasunmondal.dev.libs.gsheet.ContextWrapper
import com.prasunmondal.dev.libs.gsheet.caching.createApis.InsertObjectTemplate
import com.prasunmondal.dev.libs.gsheet.caching.deleteApis.DeleteAPIsTemplate
import com.prasunmondal.dev.libs.gsheet.caching.readApis.FetchAllTemplate
import com.prasunmondal.dev.libs.gsheet.caching.readApis.FetchByQueryTemplate
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.dev.libs.gsheet.post.serializable.PostObjectResponse
import java.util.function.Consumer

open class GSheetSerialized<T : Any>(
    var context: ContextWrapper,
    var scriptURL: String = ProjectConfig.dBServerScriptURL,
    var sheetId: String,
    var tabName: String,
    var modelClass: Class<T>,
    var appendInServer: Boolean = true,
    var appendInLocal: Boolean = true,
    var query: String? = null,
    var shallCacheData: Boolean = true,
    var cacheTag: String? = null,
    var onCompletion: Consumer<PostObjectResponse>? = null,
    var filter: ClientFilter<T>? = null,
    var sort: ClientSort<T>? = null
) {
    fun fetchAll(useCache: Boolean = true): FetchAllTemplate<T> {
        return FetchAllTemplate(
            context.get, sheetId, tabName, modelClass, useCache, query,
            appendInServer, appendInLocal, cacheTag, shallCacheData, filter, sort)
    }

    fun fetchByQuery(query: String? = this.query, useCache: Boolean = true): FetchByQueryTemplate<T> {
        return FetchByQueryTemplate(
            context.get, sheetId, tabName, modelClass, useCache, query,
            appendInServer, appendInLocal, cacheTag, shallCacheData, filter, sort)
    }

    fun save(list: List<T>) {
        deleteAll()
    }

    fun insert(obj: T): InsertObjectTemplate<T> {
        return InsertObjectTemplate(
            context.get, sheetId, tabName, query, modelClass,
            appendInServer, appendInLocal, cacheTag, shallCacheData, filter, sort, obj)
    }

//    fun insert(obj: List<T>, context: Context = AppContexts.get()): InsertObjectTemplate<T> {
//        return InsertObjectTemplate(
//            sheetURL, tabName, query, classTypeForResponseParsing,
//            appendInServer, appendInLocal, cacheTag, shallCacheData, context, filter, sort, obj)
//    }

    fun deleteAll(): DeleteAPIsTemplate<T> {
        return DeleteAPIsTemplate(
            context.get, sheetId, tabName, query, modelClass,
            appendInServer, appendInLocal, cacheTag, shallCacheData, filter, sort)
    }
}