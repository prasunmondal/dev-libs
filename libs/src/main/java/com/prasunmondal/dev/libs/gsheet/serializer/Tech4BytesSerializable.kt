package com.prasunmondal.dev.libs.gsheet.serializer

import com.prasunmondal.dev.libs.caching.CentralCacheObj
import com.prasunmondal.dev.libs.contexts.AppContexts
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.DeleteAPIs.GSheetDeleteAll
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchAll
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchByQuery
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe


abstract class Tech4BytesSerializable<T : Any> : java.io.Serializable {

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

    abstract fun getRequest(): APIRequests

    fun get(
        useCache: Boolean = true,
        getEmptyListIfEmpty: Boolean = false,
        cacheTag: String = this.cacheTag
    ): List<T> {
        val cacheKey = getFilterName(cacheTag)
        LogMe.log("Getting records: $cacheKey")
        val cacheResults = try {
            CentralCacheObj.centralCache.get<T>(AppContexts.get(), cacheKey, useCache)
        } catch (ex: ClassCastException) {
            arrayListOf(CentralCacheObj.centralCache.get<T>(AppContexts.get(), cacheKey, useCache))
        }

        LogMe.log("Getting delivery records: Cache Hit: " + (cacheResults != null))
        return if (cacheResults != null) {
            cacheResults as List<T>
        } else {
            synchronized(Tech4BytesSerializableLocks.getLock(cacheKey)!!) {
                // Synchronized code block
                println("Synchronized function called with key: $cacheKey")

                val r = getFromServer()
                var list: List<T> = listOf()
                r.forEach { k, v ->
                    LogMe.log(v.content)
                    list = parseAndSaveToCache2(v.content, cacheKey)
                }
                list.forEach {
                    LogMe.log(it.toString())
                }
                return list
//                if(r.size == 1) {
//                    return parseAndSaveToCache2(r.get(0)!!.content, cacheKey)
//                } else {
//                    listOf()
//                }
            }
        }
    }

    private fun getFromServer(): MutableMap<String, APIResponse> {
        LogMe.log("Expensive Operation - get data from server: $sheetURL - $tabname")
        val requestObj = getGetRequest()
        GScript.addRequest(requestObj)
        val response = GScript.execute(ProjectConfig.dBServerScriptURL)
        return response
    }

    private fun parseNGetResponse2(rawResponse: String): List<T> {
        LogMe.log(rawResponse)
//        val typeOfT: Type = TypeToken.getParameterized(MutableList::class.java, clazz).type
        var parsedResponse =
            APIResponse.JsonArrayToObjectArray<T>(rawResponse, classTypeForResponseParsing)

        LogMe.log(parsedResponse.size)
        if ((getEmptyListIfEmpty || this.getEmptyListIfEmpty) && parsedResponse.isEmpty())
            return listOf()
        parsedResponse = filterResults(parsedResponse)
        parsedResponse = sortResults(parsedResponse)
        return parsedResponse
    }

    fun getGetRequest(useCache: Boolean = true, cacheTag: String = this.cacheTag): APIRequests? {
        return if (useCache && isDataAvailable(cacheTag))
            null
        else
            getGetRequest()
    }

    private fun getGetRequest(): APIRequests {
        return if (query == null || query!!.isEmpty()) {
            val request = GSheetFetchAll<T>()
            request.sheetId = sheetURL
            request.tabName = tabname
            request.classTypeForResponseParsing = classTypeForResponseParsing
            request
        } else {
            val request = GSheetFetchByQuery<T>()
            request.sheetId = sheetURL
            request.tabName = tabname
            request.query = query!!
            request.classTypeForResponseParsing = classTypeForResponseParsing
            request
        }
    }

    fun isDataAvailable(cacheTag: String = "default"): Boolean {
        val useCache = true
        val cacheKey = getFilterName(cacheTag)
        LogMe.log("Getting records: " + cacheKey)
        val cacheResults = try {
            CentralCacheObj.centralCache.get<T>(AppContexts.get(), cacheKey, useCache)
        } catch (ex: ClassCastException) {
            arrayListOf(CentralCacheObj.centralCache.get<T>(AppContexts.get(), cacheKey, useCache))
        }
        return cacheResults != null
    }

