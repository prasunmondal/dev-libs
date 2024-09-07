package com.prasunmondal.dev.libs.gsheet.caching.createApis

import android.content.Context
import com.prasunmondal.dev.libs.gsheet.caching.CachingUtils
import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequest
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.DeleteAPIs.GSheetDeleteAll
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.FetchData.GSheetFetchAll
import com.prasunmondal.dev.libs.gsheet.clients.ClientFilter
import com.prasunmondal.dev.libs.gsheet.clients.ClientSort

class SaveObjectsListTemplate<T : Any>(
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
    var data: List<T>
) : RequestTemplatesInterface<T>, CachingUtils<T> {

    override fun prepareRequest(): List<APIRequest> {
        val requestsList: MutableList<APIRequest> = mutableListOf()

        // delete
        val deleteRequest = GSheetDeleteAll(context)
        if (sheetId.isNotBlank() && tabname.isNotBlank()) {
            deleteRequest.sheetId(sheetId)
            deleteRequest.tabName(tabname)
        }
        requestsList.add(deleteRequest)

        // save the list
        data.forEach {
            val insertRequest = GSheetInsertObject(context)
            insertRequest.sheetId = sheetId
            insertRequest.tabName = tabname
            insertRequest.setDataObject(it as Any)
            requestsList.add(insertRequest)
        }


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
        return requestsList.toList()
    }
}