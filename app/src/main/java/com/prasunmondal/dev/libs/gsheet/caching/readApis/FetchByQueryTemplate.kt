package com.prasunmondal.dev.libs.gsheet.caching.readApis

import android.content.Context
import com.prasunmondal.dev.libs.gsheet.caching.CachingUtils
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequest
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchByQuery
import com.prasunmondal.dev.libs.gsheet.clients.ClientFilter
import com.prasunmondal.dev.libs.gsheet.clients.ClientSort

class FetchByQueryTemplate<T>(
    override var context: Context,
    override var sheetId: String,
    override var tabname: String,
    override var query: String?,
    override var modelClass: Class<T>,
    override var appendInServer: Boolean,
    override var appendInLocal: Boolean,
    override var cacheTag: String?,
    override var shallCacheData: Boolean,
    override var filter: ClientFilter<T>?,
    override var sort: ClientSort<T>?,
) : RequestTemplatesInterface<T>, CachingUtils<T> {

    override fun prepareRequest(): APIRequest {
        val request = GSheetFetchByQuery<T>(context)

        if (sheetId.isNotBlank() && tabname.isNotBlank()) {
            request.sheetId(sheetId)
            request.tabName(tabname)
            request.query = query!!
            request.filter = filter
            request.sort = sort
            request.modelClass = modelClass
        }
        return request
    }
}