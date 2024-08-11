package com.prasunmondal.dev.libs.gsheet.clients

import com.prasunmondal.dev.libs.gsheet.clients.Tests.ModelInsertObject

class ClientFilter<T>(
    var filterName: String,
    var filter: ((List<T>) -> List<T>)? = null
)