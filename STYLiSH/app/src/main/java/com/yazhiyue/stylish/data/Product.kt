package com.yazhiyue.stylish.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Product(
    val id: Long,
    val category: String,
    val title: String,
    val description: String,
    val price: Int,
    val texture: String,
    val wash: String,
    val place: String,
    val note: String,
    val story: String,
    val colors: @RawValue List<Color>,
    val sizes: @RawValue List<String>,
    val variants: @RawValue List<Variant>,
    @Json(name = "main_image") val mainImage: String,
    val images: List<String>
): Parcelable