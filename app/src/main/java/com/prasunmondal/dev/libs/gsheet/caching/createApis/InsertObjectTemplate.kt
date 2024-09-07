package com.prasunmondal.dev.libs.gsheet.caching.createApis

import android.content.Context
import com.prasunmondal.dev.libs.caching.CentralCacheObj
import com.prasunmondal.dev.libs.gsheet.caching.CachingUtils
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequest
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.ClientFilter
import com.prasunmondal.dev.libs.gsheet.clients.ClientSort

class InsertObjectTemplate<T : Any>(
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
    var data: T
) : RequestTemplatesInterface<T>, CachingUtils<T> {

    var request = GSheetInsertObject(context)

    override fun prepareRequest(): APIRequest {
        request.sheetId = sheetId
        request.tabName = tabname
        request.setDataObject(data as Any)
        return request
    }

    override fun cacheUpdateOperation() {
        deleteCacheObjects(context,"${request.sheetId}//${request.tabName}")
    }

    fun saveToLocal(append: Boolean = false) {
//        val request = GSheetInsertObject(context)
        val list: MutableList<T> = mutableListOf()
        if(append) {
            val existingList = CentralCacheObj.centralCache.get<T>(context, "${sheetId}//${tabname}") as MutableList<T>
            list.addAll(existingList)
        }
        list.add(data)
        CentralCacheObj.centralCache.putDirect(context, "${sheetId}//${tabname}", list)
    }
}