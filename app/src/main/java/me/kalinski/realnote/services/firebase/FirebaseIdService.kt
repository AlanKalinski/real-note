package me.kalinski.realnote.services.firebase

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import dagger.android.AndroidInjection
import timber.log.Timber

class FirebaseIdService : FirebaseInstanceIdService() {
    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onTokenRefresh() {
        super.onTokenRefresh()

        FirebaseInstanceId.getInstance().token?.let {
            Timber.d("FIREBASE TOKEN: $it")
        }
    }
}