package com.ranaturker.readopia.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranaturker.readopia.network.Books
import com.ranaturker.readopia.network.BooksApiService
import com.ranaturker.readopia.network.Result
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class DetailViewModel: ViewModel() {
    val bookLiveData = MutableLiveData<Result?>()
    val book: LiveData<Result?> = bookLiveData

    fun getBookWithId(id : Int) {
        viewModelScope.launch {
            BooksApiService.api.getBookWithId(id).enqueue(object : retrofit2.Callback<Books> {
                override fun onResponse(
                    call: Call<Books>,
                    response: Response<Books>
                ) {
                    if (response.isSuccessful) {
                        val books = response.body()
                        if (books != null) {
                            bookLiveData.postValue(books.results?.get(0))
                        }
                        if (books != null) {
                            Log.d("ranad", books.results.toString())
                        }
                    } else {
                        Log.e("failed", "Failed to fetch books: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Books>, t: Throwable) {
                    Log.e("error", "Error: ${t.message}")
                }
            })
        }
    }
}