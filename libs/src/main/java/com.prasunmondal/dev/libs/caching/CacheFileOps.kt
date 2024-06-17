package com.prasunmondal.dev.libs.caching

import android.content.Context
import com.prasunmondal.dev.libs.files.IOObjectToFile
import com.prasunmondal.dev.libs.contexts.AppContexts
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

open class CacheFileOps : CacheFileName() {
    fun saveCacheDataToFile(
        cacheKey: String,
        cache: MutableMap<String, MutableMap<String, CacheModel>>
    ) {
        LogMe.log("Saving cache data - File: ${getFileName(cacheKey)}")
        val filename = getFileName(cacheKey)
        val writeObj = IOObjectToFile()
        writeObj.WriteObjectToFile(AppContexts.get(), filename, cache)
        CacheFilesList.addToCacheFilesList(filename)
    }

    fun getCacheDataFromFile(
        context: Context,
        cacheKey: String
    ): MutableMap<String, MutableMap<String, CacheModel>> {
        return try {
            val readObj = IOObjectToFile()
            val result = readObj.ReadObjectFromFile(
                context,
                getFileName(cacheKey)
            ) as MutableMap<String, MutableMap<String, CacheModel>>
            LogMe.log("Reading records from file: ${getFileName(cacheKey)}: Successful")
            LogMe.log("Reading records from file contents: $result")
            result
        } catch (e: Exception) {
            LogMe.log("Reading records from file: ${getFileName(cacheKey)}: Failed")
            mutableMapOf()
        }
    }
}