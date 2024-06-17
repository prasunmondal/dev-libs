package com.prasunmondal.dev.libs.gsheet.post.serializable

import com.prasunmondal.dev.libs.gsheet.clients.APIResponses.APIResponse

class PostObjectResponse : APIResponse {

    constructor(responsePayload: String) {
        this.content = responsePayload
    }

    fun getObject(): PostObjectResponse {
        return this
    }
}