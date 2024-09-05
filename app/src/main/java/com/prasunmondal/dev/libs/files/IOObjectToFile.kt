package com.prasunmondal.dev.libs.files

import android.content.Context
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe.log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class IOObjectToFile {
    @Synchronized
    @Throws(IOException::class)
    fun WriteObjectToFile(context: Context, fileName: String, obj: Any?) {
        log("Writing to file: $fileName")
        log("Writing to file content: ${obj ?: "null"}")
        var fos: FileOutputStream? = null
        var os: ObjectOutputStream? = null
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            os = ObjectOutputStream(fos)
            os.writeObject(obj)
        } catch (e: Exception) {
            println("Error while writing object to file: $fileName")
            println(e)
            throw e
        } finally {
            assert(os != null)
            os!!.close()
            fos!!.close()
        }
    }

    fun ReadObjectFromFile(context: Context, fileName: String): Any? {
        log("Reading from file: $fileName")
        try {
            val fis = context.openFileInput(fileName)
            val `is` = ObjectInputStream(fis)
            val `object` = `is`.readObject()
            `is`.close()
            fis.close()
            return `object`
        } catch (e: Exception) {
            println("Error while reading object from file")
            println(e)
        }
        return null
    }

    companion object {
        fun deleteObjectFromFile(fileName: String) {
            var fileName = fileName
            try {
                fileName = "/$fileName"
                println(fileName)
                val fdelete = File(fileName)
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
                        println("file Deleted :$fileName")
                    } else {
                        println("file not Deleted :$fileName")
                    }
                } else {
                    println("file Deleted not exist:")
                }
            } catch (e: Exception) {
                System.err.println("Delete error: ")
                e.printStackTrace()
            }
        }
    }
}
