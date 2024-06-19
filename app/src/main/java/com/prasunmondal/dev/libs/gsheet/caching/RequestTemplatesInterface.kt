package com.prasunmondal.dev.libs.gsheet.caching

interface RequestTemplatesInterface<T>{

    var scriptURL: String
    var sheetURL: String
    var tabname: String
    var query: String?
    var classTypeForResponseParsing: Class<T>
    var appendInServer: Boolean
    var appendInLocal: Boolean
    var getEmptyListIfEmpty: Boolean
    var cacheTag: String
}