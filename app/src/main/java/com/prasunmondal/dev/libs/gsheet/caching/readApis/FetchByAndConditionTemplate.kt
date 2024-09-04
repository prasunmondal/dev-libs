package com.prasunmondal.dev.libs.gsheet.caching.readApis

import com.prasunmondal.dev.libs.gsheet.caching.CachingUtils
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchByAndCondition

interface FetchWithByAndConditionTemplate<T> : RequestTemplatesInterface<T>, CachingUtils<T> {
    override fun prepareRequest(): APIRequests {
        val request = GSheetFetchByAndCondition<T>(context)

        if (sheetId.isNotBlank() && tabname.isNotBlank()) {
            request.sheetId(sheetId)
            request.tabName(tabname)
            request.filter = filter
            request.sort = sort
            request.modelClass = modelClass
        }
        return request
    }
}