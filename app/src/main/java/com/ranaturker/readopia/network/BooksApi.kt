package com.ranaturker.readopia.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface BooksApi {
    @GET("books")
    fun getBooks(): Call<Books>

    @GET("books")
    fun getBookWithId(@Query("ids") id: Int): Call<Books>

    @GET
    fun getBookContent(@Url contentUrl: String): Call<ResponseBody>
}