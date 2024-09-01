package com.prasunmondal.dev.libs.caching

import android.content.Context
import com.prasunmondal.dev.libs.contexts.AppContexts
import com.prasunmondal.dev.libs.files.IOObjectToFile
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe
import com.prasunmondal.dev.libs.reflections.code.current.ClassDetailsUtils
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
open class CentralCache : CacheFileOps() {

    // Map of filenames, and (contents with key)
    var cache: MutableMap<String, MutableMap<String, CacheModel>> = hashMapOf()


    fun <T> get(
        context: Context,
        key: String,
        useCache: Boolean = true,
        appendCacheKeyPrefix: Boolean = true
    ): T? {

        // if user wants to force refresh the values in the cache, pass useCache as false
        if (!useCache) {
            LogMe.log("UseCache: False (Forced to not use cached data)")
            return null
        }

        val cacheObjectKey = getCacheKey(key, appendCacheKeyPrefix)
        val cacheClassKey = getClassKey()

        // check if the value is available in local cache
        var valueFromCache = getFromCacheMemory<T>(key, appendCacheKeyPrefix)
        if (valueFromCache != null) {
            return valueFromCache
        }

        // if not available in local cache,
        // load from cache file
        CentralCacheObj.centralCache.cache =
            CentralCacheObj.centralCache.getCacheDataFromFile(context, cacheObjectKey)
        valueFromCache = getFromCacheMemory<T>(key, appendCacheKeyPrefix)
        return if (valueFromCache != null) {
            valueFromCache
        } else {
            LogMe.log("Cache Miss (key:$cacheObjectKey)")
            null
        }
    }

    fun isAvailable(
        context: Context,
        key: String,
        useCache: Boolean = true,
        appendCacheKeyPrefix: Boolean = true
    ): Boolean {

        // if user wants to force refresh the values in the cache, pass useCache as false
        if (!useCache) {
            LogMe.log("UseCache: False (Forced to not use cached data)")
            return false
        }

        val cacheObjectKey = getCacheKey(key, appendCacheKeyPrefix)

        // check if the value is available in local cache
        val isPresentInCache = getAvailableInCacheMemory(key, appendCacheKeyPrefix)
        if (isPresentInCache) {
            return true
        }

        // if not available in local cache,
        // load from cache file
        CentralCacheObj.centralCache.cache =
            CentralCacheObj.centralCache.getCacheDataFromFile(context, cacheObjectKey)

        val t = getAvailableInCacheMemory(key, appendCacheKeyPrefix)
        return t
    }

    fun getAvailableInCacheMemory(key: String, appendCacheKeyPrefix: Boolean): Boolean {
        val cacheObjectKey = getCacheKey(key, appendCacheKeyPrefix)
        val cacheClassKey = getClassKey()

        val classElements = CentralCacheObj.centralCache.cache[cacheClassKey]
        if (classElements != null && classElements.containsKey(cacheObjectKey)) {
            LogMe.log("Cache Hit (key:$cacheObjectKey)- File")
            val cacheObj = classElements[cacheObjectKey]!!
            if (cacheObj.isExpired(cacheObjectKey, cacheClassKey)) {
                return false
            }
            return true
        }
        return false
    }

    fun <T> getFromCacheMemory(key: String, appendCacheKeyPrefix: Boolean): T? {
        val cacheObjectKey = getCacheKey(key, appendCacheKeyPrefix)
        val cacheClassKey = getClassKey()

        val classElements = CentralCacheObj.centralCache.cache[cacheClassKey]
        if (classElements != null && classElements.containsKey(cacheObjectKey)) {
            LogMe.log("Cache Hit (key:$cacheObjectKey)- File")
            val cacheObj = classElements[cacheObjectKey]!!
            if (cacheObj.isExpired(cacheObjectKey, cacheClassKey)) {
                return null
            }
            return cacheObj.content as T
        }
        return null
    }

    /**
     * First try to get the value from cache,
     * if not available,
     * prepares the data, saves to cache for future use and returns it
     */
//        fun <T: Any> getOrPutNGet(context: Context, key: String, contentGenerator: Consumer<T>): T {
//            val isCacheHit = get<T>(context, key)
//            if(isCacheHit == null) {
//                var content = contentGenerator.accept() as T
//                put(key, content)
//            }
//            return content
//        }

    fun <T> put(key: String, data: T, appendCacheKeyPrefix: Boolean = true) {
        val cacheClassKey = getClassKey()
        val cacheKey = getCacheKey(key, appendCacheKeyPrefix)
        LogMe.log("Putting data to Cache - key: $cacheKey")
        val presentData = CentralCacheObj.centralCache.cache[cacheClassKey]
        if (presentData == null) {
            CentralCacheObj.centralCache.cache[cacheClassKey] = hashMapOf()
        }
        CentralCacheObj.centralCache.cache[cacheClassKey]!![cacheKey] = CacheModel(data as Any?)
        CentralCacheObj.centralCache.saveCacheDataToFile(
            cacheKey,
            CentralCacheObj.centralCache.cache
        )
    }

    fun <T> putDirect(key: String, data: T) {
        val cacheClassKey = getClassKey()
        val cacheKey = getCacheKey(key, false)
        LogMe.log("Putting data to Cache - key: $cacheKey")
        val presentData = CentralCacheObj.centralCache.cache[cacheClassKey]
        if (presentData == null) {
            CentralCacheObj.centralCache.cache[cacheClassKey] = hashMapOf()
        }
        CentralCacheObj.centralCache.cache[cacheClassKey]!![key] = CacheModel(data as Any?)
        CentralCacheObj.centralCache.saveCacheDataToFile(
            key,
            CentralCacheObj.centralCache.cache
        )
    }

    fun <T> putNGet(key: String, data: T): T {
        put(key, data)
        return data
    }

    fun invalidateFullCache() {
        CentralCacheObj.centralCache.cache.clear()
        val cacheFiles = CacheFilesList.getCacheFilesList()
        val writeObj = IOObjectToFile()
        cacheFiles.forEach {
            LogMe.log("Clearing cache: deleting file - $it")
            writeObj.WriteObjectToFile(AppContexts.get(), it, null)
        }
        CacheFilesList.clearCacheFilesList()
    }

    fun invalidateClassCache(cacheKey: String) {
        CentralCacheObj.centralCache.cache[getClassKey()] = hashMapOf()
        CentralCacheObj.centralCache.saveCacheDataToFile(
            cacheKey,
            CentralCacheObj.centralCache.cache
        )
    }

    fun <T : Any> invalidateClassCache(clazz: KClass<T>, cacheKey: String) {
        CentralCacheObj.centralCache.cache[ClassDetailsUtils.getClassName(clazz)] = hashMapOf()
        CentralCacheObj.centralCache.saveCacheDataToFile(
            cacheKey,
            CentralCacheObj.centralCache.cache
        )
    }

    fun removeCacheObjectsWhereKeyStartsWith(keyStartingString: String) {
        val keysToRemove = CentralCacheObj.centralCache.cache[getClassKey()]!!.keys.filter { it.startsWith(keyStartingString) }
        for (key in keysToRemove) {
            CentralCacheObj.centralCache.cache[getClassKey()]!!.remove(key)
            LogMe.log("Cache Op: Deleted: key: $key")
        }
        CentralCacheObj.centralCache.saveCacheDataToFile("ignoredFileName", CentralCacheObj.centralCache.cache)
    }
}