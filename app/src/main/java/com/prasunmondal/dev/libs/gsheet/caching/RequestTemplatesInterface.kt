package com.prasunmondal.dev.libs.gsheet.caching

interface RequestTemplatesInterface<T> : ExecutionOperations<T> {
    override fun getCacheKeyForGSheet(): String {
        if (cacheTag == null || cacheTag!!.isEmpty())
            return "$sheetId\\$tabname\\$cacheTag"
        return cacheTag!!
    }
}