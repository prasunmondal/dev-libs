package com.prasunmondal.dev.libs.gsheet.clients

class ClientSort<T>(
    var sortName: String,
    var sort: ((List<T>) -> List<T>)? = null
)