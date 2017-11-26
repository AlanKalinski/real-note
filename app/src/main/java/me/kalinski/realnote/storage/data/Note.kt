package me.kalinski.realnote.storage.data

import android.os.Parcel
import android.os.Parcelable
import me.kalinski.utils.adapters.universalrecycler.models.BaseItemObject
import java.util.*

data class Note constructor(
        val title: String,
        val description: String,
        val editDate: Date,
        val createDate: Date = editDate,
        val imageUrl: String? = null
) : BaseItemObject, Parcelable {
    override val viewType: Int = 0

    val id: String = UUID.randomUUID().toString()

    override fun getDifferenceItem(): String = toString()

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readSerializable() as Date,
            source.readSerializable() as Date,
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeString(description)
        writeSerializable(editDate)
        writeSerializable(createDate)
        writeString(imageUrl)
    }

    override fun toString(): String {
        return "Note(title='$title', description='$description', editDate=$editDate, createDate=$createDate, imageUrl=$imageUrl, viewType=$viewType, id='$id')"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Note> = object : Parcelable.Creator<Note> {
            override fun createFromParcel(source: Parcel): Note = Note(source)
            override fun newArray(size: Int): Array<Note?> = arrayOfNulls(size)
        }
    }
}
