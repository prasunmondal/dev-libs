package com.prasunmondal.dev.libs.gsheet.clients

class ClientFilter<T>(
    var filterName: String,
    var filter: ((List<T>) -> List<T>)? = null
)