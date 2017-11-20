package me.kalinski.utils.extensions

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.widget.Toast

fun Activity.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

inline fun <reified T : Activity> Activity.navigate(id: String? = null, sharedView: View? = null,
                                                    transitionName: String? = null) {
    val intent = Intent(this, T::class.java)
    id?.let {
        intent.putExtra("id", id)
    }

    var options: ActivityOptionsCompat? = null

    if (sharedView != null && transitionName != null) {
        options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedView, transitionName)
    }

    ActivityCompat.startActivity(this, intent, options?.toBundle())
}

fun Activity.getNavigationId(): String {
    val intent = intent
    return intent.getStringExtra("id")
}