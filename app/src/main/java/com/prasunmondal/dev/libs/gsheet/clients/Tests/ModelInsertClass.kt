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