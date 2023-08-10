package com.ranaturker.readopia.network

import retrofit2.Call
import retrofit2.http.GET

interface BooksApi {
    @GET("books")
    fun getBooks(): Call<List<Books>>
    @GET("books/?id")
    fun getBooksWithId(): Call<List<Books>>
}