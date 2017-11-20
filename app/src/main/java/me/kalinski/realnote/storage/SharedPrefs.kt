package me.kalinski.realnote.storage

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefs @Inject constructor(val context: Context) {
    private val getter: SharedPreferences = context.getSharedPreferences("repo_preferences", Context.MODE_PRIVATE)
    private val setter: SharedPreferences.Editor by lazy {
        getter.edit()
    }

    private fun clear(vararg keys: String) {
        for (key in keys)
            setter.remove(key)
        setter.apply()
    }

    private operator fun set(key: String, value: Any) {
        if (value is String) setter.putString(key, value)
        if (value is Boolean) setter.putBoolean(key, value)
        if (value is Long) setter.putLong(key, value)
        if (value is Int) setter.putInt(key, value)
        if (value is Float) setter.putFloat(key, value)
        setter.apply()
    }

    private operator fun get(key: String, defaultValue: String?): String {
        return getter.getString(key, defaultValue)
    }

    private operator fun get(key: String, defaultValue: Boolean?): Boolean {
        return getter.getBoolean(key, defaultValue ?: false)
    }

    private operator fun get(key: String, defaultValue: Long?): Long {
        return getter.getLong(key, defaultValue!!)
    }

    private operator fun get(key: String, defaultValue: Int?): Int {
        return getter.getInt(key, defaultValue!!)
    }

    private operator fun get(key: String, defaultValue: Float?): Float {
        return getter.getFloat(key, defaultValue!!)
    }

    inner class Test {
        private val TEST = "test"

        var test: String
            get() = get(TEST, "test")
            set(value) = set(TEST, value)
    }
}