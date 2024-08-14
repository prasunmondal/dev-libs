package com.prasunmondal.dev.libs.gsheet.clients

import android.content.Context
import com.prasunmondal.dev.libs.gsheet.caching.createApis.InsertAPIsTemplate
import com.prasunmondal.dev.libs.gsheet.caching.deleteApis.DeleteAPIsTemplate
import com.prasunmondal.dev.libs.gsheet.caching.readApis.ReadAPIsTemplate
import com.prasunmondal.dev.libs.gsheet.post.serializable.PostObjectResponse
import java.util.function.Consumer

open class GSheetSerialized<T>(
    override var context: Any,
    override var scriptURL: String,
    override var sheetURL: String,
    override var tabname: String,
    override var classTypeForResponseParsing: Class<T>,
    override var appendInServer: Boolean,
    override var appendInLocal: Boolean,
    override var query: String? = null,
    override var shallCacheData: Boolean = true,
    override var cacheTag: String? = null,
    override var onCompletion: Consumer<PostObjectResponse>? = null,
    override var filter: ClientFilter<T>? = null,
    override var sort: ClientSort<T>? = null,
) : ReadAPIsTemplate<T>, DeleteAPIsTemplate<T>, InsertAPIsTemplate<T>