package com.ranaturker.readopia.network

import com.google.gson.annotations.SerializedName

data class Books(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("previous")
    val previous: Any? = null,
    @SerializedName("results")
    val results: List<Result?>? = null
)
data class Formats(
    @SerializedName("image/jpeg")
    val imagejpeg: String? = null,
    @SerializedName("text/plain")
    val textplain: String? = null,
    @SerializedName("text/plain; charset=iso-8859-1")
    val textplainCharsetiso88591: String? = null,
    @SerializedName("text/plain; charset=us-ascii")
    val textplainCharsetusAscii: String? = null,
    @SerializedName("text/plain; charset=utf-8")
    val textplainCharsetutf8: String? = null
)
data class Author(
    @SerializedName("birth_year")
    val birthYear: Int? = null,
    @SerializedName("death_year")
    val deathYear: Int? = null,
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
    @SerializedName("media_type")
    val mediaType: String? = null,
    @SerializedName("subjects")
    val subjects: List<String?>? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("translators")
    val translators: List<Translator?>? = null
)
data class Translator(
    @SerializedName("name")
    val name: String? = null
)