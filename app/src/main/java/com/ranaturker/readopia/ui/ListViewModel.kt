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
class ListViewModel : ViewModel() {

    private val _uiState = MutableLiveData<UIState>()
    val uiState: LiveData<UIState> = _uiState

    init {
        getBooks()
    }

    private fun getBooks() {
        viewModelScope.launch {
            _uiState.postValue(UIState.Loading)
            BooksApiService.api.getBooks().enqueue(object : retrofit2.Callback<Books> {
                override fun onResponse(
                    call: Call<Books>,
                    response: Response<Books>
                ) {
                    if (response.isSuccessful) {
                        val books = response.body()
                        if (books != null) {
                            _uiState.postValue(UIState.Success(data = books.results.orEmpty()))
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

sealed class UIState {
    object Loading : UIState()
    data class Success(val data: List<Result>) : UIState()
}