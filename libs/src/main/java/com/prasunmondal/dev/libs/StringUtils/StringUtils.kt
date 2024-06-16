package com.prasunmondal.dev.libs.StringUtils

import java.util.UUID

class StringUtils {

    companion object {
        fun generateUniqueString(): String {
            val currentTimeMillis = System.currentTimeMillis()
            return UUID.randomUUID().toString() + "-" + currentTimeMillis
        }
    }
}