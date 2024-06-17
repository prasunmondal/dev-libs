package com.prasunmondal.dev.libs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prasunmondal.dev.libs.contexts.AppContexts
import com.prasunmondal.dev.libs.gsheet.clients.Tests.Test
import com.prasunmondal.dev.libs.reflections.Tests.Tests

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppContexts.set(this)
        testAll()
    }

    fun testAll() {

        Test.start()
        Tests()
    }
}