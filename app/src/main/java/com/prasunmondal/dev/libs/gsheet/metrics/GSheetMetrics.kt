package com.prasunmondal.dev.libs.gsheet.metrics

class GSheetMetrics {
    companion object {
        var callCounter = 0
        var requestsQueued = 0
        var requestsProcessed = 0
    }
}

data class GSheetMetricsState(var callCounter: Int, var requestsQueued: Int, var requestsProcessed: Int) {

    companion object {
        fun getState(): GSheetMetricsState {
            return GSheetMetricsState(
                GSheetMetrics.callCounter,
                GSheetMetrics.requestsQueued,
                GSheetMetrics.requestsProcessed
            )
        }
    }
}