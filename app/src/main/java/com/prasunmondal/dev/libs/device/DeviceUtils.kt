package com.prasunmondal.dev.libs.device

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.provider.Settings
import java.util.UUID

object DeviceUtils {

    private val uniqueDeviceId: String? = null

    val isEmulator: Boolean
        get() =
            (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                    || Build.FINGERPRINT.startsWith("generic")
                    || Build.FINGERPRINT.startsWith("unknown")
                    || Build.HARDWARE.contains("goldfish")
                    || Build.HARDWARE.contains("ranchu")
                    || Build.MODEL.contains("google_sdk")
                    || Build.MODEL.contains("Emulator")
                    || Build.MODEL.contains("Android SDK built for x86")
                    || Build.MANUFACTURER.contains("Genymotion")
                    || Build.PRODUCT.contains("sdk_google")
                    || Build.PRODUCT.contains("google_sdk")
                    || Build.PRODUCT.contains("sdk")
                    || Build.PRODUCT.contains("sdk_x86")
                    || Build.PRODUCT.contains("sdk_gphone64_arm64")
                    || Build.PRODUCT.contains("vbox86p")
                    || Build.PRODUCT.contains("emulator")
                    || Build.PRODUCT.contains("simulator"))


    fun getUniqueID(context: Context): String {
        
        if(uniqueDeviceId != null) {
            return uniqueDeviceId
        }

        val PREF_UNIQUE_ID = "PREF_UNIQUE_ID"
        val DEFAULT_ANDROID_ID = "9774d56d682e549c"

        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_UNIQUE_ID, Context.MODE_PRIVATE)

        // Check if the unique ID is already stored in SharedPreferences
        val storedID = sharedPreferences.getString(PREF_UNIQUE_ID, null)
        if (storedID != null) {
            return storedID
        }

        // Generate a new unique ID
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        val newID: String = if (DEFAULT_ANDROID_ID == androidId) {
            // Generate a random UUID if the default value is detected
            UUID.randomUUID().toString()
        } else {
            // Generate a UUID based on the Android ID
            UUID.nameUUIDFromBytes(androidId.toByteArray()).toString()
        }

        // Store the unique ID in SharedPreferences
        sharedPreferences.edit().putString(PREF_UNIQUE_ID, newID).apply()
        return newID
    }
}
