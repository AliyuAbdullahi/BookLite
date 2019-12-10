package com.example.bookslite.presentation

import androidx.lifecycle.ViewModel
import com.example.bookslite.domain.api
import com.example.bookslite.domain.models.Book
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

class BooksViewModel : ViewModel() {
    val bookService by lazy {
        api().bookService
    }

    @ExperimentalCoroutinesApi
    val channel = ConflatedBroadcastChannel<List<Book>>()

    val job = Job()

    val coroutineScope = CoroutineScope(Dispatchers.Main + job)


    @ExperimentalCoroutinesApi
    fun getBooks() {
        coroutineScope.launch {
            val result = bookService.getBooks()
            if (result.code() == 200) {
                result.body()?.let {
                    channel.send(it)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}