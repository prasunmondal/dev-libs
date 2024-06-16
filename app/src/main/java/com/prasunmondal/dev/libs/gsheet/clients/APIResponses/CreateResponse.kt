package com.prasunmondal.libs.gsheet.clients.APIResponses

class CreateResponse : APIResponse() {
    lateinit var sheetId: String
    lateinit var tabName: String
    lateinit var data: String
    var appendInServer: Boolean = true


}