package com.prasunmondal.dev.libs.caching

interface CacheInterface {

    fun getFilename(): String

    fun save(): String

    fun get(): Object
}