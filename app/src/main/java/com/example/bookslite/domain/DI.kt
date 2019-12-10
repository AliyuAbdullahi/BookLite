package com.example.bookslite.domain

import com.example.bookslite.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiComponent {
    val okHttpClient: OkHttpClient
    val retrofit: Retrofit
    val bookService: BookService
}

object ApiModule : ApiComponent {
    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    override val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(httpLoggingInterceptor)
        .build()

    override val retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

    override val bookService = retrofit.create(BookService::class.java)
}

fun api(): ApiComponent = ApiModule
