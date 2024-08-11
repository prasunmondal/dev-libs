package com.prasunmondal.dev.libs.gsheet.caching.readApis

import com.prasunmondal.dev.libs.gsheet.caching.CachingUtils
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchByAndCondition

interface FetchWithByAndConditionTemplate<T> : RequestTemplatesInterface<T>, CachingUtils<T> {
    fun prepareFetchByAndConditionRequest(): GSheetFetchByAndCondition<T> {
        val request = GSheetFetchByAndCondition<T>()

        if (sheetURL.isNotBlank() && tabname.isNotBlank()) {
            request.sheetId(sheetURL)
            request.tabName(tabname)
            request.filter = filter
            request.sort = sort
            request.classTypeForResponseParsing = classTypeForResponseParsing
        }
        return request
    }
}