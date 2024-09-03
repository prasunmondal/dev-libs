package com.prasunmondal.dev.libs.gsheet.caching.readApis

import com.prasunmondal.dev.libs.gsheet.caching.CachingUtils
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchByAndCondition

interface FetchByOrConditionTemplate<T> : RequestTemplatesInterface<T>, CachingUtils<T> {
    override fun prepareRequest(): APIRequests {
        val request = GSheetFetchByAndCondition<T>(context)

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