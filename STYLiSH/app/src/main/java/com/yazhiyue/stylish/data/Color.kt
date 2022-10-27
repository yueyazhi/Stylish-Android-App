package com.yazhiyue.stylish.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Color(
    val code: String,
    val name: String
) : Parcelable