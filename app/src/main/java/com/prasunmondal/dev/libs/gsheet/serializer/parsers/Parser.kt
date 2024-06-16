package com.prasunmondal.libs.gsheet.serializer.parsers

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.prasunmondal.libs.logs.instant.terminal.LogMe
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type

class Parser {

    companion object {

        open fun convertJsonArrayStringToJsonObjList(jsonArrayString: String?): List<JSONObject> {
            val jsonObjectList: MutableList<JSONObject> = ArrayList()
            try {
                val jsonArray = JSONArray(jsonArrayString)
                for (i in 0 until jsonArray.length()) {
                    jsonObjectList.add(jsonArray.getJSONObject(i))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return jsonObjectList
        }

        //        fun <T> JsonArrayToObjectArray(jsonString: String, clazz: Class<T>): List<T> {
//            val jsonArrayString = jsonString.trimIndent()
//            val gson = Gson()
//            val jsonArray = JsonParser().parse(jsonArrayString).asJsonArray
//            val contentListType: Type = TypeToken.getParameterized(MutableList::class.java, clazz).type
//            val t: List<T> = gson.fromJson(jsonArray, contentListType)
//            return t
//        }
        fun <T> convertJsonArrayStringToJavaObjList(jsonString: String, clazz: Class<T>): List<T> {
            LogMe.log("Parsing string in 'convertJsonArrayStringToJavaObjList': " + jsonString)
            val jsonArrayString = jsonString.trimIndent()
            val gson = Gson()
            val jsonArray = JsonParser().parse(jsonArrayString).asJsonArray
            val contentListType: Type =
                TypeToken.getParameterized(MutableList::class.java, clazz).type
            val t: List<T> = gson.fromJson(jsonArray, contentListType)
            return t
        }

    }
}