package com.prasunmondal.libs.gsheet.clients.responseCaching

import com.prasunmondal.libs.app.contexts.AppContexts
import com.prasunmondal.libs.caching.CentralCacheObj
import com.prasunmondal.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.libs.gsheet.clients.APIRequests.DeleteAPIs.GSheetDeleteAll
import com.prasunmondal.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchAll
import com.prasunmondal.libs.gsheet.clients.APIRequests.ReadAPIs.ReadAPIs
import com.prasunmondal.libs.gsheet.clients.GScript
import com.prasunmondal.libs.logs.instant.terminal.LogMe
import com.tech4bytes.extrack.centralCache.CentralCache

open class APIRequestsTemplates<T> : CachingUtils {

    val apiTemplates: MutableMap<String, APIRequests> = mutableMapOf()

    @Transient
    var scriptURL: String

    @Transient
    var sheetURL: String

    @Transient
    var tabname: String

    @Transient
    var query: String?

    @Transient
    var classTypeForResponseParsing: Class<T>

    @Transient
    var appendInServer: Boolean

    @Transient
    var appendInLocal: Boolean

    @Transient
    var getEmptyListIfEmpty: Boolean

    @Transient
    var cacheTag: String = "default"

    constructor(
        scriptURL: String,
        sheetURL: String,
        tabname: String,
        query: String? = null,
        classTypeForResponseParsing: Class<T>,
        appendInServer: Boolean,
        appendInLocal: Boolean,
        getEmptyListIfNoResultsFoundInServer: Boolean = false,
        cacheTag: String = "default"
    ) {
        this.scriptURL = scriptURL
        this.sheetURL = sheetURL
        this.tabname = tabname
        this.query = query
        this.classTypeForResponseParsing = classTypeForResponseParsing
        this.appendInServer = appendInServer
        this.appendInLocal = appendInLocal
        this.getEmptyListIfEmpty = getEmptyListIfNoResultsFoundInServer
        this.cacheTag = cacheTag
    }


    fun prepareFetchAllRequest(): GSheetFetchAll<T> {
        val request = GSheetFetchAll<T>()

        if (sheetURL.isNotBlank() && tabname.isNotBlank()) {
            request.sheetId(sheetURL)
            request.tabName(tabname)
            request.classTypeForResponseParsing = classTypeForResponseParsing
        }
        return request
    }

    fun queueFetchAll() {
        val request = prepareFetchAllRequest()
        GScript.addRequest(request)
    }

    fun fetchAll(useCache: Boolean = true): List<T> {
        return get(AppContexts.get(), prepareFetchAllRequest(), useCache)
    }

    fun queueDeleteAll() {
        val request = prepareDeleteAllRequest()
        GScript.addRequest(request)
    }

    fun prepareDeleteAllRequest(): APIRequests {
        val request = GSheetDeleteAll()
        if (sheetURL.isNotBlank() && tabname.isNotBlank()) {
            request.sheetId(sheetURL)
            request.tabName(tabname)
            apiTemplates["DELETE_ALL"] = request
        }
        return request
    }

    fun insert(obj: T) {
        val t = prepareInsertRequest(obj)
        t.execute(this.scriptURL)
    }

    fun queueInsert(obj: List<T>) {
        val listOfReqs = prepareInsertRequest(obj)
        GScript.addRequest(listOfReqs)
    }

    fun prepareInsertRequest(obj: T): GSheetInsertObject {
        val request = GSheetInsertObject()
        request.sheetId = sheetURL
        request.tabName = tabname
        request.setDataObject(obj as Any)
        return request
    }

    fun prepareInsertRequest(obj: List<T>): List<GSheetInsertObject> {
        val requestsList: MutableList<GSheetInsertObject> = mutableListOf()
        obj.forEach {
            requestsList.add(prepareInsertRequest(it))
        }
        return requestsList
    }

    fun <T> initialize(requestToInitiallize: APIRequests): Any {
        var request = requestToInitiallize
        if (requestToInitiallize is ReadAPIs<*>) {
            request = requestToInitiallize as ReadAPIs<T>
            request.sheetId = sheetURL
            request.tabName = tabname
            request.classTypeForResponseParsing = classTypeForResponseParsing as Class<T>
            request.cacheData = true
        }
        if (requestToInitiallize is ReadAPIs<*>) {
            request = requestToInitiallize as ReadAPIs<T>
            request.sheetId = sheetURL
            request.tabName = tabname
            request.classTypeForResponseParsing = classTypeForResponseParsing as Class<T>
            request.cacheData = true
        }
        return request
    }
}