package com.yazhiyue.stylish.data

import com.squareup.moshi.Json

data class MarketingHotsResult (
    @Json(name = "data") val hotsList: List<Hots>? = null
)

