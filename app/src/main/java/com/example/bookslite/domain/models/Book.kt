package com.example.bookslite.domain.models

data class Book(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)