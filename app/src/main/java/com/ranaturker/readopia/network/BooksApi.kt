package com.ranaturker.readopia.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {
    @GET("books")
    fun getBooks(): Call<Books>

    @GET("books")
    fun getBookWithId(@Query("ids") id: Int): Call<Books>

}