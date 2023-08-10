package com.ranaturker.readopia.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BooksApiService {
    val api:BooksApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://gutendex.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }
}