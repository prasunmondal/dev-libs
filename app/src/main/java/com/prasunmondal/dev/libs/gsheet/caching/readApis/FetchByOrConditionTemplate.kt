package com.prasunmondal.dev.libs.gsheet.caching.readApis

import com.prasunmondal.dev.libs.gsheet.caching.CachingUtils
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequest
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchByAndCondition

interface FetchByOrConditionTemplate<T> : RequestTemplatesInterface<T>, CachingUtils<T> {
    override fun prepareRequest(): List<APIRequest> {
        val request = GSheetFetchByAndCondition<T>(context)

        if (sheetId.isNotBlank() && tabname.isNotBlank()) {
            request.sheetId(sheetId)
            request.tabName(tabname)
            request.filter = filter
            request.sort = sort
            request.modelClass = modelClass
        }
        return listOf(request)
    }
}