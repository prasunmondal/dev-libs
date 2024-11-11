package com.prasunmondal.dev.libs.caching

import android.content.Context
import com.prasunmondal.dev.libs.files.IOObjectToFile
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

class CacheFilesList : java.io.Serializable {
    companion object {

        private val cacheFilesFileName: String = "cacheFilesIndex.dat"

        fun getCacheFilesList(context: Context): MutableList<String> {
            val readObj = IOObjectToFile()
            val list = try {
                readObj.ReadObjectFromFile(
                    context,
                    cacheFilesFileName
                ) as MutableList<String>
            } catch (e: Exception) {
                LogMe.log("Couldn't read file: $cacheFilesFileName")
                mutableListOf()
            }
            return list
        }

        fun addToCacheFilesList(context: Context, filename: String) {
            val list = getCacheFilesList(context)
            if (list.contains(filename))
                return
            list.add(filename)
            val writeObj = IOObjectToFile()
            writeObj.WriteObjectToFile(context, cacheFilesFileName, list)
        }

        fun removeFromCacheFilesList(context: Context, classKey: String) {
            val list = getCacheFilesList(context)
            if (!list.contains(classKey))
                return
            list.remove(classKey)
            val writeObj = IOObjectToFile()
            writeObj.WriteObjectToFile(context, cacheFilesFileName, list)
        }

        fun clearCacheFilesList(context: Context) {
            val writeObj = IOObjectToFile()
            writeObj.WriteObjectToFile(context, cacheFilesFileName, null)
        }
    }
}