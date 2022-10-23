package com.yazhiyue.stylish.data

import com.squareup.moshi.Json

data class ProductListResult(
    val data: List<Product>,
    @Json(name = "next_paging") val nextPaging: Int?
)