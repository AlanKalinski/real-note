package me.kalinski.realnote.storage.data

import android.os.Parcel
import android.os.Parcelable
import me.kalinski.utils.adapters.universalrecycler.models.BaseItemObject
import java.util.*

data class Note constructor(
        var title: String = "",
        var description: String = "",
        var editDate: Long = Date().time,
        var createDate: Long = editDate,
        var imageUrl: String? = "",
        var uid: String? = null
) : BaseItemObject, Parcelable {
    override fun differenceItem(): String = toString()

    override fun viewType(): Int = 0

    override fun toString(): String {
        return "Note(title='$title', description='$description', editDate=$editDate, createDate=$createDate, imageUrl=$imageUrl, viewType=${viewType()}, uid='${this.uid}')"
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readLong(),
            source.readLong(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeString(description)
        writeLong(editDate)
        writeLong(createDate)
        writeString(imageUrl)
        writeString(uid)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Note> = object : Parcelable.Creator<Note> {
            override fun createFromParcel(source: Parcel): Note = Note(source)
            override fun newArray(size: Int): Array<Note?> = arrayOfNulls(size)
        }
    }
}
