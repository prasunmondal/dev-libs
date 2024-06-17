package com.prasunmondal.dev.libs.caching

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