package com.prasunmondal.dev.libs.gsheet.clients.responseCaching

import android.content.Context
import com.prasunmondal.dev.libs.caching.CentralCacheObj
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.ReadAPIs
import com.prasunmondal.dev.libs.gsheet.clients.Tests.TestBulkOps.TestSheet1Model.scriptURL
import com.prasunmondal.dev.libs.gsheet.serializer.Tech4BytesSerializableLocks
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

open class CachingUtils {
    fun <T> get(context: Context, request: ReadAPIs<T>, useCache: Boolean): List<T> {
        val cacheKey = request.getCacheKey()
        var cacheResults = readFromCache(context, request, useCache)

        LogMe.log("Getting delivery records: Cache Hit: " + (cacheResults != null))
        return if (cacheResults != null) {
            cacheResults as List<T>
        } else {
            synchronized(Tech4BytesSerializableLocks.getLock(cacheKey)!!) {
                // Synchronized code block
                println("Synchronized function called with key: $cacheKey")
                request.execute(scriptURL)
                cacheResults = readFromCache(context, request, useCache)
                if (cacheResults == null)
                    listOf()
                else
                    cacheResults as List<T>
            }
        }
    }

    private fun <T> readFromCache(context: Context, request: ReadAPIs<T>, useCache: Boolean): Any? {
        val cacheKey = request.getCacheKey()
        return try {
            CentralCacheObj.centralCache.get<T>(context, cacheKey, useCache, false)
        } catch (ex: ClassCastException) {
            arrayListOf(CentralCacheObj.centralCache.get<T>(context, cacheKey, useCache, false))
        }
    }

    fun <T> insert(obj: List<T>) {

    }
}