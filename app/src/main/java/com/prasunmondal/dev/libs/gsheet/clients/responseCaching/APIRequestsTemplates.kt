package com.prasunmondal.dev.libs.gsheet.clients.responseCaching

import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequests
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.CreateAPIs.GSheetInsertObject
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.DeleteAPIs.GSheetDeleteAll
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.ReadAPIs
import com.prasunmondal.dev.libs.gsheet.clients.GScript

open class APIRequestsTemplates<T> {


    @Transient
    var cacheTag: String = "default"




//    fun <T> initialize(requestToInitiallize: APIRequests): Any {
//        var request = requestToInitiallize
//        if (requestToInitiallize is ReadAPIs<*>) {
//            request = requestToInitiallize as ReadAPIs<T>
//            request.sheetId = sheetURL
//            request.tabName = tabname
//            request.classTypeForResponseParsing = classTypeForResponseParsing as Class<T>
//            request.cacheData = true
//        }
//        if (requestToInitiallize is ReadAPIs<*>) {
//            request = requestToInitiallize as ReadAPIs<T>
//            request.sheetId = sheetURL
//            request.tabName = tabname
//            request.classTypeForResponseParsing = classTypeForResponseParsing as Class<T>
//            request.cacheData = true
//        }
//        return request
//    }
}