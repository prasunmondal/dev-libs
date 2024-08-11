package com.prasunmondal.dev.libs.gsheet.clients.APIResponses

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.prasunmondal.dev.libs.gsheet.clients.JsonTags
import org.json.JSONObject
import java.io.Serializable
import java.lang.reflect.Type

open class APIResponse : Serializable {
    var content: String = ""
    var statusCode: Int = 0
    var affectedRows = 0
    var opId = ""
    var logs = ""

//    fun getContent(): String {
//        return this.content
//    }

//    fun getStatusCode(): Int {
//        return statusCode
//    }

    fun getUID(): Int {
        return getJsonObject()!!.get(JsonTags.RESPONSE_OP_CODE).asInt
    }

    fun getJsonObject(): JsonObject? {
        val parser = JsonParser()
        return parser.parse(content).asJsonObject
    }

    fun getExceptionMessage(): String {
        return ""
    }

    fun getExceptionStackTrace(): String {
        return ""
    }

    fun isOperationLocked(): Boolean {
        return getJsonObject()!!.get(JsonTags.RESPONSE_IS_LOCKED_OPERATION).asBoolean
    }

    override fun toString(): String {
        return "APIResponse(content='$content', statusCode=$statusCode, affectedRows=$affectedRows, opId='$opId', logs='$logs')"
    }

    fun <T> parseToObj(classTypeForResponseParsing: Class<T>): List<T> {
        return com.prasunmondal.dev.libs.jsons.JsonParser.convertJsonArrayStringToJavaObjList(
            this.content,
            classTypeForResponseParsing
        )
    }

    companion object {
        fun <T> JsonArrayToObjectArray(jsonString: String, clazz: Class<T>): List<T> {
            val jsonArrayString = jsonString.trimIndent()
            val gson = Gson()
            val jsonArray = JsonParser().parse(jsonArrayString).asJsonArray
            val contentListType: Type =
                TypeToken.getParameterized(MutableList::class.java, clazz).type
            val t: List<T> = gson.fromJson(jsonArray, contentListType)
            return t
        }

        fun parseToAPIResponse(jsonString: JSONObject): APIResponse {
            Log.e("parsing to object ", jsonString.toString())
            var result = APIResponse()
            result.opId = jsonString.getString("opId")
            result.affectedRows = try {
                (jsonString.getString("rowsAffected")).toInt()
            } catch (e: Exception) {
                0
            }
            result.statusCode = try {
                (jsonString.getString("statusCode")).toInt()
            } catch (e: Exception) {
                0
            }
            result.content = jsonString.getString("content")
            result.logs = jsonString.getString("logs")
            return result
        }
    }
}