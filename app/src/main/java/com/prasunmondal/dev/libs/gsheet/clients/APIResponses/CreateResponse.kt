package com.prasunmondal.dev.libs.gsheet.clients.APIResponses

import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse

class CreateResponse : APIResponse() {
    lateinit var sheetId: String
    lateinit var tabName: String
    lateinit var data: String
    var appendInServer: Boolean = true


}