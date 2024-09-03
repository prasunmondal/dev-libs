package com.prasunmondal.dev.libs.gsheet.caching.createApis

import android.content.Context
import com.prasunmondal.dev.libs.caching.CentralCacheObj
import com.prasunmondal.dev.libs.gsheet.ContextKeeper
import com.prasunmondal.dev.libs.gsheet.caching.CachingUtils
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.ClientFilter
import com.prasunmondal.dev.libs.gsheet.clients.ClientSort

class InsertObjectTemplate<T : Any>(
    override var context: Context,
    override var sheetURL: String,
    override var tabname: String,
    override var query: String?,
    override var classTypeForResponseParsing: Class<T>,
    override var appendInServer: Boolean,
    override var appendInLocal: Boolean,
    override var cacheTag: String?,
    override var shallCacheData: Boolean,
    override var filter: ClientFilter<T>?,
    override var sort: ClientSort<T>?,
    var data: T
) : RequestTemplatesInterface<T>, CachingUtils<T> {

    var request = GSheetInsertObject(context)

    override fun prepareRequest(): APIRequests {
        request.sheetId = sheetURL
        request.tabName = tabname
        request.setDataObject(data as Any)
        return request
    }

    override fun cacheUpdateOperation() {
        deleteCacheObjects(context,"${request.sheetId}//${request.tabName}")
    }

    fun saveToLocal(append: Boolean = false) {
        val request = GSheetInsertObject(context)
        val list: MutableList<T> = mutableListOf()
        if(append) {
            val existingList = CentralCacheObj.centralCache.get<T>(context, "${request.sheetId}//${request.tabName}") as MutableList<T>
            list.addAll(existingList)
        }
        list.add(data)
        CentralCacheObj.centralCache.putDirect(context, "${request.sheetId}//${request.tabName}", list)
    }
}