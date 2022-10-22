package com.yazhiyue.stylish.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yazhiyue.stylish.data.MarketingHotsResult
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.moshi.MoshiConverterFactory


private const val BASE_URL = "https://api.appworks-school.tw/api/1.0/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface StylishApiService {
    @GET("marketing/hots")
    suspend fun getMarketingHots(): MarketingHotsResult
}



object StylishApi {
    val retrofitService : StylishApiService by lazy { retrofit.create(StylishApiService::class.java) }
}