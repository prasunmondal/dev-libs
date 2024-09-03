package com.prasunmondal.dev.libs.gsheet

import android.content.Context

class ContextWrapper(private var context: Context) {

    val get: Context
        get() {
            return context
        }
}