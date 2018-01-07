package me.kalinski.realnote.storage.models

import android.os.Parcel
import android.os.Parcelable
import me.kalinski.realnote.storage.data.Note
import java.util.*

data class User(
        var displayName: String = "",
        var email: String = "",
        var phoneNumber: String = "",
        var photoUrl: String = "",
        var providerId: String = "",
        var uid: String = email,
        var notes: Array<Note> = emptyArray()
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readParcelableArray(Note::class.java.classLoader) as Array<Note>
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(displayName)
        writeString(email)
        writeString(phoneNumber)
        writeString(photoUrl)
        writeString(providerId)
        writeString(uid)
        writeParcelableArray(notes, 0)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (displayName != other.displayName) return false
        if (email != other.email) return false
        if (phoneNumber != other.phoneNumber) return false
        if (photoUrl != other.photoUrl) return false
        if (providerId != other.providerId) return false
        if (uid != other.uid) return false
        if (!Arrays.equals(notes, other.notes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = displayName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        result = 31 * result + photoUrl.hashCode()
        result = 31 * result + providerId.hashCode()
        result = 31 * result + uid.hashCode()
        result = 31 * result + Arrays.hashCode(notes)
        return result
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}