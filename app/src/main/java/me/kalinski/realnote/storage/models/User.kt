package me.kalinski.realnote.storage.models

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentReference

data class User(
        var displayName: String = "",
        var email: String = "",
        var phoneNumber: String = "",
        var photoUrl: String = "",
        var providerId: String = "",
        var uid: String = email,
        var notes: List<DocumentReference> = emptyList()
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            ArrayList<DocumentReference>().apply { source.readList(this, DocumentReference::class.java.classLoader) }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(displayName)
        writeString(email)
        writeString(phoneNumber)
        writeString(photoUrl)
        writeString(providerId)
        writeString(uid)
        writeList(notes)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}