package com.example.bookslite.domain

import com.example.bookslite.domain.models.Book
import retrofit2.Response
import retrofit2.http.GET

interface BookService {
    @GET(".")
    suspend fun getBooks(): Response<List<Book>>
}