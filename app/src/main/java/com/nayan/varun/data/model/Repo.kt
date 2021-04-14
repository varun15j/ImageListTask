package com.nayan.varun.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize

@Entity(tableName = "repo" )
data class Repo (
         @SerializedName("title") var name: String?,
         @SerializedName("imageHref") var url: String?,
        @SerializedName("description") var description: String?

): Parcelable
{
    @PrimaryKey(autoGenerate = true) var id: Long = 0
}

