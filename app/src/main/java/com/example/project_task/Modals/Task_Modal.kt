package com.example.project_task.Modals

import android.text.Editable
import android.os.Parcel
import android.os.Parcelable

data class Task_Modal(
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val id: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(date)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task_Modal> {
        override fun createFromParcel(parcel: Parcel): Task_Modal {
            return Task_Modal(parcel)
        }

        override fun newArray(size: Int): Array<Task_Modal?> {
            return arrayOfNulls(size)
        }
    }
}

