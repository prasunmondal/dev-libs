package com.prasunmondal.dev.libs.gsheet.clients.responseCaching//package com.prasunmondal.libs.gsheet.clients.responseCaching
//
//import com.prasunmondal.dev.libs.AppContexts.AppContexts
//import com.prasunmondal.dev.libs.Logs.instant.LogMe
//import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse
//import com.prasunmondal.dev.libs.gsheet.clients.GScript
//import com.prasunmondal.dev.libs.gsheet.clients.Tests.ProjectConfig
//import com.prasunmondal.dev.libs.gsheet.serializer.Tech4BytesSerializableLocks
//import com.tech4bytes.extrack.centralCache.CentralCache
//
//interface T4BSerializable<T> {
//
//    fun getCacheKey(): String
//    fun get(
//        useCache: Boolean = true,
//        getEmptyListIfEmpty: Boolean = false,
//    ): List<T> {
//        val cacheKey = getCacheKey()
//        LogMe.log("Getting records: $cacheKey")
//        val cacheResults = try {
//            CentralCache.get<T>(AppContexts.get(), cacheKey, useCache)
//        } catch (ex: ClassCastException) {
//            arrayListOf(CentralCache.get<T>(AppContexts.get(), cacheKey, useCache))
//        }
//
//        LogMe.log("Getting delivery records: Cache Hit: " + (cacheResults != null))
//        return if (cacheResults != null) {
//            cacheResults as List<T>
//        } else {
//            synchronized(Tech4BytesSerializableLocks.getLock(cacheKey)!!) {
//                // Synchronized code block
//                println("Synchronized function called with key: $cacheKey")
//
//                val r = getFromServer()
//                var list: List<T> = listOf()
//                r.forEach { k, v ->
//                    LogMe.log(v.content)
//                    list = parseAndSaveToCache2(v.content, cacheKey)
//                }
//                list.forEach {
//                    LogMe.log(it.toString())
//                }
//                return list
////                if(r.size == 1) {
////                    return parseAndSaveToCache2(r.get(0)!!.content, cacheKey)
////                } else {
////                    listOf()
////                }
//            }
//        }
//    }
//
//    private fun getFromServer(): MutableMap<String, APIResponse> {
//        LogMe.log("Expensive Operation - get data from server: $sheetURL - $tabname")
//        val requestObj = getGetRequest()
//        GScript.addRequest(requestObj)
//        val response = GScript.execute(ProjectConfig.dBServerScriptURL)
//        return response
//    }
//}