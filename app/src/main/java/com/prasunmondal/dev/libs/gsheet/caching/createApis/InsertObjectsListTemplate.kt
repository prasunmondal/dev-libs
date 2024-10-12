package com.prasunmondal.dev.libs.gsheet.caching.createApis

import android.content.Context
import com.prasunmondal.dev.libs.gsheet.caching.CachingUtils
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequest
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObjects
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchAll
import com.prasunmondal.dev.libs.gsheet.clients.ClientFilter
import com.prasunmondal.dev.libs.gsheet.clients.ClientSort

class InsertObjectsListTemplate<T : Any>(
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
    var data: List<T>,
    var uId: String = ""
) : RequestTemplatesInterface<T>, CachingUtils<T> {
    override fun prepareRequest(): List<APIRequest> {
        val requestsList: MutableList<APIRequest> = mutableListOf()

        // insert
        val insertRequest = GSheetInsertObjects(context)
        insertRequest.sheetId = sheetId
        insertRequest.tabName = tabname
        insertRequest.setDataObject(data as Any)
        insertRequest.setUId(uId)
        requestsList.add(insertRequest)

        // fetch
        val fetchRequest = GSheetFetchAll<T>(context)
        if (sheetId.isNotBlank() && tabname.isNotBlank()) {
            fetchRequest.sheetId(sheetId)
            fetchRequest.tabName(tabname)
            fetchRequest.filter = filter
            fetchRequest.sort = sort
            fetchRequest.modelClass = modelClass
            fetchRequest.useCache = false
        }
        requestsList.add(fetchRequest)

        return requestsList
    }
}