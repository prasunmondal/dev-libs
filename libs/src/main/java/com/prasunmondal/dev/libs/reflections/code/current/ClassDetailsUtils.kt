package com.prasunmondal.dev.libs.reflections.code.current

import com.prasunmondal.dev.libs.gsheet.clients.Tests.Test
import kotlin.reflect.KClass

class ClassDetailsUtils {

    companion object {

        /**
        Returns the calling method.
        Example: com.tech4bytes.extrack.models.ExpenseData
         */
        fun getCaller(thresholdClass: String = ""): String {
            val parentPackage =
                if (thresholdClass.isNotEmpty()) thresholdClass
                else "com.prasunmondal.libs.reflections.code"

            var startSearching = false

            /*
                The stacktrace starts with some thread classes.
                So, first reach to the current package where this code is present
                and the as soon as we exit from this current package, return the first value after that
             */
            Thread.currentThread().stackTrace.forEach { trace ->
                if (!startSearching && trace.className.startsWith(parentPackage))
                    startSearching = true

                if (startSearching && !trace.className.startsWith(parentPackage))
                    return Class.forName(trace.className).name.substringBefore('$')
            }
            return ""
        }

        fun <T : Any> getClassName(clazz: KClass<T>): String {
            Test.javaClass.name
            return clazz.qualifiedName + ""
        }
    }
}