    fun parseAndSaveToCache2(response: String, cacheKey: String? = null): List<T> {
        val resolvedCacheKey = if (cacheKey.isNullOrEmpty()) {
            getFilterName()
        } else {
            cacheKey
        }
        val parsedData = parseNGetResponse2(response)
        CentralCacheObj.centralCache.put(resolvedCacheKey, parsedData)
        LogMe.log("Put Complete")
        LogMe.log("cacheKey: $resolvedCacheKey")
        LogMe.log(parsedData.size)
        return parsedData
    }

    private fun getFilterName(cacheTag: String = "default"): String {
//        var callerClassName = ClassDetailsUtils.getCaller(ClassDetailsUtils.getCaller())
        return "$sheetURL/$tabname/$cacheTag"
    }

    open fun <T : Any> filterResults(list: ArrayList<T>): ArrayList<T> {
        return list
    }

    open fun <T : Any> filterResults(list: List<T>): List<T> {
        return list
    }

    open fun <T : Any> sortResults(list: ArrayList<T>): ArrayList<T> {
        return list
    }

    open fun <T : Any> sortResults(list: List<T>): List<T> {
        return list
    }

    fun <T : Any> saveToLocalThenServer(dataObject: T) {
        saveToLocal(dataObject)
        saveToServer(dataObject)
    }

    /*
    *
    *
    *
    * Save Data Code
    *
    *
    *
     */
    fun saveToServerThenLocal(dataObject: T) {
        saveToServer(dataObject)
        saveToLocal(dataObject)
    }

    /**
     * dataObject: Data to save
     * cacheKey: cacheKey used to identify the cache object, pass null to generate the cacheKey
     */
    fun saveToLocal(dataObject: Any?, cacheKey: String? = getFilterName()) {
        var finalCacheKey = cacheKey
        if (cacheKey == null) {
            finalCacheKey = getFilterName()
        }
        LogMe.log("Expensive Operation - saving data to local: $finalCacheKey")
        if (finalCacheKey == null) {
            finalCacheKey = getFilterName()
        }
        if (dataObject == null) {
            CentralCacheObj.centralCache.put(finalCacheKey, dataObject)
            return
        }

        val dataToSave = if (appendInLocal) {
            var dataList = get() as ArrayList
            dataList.addAll(arrayListOf(dataObject as T))
            dataList = filterResults(dataList)
            dataList = sortResults(dataList)
            dataList
        } else {
            dataObject
        }
        CentralCacheObj.centralCache.put(finalCacheKey, dataToSave)
    }

    fun <T> saveToServer(obj: T) {
        LogMe.log("Expensive Operation - saving data to server: $sheetURL - $tabname")
        if (!appendInServer) {
            deleteDataFromServer()
        }

        val requestObj = getSaveRequest(obj)
        requestObj.execute(ProjectConfig.dBServerScriptURL)
    }

    private fun <T> getSaveRequest(obj: T): GSheetInsertObject {
        var request = GSheetInsertObject()
        request.sheetId = sheetURL
        request.tabName = tabname
        request.setDataObject(obj as Any)
        return request
    }


    /*
    *
    *
    *
    *
    * Deletion Codes
    *
    *
    *
     */

    fun deleteData() {
        deleteDataFromServer()
        deleteDataFromLocal()
    }

    fun deleteDataFromLocal() {
        saveToLocal(null)
    }

    fun deleteDataFromServer() {
        val requestObj = getDeleteRequest()

        GScript.addRequest(requestObj)
        GScript.execute(ProjectConfig.dBServerScriptURL)
    }

    fun getDeleteRequest(): GSheetDeleteAll {
        val deleteRequest = GSheetDeleteAll()
        deleteRequest.sheetId = sheetURL
        deleteRequest.tabName = tabname
        deleteRequest.execute(ProjectConfig.dBServerScriptURL)
        return deleteRequest
    }
}