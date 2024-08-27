package com.prasunmondal.dev.libs.gsheet.clients.Tests

import java.io.Serializable

class ModelInsertObject : Serializable {
    var name = ""
    var title = ""

    constructor(name: String, title: String) {
        this.name = name
        this.title = title
    }

    override fun toString(): String {
        return "ModelInsertObject(name='$name', title='$title')"
    }
}
//
//object ModelInsertClass : Tech4BytesSerializable<ModelInsertObject>(
//    ProjectConfig.dBServerScriptURL,
//    ProjectConfig.DB_SHEET_ID,
//    "TestSheet1",
//    query = null,
//    classTypeForResponseParsing = ModelInsertObject::class.java,
//    appendInServer = true,
//    appendInLocal = true
//) {
//    override fun getRequest(): APIRequests {
//        val t = GSheetFetchAll<ModelInsertObject>()
//        t.sheetId = ProjectConfig.DB_SHEET_ID
//        t.tabName = "TestSheet1"
//        t.classTypeForResponseParsing = ModelInsertObject::class.java
//        return t
//    }
//
//}