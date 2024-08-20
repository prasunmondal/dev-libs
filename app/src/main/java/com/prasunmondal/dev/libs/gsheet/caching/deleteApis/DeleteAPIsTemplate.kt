package com.prasunmondal.dev.libs.gsheet.caching.deleteApis

import com.prasunmondal.dev.libs.gsheet.caching.RequestTemplatesInterface
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.DeleteAPIs.GSheetDeleteAll
import com.prasunmondal.dev.libs.gsheet.clients.GScript

interface DeleteAPIsTemplate<T> : RequestTemplatesInterface<T> {
    override fun prepareRequest(): APIRequests {
        val request = GSheetDeleteAll()
        if (sheetURL.isNotBlank() && tabname.isNotBlank()) {
            request.sheetId(sheetURL)
            request.tabName(tabname)
        }
        return request
    }

    fun queueDeleteAll() {
        val request = prepareRequest()
        GScript.addRequest(request)
    }
}