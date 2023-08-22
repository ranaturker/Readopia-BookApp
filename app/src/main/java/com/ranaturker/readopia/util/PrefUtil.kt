package com.ranaturker.readopia.util

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PrefUtil {
    lateinit var pref: SharedPreferences

    private fun getEditor(): Editor {
        return pref.edit()
    }

    fun initPref(context: Context) {
        pref = context.getSharedPreferences("readopia", Context.MODE_PRIVATE)
    }

    fun isReading(id: Int): Boolean {
        Log.d("bookId", "id: $id")
        val bookIds = pref.getString(BOOK_KEY, null) ?: return false
        val listType = object : TypeToken<List<Int>>() {}.type
        val bookIdList: MutableList<Int> = Gson().fromJson(bookIds, listType)
        Log.d("bookId", bookIdList.joinToString())
        return bookIdList.contains(id)
    }

    fun setBookPref(id: Int) {
        if (!pref.contains(BOOK_KEY)) {
            val bookIds = mutableListOf<Int>()
            bookIds.add(id)
            val bookIdsJson = Gson().toJson(bookIds)
            getEditor().putString(BOOK_KEY, bookIdsJson)
            getEditor().apply()
        } else {
            val bookIds = pref.getString(BOOK_KEY, "")
            val listType = object : TypeToken<List<String>>() {}.type
            val bookIdList: MutableList<Int> = Gson().fromJson(bookIds, listType)
            bookIdList.add(id)
            val bookIdsJson = Gson().toJson(bookIds)
            getEditor().putString(BOOK_KEY, bookIdsJson)
            getEditor().apply()
        }
    }

    const val BOOK_KEY = "books"
}

data class BookState(val id: Int)