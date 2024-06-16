package com.prasunmondal.dev.libs.gsheet

class ListUtils {

    companion object {
        fun getCSV(list: MutableList<String>): String {
            var s = ""
            for (data in list) {
                if (list.get(0) != data)
                    s = "$s,"
                s = "$s$data"
            }
            return s
        }
    }
}