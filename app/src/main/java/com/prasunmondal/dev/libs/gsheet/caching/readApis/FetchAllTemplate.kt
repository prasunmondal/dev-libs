package com.prasunmondal.dev.libs.gsheet.caching.readApis

import com.prasunmondal.dev.libs.contexts.AppContexts
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchAll
import com.prasunmondal.dev.libs.gsheet.clients.GScript
import com.prasunmondal.dev.libs.gsheet.caching.CachingUtils

interface FetchAllTemplate<T>: RequestTemplatesInterface<T>, CachingUtils<T> {
    fun prepareFetchAllRequest(): GSheetFetchAll<T> {
        val request = GSheetFetchAll<T>()

        if (sheetURL.isNotBlank() && tabname.isNotBlank()) {
            request.sheetId(sheetURL)
            request.tabName(tabname)
            request.classTypeForResponseParsing = classTypeForResponseParsing
        }
        return request
    }

    fun fetchAll(useCache: Boolean = true): List<T> {
        return get(AppContexts.get(), prepareFetchAllRequest(), useCache)
    }

    fun queueFetchAll() {
        val request = prepareFetchAllRequest()
        GScript.addRequest(request)
    }
}