package com.prasunmondal.dev.libs.gsheet.clients.APIRequests.DeleteAPIs

import com.prasunmondal.dev.libs.caching.CentralCacheObj
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequest

abstract class DeleteAPIs : APIRequest() {
    lateinit var sheetId: String
    lateinit var tabName: String
    lateinit var data: String
    var appendInServer: Boolean = true
    override var cacheUpdateOperation: ((APIRequest) -> Unit)? = ::cacheUpdateOperation

    fun sheetId(sheetId: String) {
        this.sheetId = sheetId
    }

    fun tabName(tabName: String) {
        this.tabName = tabName
    }

    fun cacheUpdateOperation(request: APIRequest) {
        CentralCacheObj.centralCache.removeCacheObjectsWhereKeyStartsWith(context, "$sheetId//$tabName")
    }
}