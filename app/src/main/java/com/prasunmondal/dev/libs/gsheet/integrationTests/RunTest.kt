package com.prasunmondal.dev.libs.gsheet.integrationTests

import android.content.Context
import com.prasunmondal.dev.libs.gsheet.integrationTests.ReadAPIs.TestDataFetchAll

class RunTest {

    fun runTest(context: Context) {
        TestDataFetchAll().main(context);
    }
}