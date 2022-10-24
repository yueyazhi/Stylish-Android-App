package com.yazhiyue.stylish.data

import com.squareup.moshi.Json

data class Variant(
    @Json(name = "color_code") val colorCode: String,
    val size: String,
    val stock: Int
)