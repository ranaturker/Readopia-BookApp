package com.ranaturker.readopia.network.model

import com.google.gson.annotations.SerializedName

//Response model
data class Books(
    @SerializedName("results")
    val results: List<Result>? = null
)

data class Formats(
    @SerializedName("image/jpeg")
    val imageJpeg: String? = null,
    @SerializedName("text/html")
    val textHtml: String? = null,
    @SerializedName("text/html; charset=utf-8")
    val textHtmlUtf8: String? = null,
)

data class Author(
    @SerializedName("name")
    val name: String? = null
)

data class Result(
    @SerializedName("authors")
    val authors: List<Author?>? = null,
    @SerializedName("bookshelves")
    val bookshelves: List<String?>? = null,
    @SerializedName("copyright")
    val copyright: Boolean? = null,
    @SerializedName("download_count")
    val downloadCount: Int? = null,
    @SerializedName("formats")
    val formats: Formats? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("languages")
    val languages: List<String?>? = null,
    @SerializedName("subjects")
    val subjects: List<String?>? = null,
    @SerializedName("title")
    val title: String? = null,
)

