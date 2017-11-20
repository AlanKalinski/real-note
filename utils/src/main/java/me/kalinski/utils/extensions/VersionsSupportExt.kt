package me.kalinski.utils.extensions

import android.os.Build

inline fun supportsVersion(code: () -> Unit, sdk: Int) {
    if (Build.VERSION.SDK_INT >= sdk) {
        code.invoke()
    }
}