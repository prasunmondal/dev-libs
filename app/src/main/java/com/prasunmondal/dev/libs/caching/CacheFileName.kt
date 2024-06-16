package com.prasunmondal.libs.caching

import com.tech4bytes.extrack.centralCache.Configuration

open class CacheFileName : CacheKey() {
    fun getFileName(cacheKey: String): String {
        return "CentralCache-" + if (Configuration.configs.storagePatternType == Configuration.DATA_STORING_TYPE.CLASS_FILES) {
            getClassKey()
        } else if (Configuration.configs.storagePatternType == Configuration.DATA_STORING_TYPE.CACHE_KEY) {
            cacheKey.replace("/", "-")
        } else {
            "data.dat"
        }
    }
}