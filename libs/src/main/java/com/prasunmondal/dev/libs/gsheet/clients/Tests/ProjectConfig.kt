package com.prasunmondal.dev.libs.gsheet.clients.Tests

import com.prasunmondal.libs.gsheet.operations.DeploymentID.Companion.deploymentId
import java.io.FileNotFoundException

object ProjectConfig {
    @JvmField
    var DB_SHEET_ID = "1p3v4SgXPfB70YjCXCOj57BdLrDiFBoynt7yIWPQ8WmI"
    var DB_TAB_APP_OWNER = "TestSheet1"

    @JvmStatic
    @get:Throws(FileNotFoundException::class)
    val dBServerScriptURL: String
        get() = "https://script.google.com/macros/s/" + deploymentId + "/exec"
//    https://script.google.com/macros/s//exec
}