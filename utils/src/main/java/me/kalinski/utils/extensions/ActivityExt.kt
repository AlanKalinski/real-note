package me.kalinski.utils.extensions

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import android.widget.Toast

fun Activity.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

inline fun <reified T : Activity> Activity.navigate(
        id: String? = null,
        sharedObjects: Intent? = null,
        vararg sharedElements: Pair<View, String>
) {
    val intent = Intent(this, T::class.java)
    id?.let {
        intent.putExtra("id", id)
    }

    sharedObjects?.let {
        intent.putExtras(it)
    }

    var options: ActivityOptionsCompat? = null
    if (sharedElements.isNotEmpty()) {
        options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *sharedElements)
    }

    ActivityCompat.startActivity(this, intent, options?.toBundle())
}

inline fun <reified T : Activity> Activity.navigateForResult(
        requestCode: Int,
        sharedObjects: Intent? = null,
        vararg sharedElements: Pair<View, String>
) {
    val intent = Intent(this, T::class.java)

    sharedObjects?.let {
        intent.putExtras(it)
    }

    var options: ActivityOptionsCompat? = null
    if (sharedElements.isNotEmpty()) {
        options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *sharedElements)
    }

    ActivityCompat.startActivityForResult(this, intent, requestCode, options?.toBundle())
}

fun Activity.getNavigationId(): String {
    val intent = intent
    return intent.getStringExtra("id")
}