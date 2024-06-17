package com.prasunmondal.dev.libs.caching

class Configuration {
    object configs {
        var storagePatternType = DATA_STORING_TYPE.SINGLE_FILE
    }

    enum class DATA_LOAD_MODE {
        EAGER_LOAD,
        DEMAND_LOAD
    }

    enum class DATA_STORING_TYPE {
        SINGLE_FILE, // puts all record in a single file
        CLASS_FILES, // creates a file for every class
        CACHE_KEY
    }
}