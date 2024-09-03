package com.prasunmondal.dev.libs.contexts

import android.content.Context
import com.prasunmondal.dev.libs.logs.instant.terminal.LogMe

class AppContexts {

    companion object {
        var contexts: MutableMap<String, Context> = mutableMapOf()

        fun set(obj: Any, context: Context) {
            val className = obj.javaClass.canonicalName.replace(".Companion", "")
            LogMe.log("Setting context: $className")
            contexts[className] = context
        }

        fun set(context: Context) {
            val className = context.javaClass.canonicalName.replace(".Companion", "")
            LogMe.log("Setting context: $className")
            contexts[className] = context
        }

        @JvmStatic
        fun get(): Context {
            val getCallerClassname = getCaller()
            var contextResult: Context? = null

            LogMe.log("No Contexts Found.")

            contexts.forEach { classname, context ->
                if (getCallerClassname.startsWith(classname))
                    contextResult = context
            }
            if (contextResult == null) {
                contextResult = contexts[contexts.keys.first()]!!.applicationContext
            }

            if(contextResult == null) {
                LogMe.log("")
                LogMe.log("No Contexts Found.")
                LogMe.log("Caller Classname: $getCallerClassname")
                LogMe.log("Available Contexts: ")
                contexts.forEach { classname, context ->
                    LogMe.log(classname)
                }
                LogMe.log("")
                LogMe.log("Caller Classname Stacktrace: ")
                getCaller(true)
                LogMe.log("")
            }
            return contextResult!!
        }

        fun getCaller(printStackTrace: Boolean = false): String {
            val stackTraceElements = Thread.currentThread().stackTrace
            if (printStackTrace) {
                stackTraceElements.forEach { trace ->
                    println(trace.toString())
                }
            }
            var traceClassnameMatch = ""
            stackTraceElements.forEach { trace ->
                contexts.forEach { classname, context ->
                    var clazzName = classname.replace(".Companion", "")
                    if (trace.className.startsWith(clazzName)) {
                        if (traceClassnameMatch.isEmpty())
                            traceClassnameMatch = clazzName
                    }
                }
            }
            return traceClassnameMatch
        }
    }
}