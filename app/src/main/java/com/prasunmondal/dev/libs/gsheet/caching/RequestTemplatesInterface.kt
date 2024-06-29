package com.prasunmondal.dev.libs.gsheet.caching

interface RequestTemplatesInterface<T>: GSheetCaching<T> {
    override fun getCacheKey(): String {
        return "$sheetURL/$tabname/$cacheTag"
    }
}