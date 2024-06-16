package com.tech4bytes.mbrosv3.Utils.centralCache

import com.prasunmondal.libs.caching.CentralCacheObj
import com.prasunmondal.libs.logs.instant.terminal.LogMe
import com.tech4bytes.mbrosv3.Utils.Date.Date_Utils
import java.time.LocalDateTime

class CacheModel : java.io.Serializable {
    var entryTime: LocalDateTime
    var expiryTime: LocalDateTime
    var content: Any?

    constructor(content: Any?) {
        entryTime = LocalDateTime.now()
        expiryTime = Date_Utils.getNextTimeOccurrenceTimestamp(1)
        LogMe.log("$entryTime - $expiryTime")
        this.content = content
    }

    override fun toString(): String {
        return "CacheModel(entryTime=$entryTime, expiryTime=$expiryTime, content=$content)"
    }

    fun isExpired(cacheObjectKey: String, cacheClassKey: String): Boolean {
        val isExpired = this.expiryTime.isBefore(LocalDateTime.now())
        if (isExpired) {
            deletedExpiredData(cacheObjectKey, cacheClassKey)
            return true
        }
        return false
    }

    fun deletedExpiredData(cacheObjectKey: String, cacheClassKey: String) {
        LogMe.log("Data Expired (key:$cacheObjectKey)")
        LogMe.log("Deleting cache data")
        CentralCacheObj.centralCache.cache[cacheClassKey]!!.remove(cacheObjectKey)
        CentralCacheObj.centralCache.saveCacheDataToFile(
            cacheObjectKey,
            CentralCacheObj.centralCache.cache
        )
    }
}