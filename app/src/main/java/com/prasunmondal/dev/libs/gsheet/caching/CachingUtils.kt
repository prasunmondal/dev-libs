package com.prasunmondal.dev.libs.gsheet.caching

import android.content.Context
import com.prasunmondal.dev.libs.caching.CentralCacheObj
import com.prasunmondal.dev.libs.gsheet.clients.APIRequests.ReadAPIs.ReadAPIs
import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.ReadResponse
import com.prasunmondal.dev.libs.gsheet.clients.Tests.TestBulkOps.TestSheet1Model.scriptURL
import com.prasunmondal.dev.libs.gsheet.serializer.Tech4BytesSerializableLocks
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

interface CachingUtils<T> {
    fun <T> getMultiple(context: Context, request: ReadAPIs<T>, useCache: Boolean): List<T> {
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

    fun get(request: ReadAPIs<T>, useCache: Boolean): List<T> {
        val cacheKey = request.getCacheKey()
        var cacheResults = readFromCache(request.context, request, useCache)

        LogMe.log("Getting delivery records: Cache Hit: " + (cacheResults != null))
        return if (cacheResults != null) {
            cacheResults as List<T>
        } else {
            synchronized(Tech4BytesSerializableLocks.getLock(cacheKey)!!) {
                // Synchronized code block
                println("Synchronized function called with key: $cacheKey")
                val response = request.executeOne(scriptURL, request)
                if (response == null)
                    listOf()
                else {
                    val parsedResponse = (response as ReadResponse<T>).parsedResponse
                    saveToCache(request.context, cacheKey, parsedResponse, false)
                    parsedResponse
                }
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

    fun saveToCache(context: Context, cacheKey: String, obj: List<T>, appendCacheKeyPrefix: Boolean = true) {
        CentralCacheObj.centralCache.put(context, cacheKey, obj, appendCacheKeyPrefix)
    }

    fun <T> insert(obj: List<T>) {

    }

    fun deleteCacheObjects(context: Context, whereKeyStartsWith: String) {
        CentralCacheObj.centralCache.removeCacheObjectsWhereKeyStartsWith(context, whereKeyStartsWith)
    }

    fun cacheUpdateOperation() {

    }
}