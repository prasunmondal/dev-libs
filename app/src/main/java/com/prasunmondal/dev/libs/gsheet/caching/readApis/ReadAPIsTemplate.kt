package com.prasunmondal.dev.libs.gsheet.caching.readApis

interface ReadAPIsTemplate<T> :
    FetchAllTemplate<T>,
    FetchWithByAndConditionTemplate<T>,
    FetchByOrConditionTemplate<T> {
        val filter: (List<T>) -> List<T>
        val sort: (List<T>) -> List<T>
    }