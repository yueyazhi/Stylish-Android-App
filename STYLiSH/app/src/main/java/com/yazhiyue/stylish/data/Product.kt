package com.yazhiyue.stylish.data

import com.squareup.moshi.Json

data class Product(
    val id: Long,
    val category: String,
    val title: String,
    val description: String,
    val price: Long,
    val texture: String,
    val wash: String,
    val place: String,
    val note: String,
    val story: String,
    val colors: List<Color>,
    val sizes: List<String>,
    val variants: List<Variant>,
    @Json(name = "main_image") val mainImage: String,
    val images: List<String>
)