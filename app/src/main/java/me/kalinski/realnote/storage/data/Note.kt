package me.kalinski.realnote.storage.data

import android.os.Parcel
import android.os.Parcelable
import me.kalinski.utils.adapters.universalrecycler.models.BaseItemObject
import java.util.*

data class Note constructor(
        val title: String,
        val description: String,
        val imageUrl: String? = null
) : BaseItemObject, Parcelable {
    override val viewType: Int = 0

    val id: String = UUID.randomUUID().toString()

    override fun getDifferenceItem(): String = hashCode().toString()

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeString(description)
        writeString(imageUrl)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Note> = object : Parcelable.Creator<Note> {
            override fun createFromParcel(source: Parcel): Note = Note(source)
            override fun newArray(size: Int): Array<Note?> = arrayOfNulls(size)
        }
    }
}
