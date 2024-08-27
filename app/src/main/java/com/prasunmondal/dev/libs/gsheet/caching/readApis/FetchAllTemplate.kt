package com.prasunmondal.dev.libs.gsheet.caching.readApis

import android.content.Context
import com.prasunmondal.dev.libs.gsheet.caching.CachingUtils
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchAll
import com.prasunmondal.dev.libs.gsheet.clients.ClientFilter
import com.prasunmondal.dev.libs.gsheet.clients.ClientSort
import com.prasunmondal.dev.libs.gsheet.clients.GScript

class FetchAllTemplate<T>(
    override var sheetURL: String,
    override var tabname: String,
    override var query: String?,
    override var classTypeForResponseParsing: Class<T>,
    override var appendInServer: Boolean,
    override var appendInLocal: Boolean,
    override var cacheTag: String?,
    override var shallCacheData: Boolean,
    override var context: Context,
    override var filter: ClientFilter<T>?,
    override var sort: ClientSort<T>?
) : RequestTemplatesInterface<T>, CachingUtils<T> {
    override fun prepareRequest(): APIRequests {
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

    fun fetch(useCache: Boolean = true): List<T> {
        return get(context, prepareRequest() as GSheetFetchAll<T>, useCache)
    }

    fun queueFetchAll() {
        val request = prepareRequest()
        GScript.addRequest(request)
    }
}