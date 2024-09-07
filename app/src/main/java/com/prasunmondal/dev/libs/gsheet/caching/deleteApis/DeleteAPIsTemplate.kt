package com.prasunmondal.dev.libs.gsheet.caching.deleteApis

import android.content.Context
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequest
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.DeleteAPIs.GSheetDeleteAll
import com.prasunmondal.dev.libs.gsheet.clients.ClientFilter
import com.prasunmondal.dev.libs.gsheet.clients.ClientSort

class DeleteAPIsTemplate<T>(
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
    override var sort: ClientSort<T>?
) : RequestTemplatesInterface<T> {
    override fun prepareRequest(): APIRequest {
        val request = GSheetDeleteAll(context)
        if (sheetId.isNotBlank() && tabname.isNotBlank()) {
            request.sheetId(sheetId)
            request.tabName(tabname)
        }
        return request
    }

    override fun cacheUpdateOperation() {
        val request = GSheetInsertObject(context)
        deleteCacheObjects(context, "${request.sheetId}//${request.tabName}")
    }
}