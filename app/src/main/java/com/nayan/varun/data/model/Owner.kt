package com.nayan.varun.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner (
        @SerializedName("login") var name: String,
        @SerializedName("avatar_url") var avatarUrl: String
): Parcelable