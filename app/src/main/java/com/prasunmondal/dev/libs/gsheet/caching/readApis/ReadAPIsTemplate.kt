package com.prasunmondal.dev.libs.gsheet.caching.readApis
interface ReadAPIsTemplate<T> :
    FetchAllTemplate<T>,
    FetchWithByAndConditionTemplate<T>,
    FetchByOrConditionTemplate<T>