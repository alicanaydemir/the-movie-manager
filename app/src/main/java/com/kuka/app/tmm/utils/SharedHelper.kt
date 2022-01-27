package com.kuka.app.tmm.utils

import android.content.SharedPreferences
import androidx.annotation.NonNull
import androidx.annotation.Nullable

class SharedHelper(private val sharedPreferences: SharedPreferences) {

    fun putBooleanData(@NonNull key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBooleanData(@NonNull key: String, defVal: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defVal)
    }

    fun putIntData(@NonNull key: String, @Nullable value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getIntData(@NonNull key: String, @Nullable defVal: Int): Int? {
        return sharedPreferences.getInt(key, defVal)
    }

    fun putFloatData(@NonNull key: String, @Nullable value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    fun getFloatData(@NonNull key: String, @Nullable defVal: Float): Float? {
        return sharedPreferences.getFloat(key, defVal)
    }

    fun putStringData(@NonNull key: String, @Nullable value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getStringData(@NonNull key: String, @Nullable defVal: String?): String? {
        return sharedPreferences.getString(key, defVal)
    }

    fun getSharedPref(): SharedPreferences {
        return sharedPreferences
    }

    /**
     * Check shared preferences contains given key
     */
    fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    /**
     * Deletes specific data in [SharedPreferences]
     */
    fun removeData(@NonNull key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    /**
     * Clear all shared preferences
     */
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

}