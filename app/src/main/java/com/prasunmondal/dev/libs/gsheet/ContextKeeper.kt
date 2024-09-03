package com.prasunmondal.dev.libs.gsheet

import android.content.Context

class ContextKeeper(private var context: Context) {

    fun get(): Context {
            return context
        }
}