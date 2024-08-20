package com.prasunmondal.dev.libs.gsheet.caching.readApis

import com.prasunmondal.dev.libs.gsheet.caching.CachingUtils
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchAll
import com.prasunmondal.dev.libs.gsheet.clients.GScript

interface FetchAllTemplate<T> : RequestTemplatesInterface<T>, CachingUtils<T> {
    override fun prepareRequest(): GSheetFetchAll<T> {
        val request = GSheetFetchAll<T>()

        if (sheetURL.isNotBlank() && tabname.isNotBlank()) {
            request.sheetId(sheetURL)
            request.tabName(tabname)
            request.filter = filter
            request.sort = sort
            request.classTypeForResponseParsing = classTypeForResponseParsing
        }
        return request
    }

    fun fetchAll(useCache: Boolean = true): List<T> {
        return getMultiple(context, prepareRequest(), useCache)
    }

    fun fetch(useCache: Boolean = true): List<T> {
        return get(context, prepareRequest(), useCache)
    }

    fun queueFetchAll() {
        val request = prepareRequest()
        GScript.addRequest(request)
    }
}