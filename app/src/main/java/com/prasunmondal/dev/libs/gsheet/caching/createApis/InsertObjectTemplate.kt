package com.prasunmondal.dev.libs.gsheet.caching.createApis

import android.content.Context
import com.prasunmondal.dev.libs.gsheet.caching.CachingUtils
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.ClientFilter
import com.prasunmondal.dev.libs.gsheet.clients.ClientSort

class InsertObjectTemplate<T : Any>(
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
    override var sort: ClientSort<T>?,
    var data: T?
) : RequestTemplatesInterface<T>, CachingUtils<T> {

    override fun prepareRequest(): APIRequests {
        val request = GSheetInsertObject()
        request.sheetId = sheetURL
        request.tabName = tabname
        request.setDataObject(data as Any)
        return request
    }

    override fun cacheUpdateOperation() {
        val request = GSheetInsertObject()
        deleteCacheObjects("${request.sheetId}//${request.tabName}")
    }
}