package com.prasunmondal.libs.gsheet.clients.APIResponses

class DeleteResponse : APIResponse() {
    lateinit var sheetId: String
    lateinit var tabName: String
    lateinit var data: String
}