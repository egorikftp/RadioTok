package com.egoriku.radiotok.data.retrofit

import com.egoriku.radiotok.datasource.intertal.api.Api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val DEFAULT_ENDPOINT = "https://github.com/"

internal class ApiEndpoint(hostSelectionInterceptor: HostSelectionInterceptor) {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(hostSelectionInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(DEFAULT_ENDPOINT)
        .callFactory(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun create(): Api = retrofit.create()
}