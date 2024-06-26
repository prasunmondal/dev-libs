package com.prasunmondal.dev.libs.gsheet.caching

interface RequestTemplatesInterface<T> : GSheetCaching<T> {
    override fun getCacheKey(): String {
        if (cacheTag == null || cacheTag!!.isEmpty())
            return "$sheetURL/$tabname/$cacheTag"
        return cacheTag!!
    }
}