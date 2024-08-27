package com.prasunmondal.dev.libs.gsheet.caching.deleteApis

import android.content.Context
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.DeleteAPIs.GSheetDeleteAll
import com.prasunmondal.dev.libs.gsheet.clients.ClientFilter
import com.prasunmondal.dev.libs.gsheet.clients.ClientSort

class DeleteAPIsTemplate<T>(
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
) : RequestTemplatesInterface<T> {
     override fun prepareRequest(): APIRequests {
        val request = GSheetDeleteAll()
        if (sheetURL.isNotBlank() && tabname.isNotBlank()) {
            request.sheetId(sheetURL)
            request.tabName(tabname)
        }
        return request
    }
}