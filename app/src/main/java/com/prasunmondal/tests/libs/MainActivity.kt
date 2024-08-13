package com.prasunmondal.tests.libs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.dev.libs.R
import com.prasunmondal.dev.libs.contexts.AppContexts
import com.prasunmondal.dev.libs.gsheet.clients.Tests.Test
import com.prasunmondal.dev.libs.gsheet.integrationTests.RunTest
import com.prasunmondal.dev.libs.reflections.Tests.Tests

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppContexts.set(this)

        Thread {
            RunTest().runTest(this)
        }.start()
//        testAll()
    }

    fun testAll() {

        Test.start()
        Tests()
    }
}