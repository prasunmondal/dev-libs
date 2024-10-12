package com.prasunmondal.dev.libs.caching.fileOps

import android.content.Context
import com.prasunmondal.dev.libs.caching.CacheFileName
import com.prasunmondal.dev.libs.caching.CacheFilesList
import com.prasunmondal.dev.libs.caching.CacheModel
import com.prasunmondal.dev.libs.files.FileOps
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

open class CacheFileOps : CacheFileName() {
    fun saveToFile(
        cacheKey: String,
        cache: MutableMap<String, MutableMap<String, CacheModel>>,
        context: Context
    ) {
        LogMe.log("Saving cache data - File: ${getFileName(cacheKey)}")
        val filename = getFileName(cacheKey)
        FileOps.write(context, filename, cache)
        CacheFilesList.addToCacheFilesList(context, filename)
    }

    fun getFromFile(
        context: Context,
        cacheKey: String
    ): MutableMap<String, MutableMap<String, CacheModel>> {
        return try {
            val result = FileOps.read(
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