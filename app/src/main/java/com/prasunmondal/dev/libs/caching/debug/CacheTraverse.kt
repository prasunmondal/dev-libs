package com.prasunmondal.dev.libs.caching.debug

import android.content.Context
import com.prasunmondal.dev.libs.caching.CentralCacheObj
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

class CacheTraverse {
    companion object {
        fun getAllCacheKeys(context: Context, cacheObjectKey: String = ""): MutableList<String> {
            val listOfCacheKeys = mutableListOf<String>()
            CentralCacheObj.centralCache.cache =
                CentralCacheObj.centralCache.getCacheDataFromFile(context, cacheObjectKey)

            CentralCacheObj.centralCache.cache.forEach { k, v ->
                v.forEach { k1, v1 ->
                    listOfCacheKeys.add(k1)
                    LogMe.log("CacheTag: $k1")
                }
            }
            return listOfCacheKeys
        }
    }
}