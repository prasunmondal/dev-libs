package com.prasunmondal.dev.libs.gsheet.clients.responseCaching

import android.content.Context
import com.prasunmondal.dev.libs.caching.CentralCacheObj
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.APIRequest
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.ReadAPIs
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.ReadResponse
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe
import java.io.Serializable

interface ResponseCache : Serializable {

    fun getCacheKey(): String

    companion object {

        fun saveToLocal(requestObj: APIRequest, responseObj: ReadResponse<*>) {
            if (requestObj is ReadAPIs<*>) {
                val cacheKey = requestObj.getCacheKey()
                LogMe.log("Expensive Operation - saving data to local: $cacheKey")
                CentralCacheObj.centralCache.put(requestObj.context, cacheKey, responseObj.parsedResponse, false)
            }
        }

        fun isCached(context: Context, requestObj: APIRequest): Boolean {
            if (requestObj is ReadAPIs<*>) {
                val cacheKey = requestObj.getCacheKey()
                LogMe.log("Expensive Operation - saving data to local: $cacheKey")
                return CentralCacheObj.centralCache.isAvailable(
                    context,
                    cacheKey,
                    true,
                    false
                )
            }
            return false
        }
    }
}