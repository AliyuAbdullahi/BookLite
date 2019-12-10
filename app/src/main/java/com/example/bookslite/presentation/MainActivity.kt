package com.example.bookslite.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookslite.R
import com.example.bookslite.domain.models.Book
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.asFlow

class MainActivity : AppCompatActivity() {

    @FlowPreview
    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this).get(BooksViewModel::class.java)

        viewModel.getBooks()

        val bookAdapter = ListAdapter()

        books.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = bookAdapter
        }

        GlobalScope.launch(Dispatchers.Main) {
            viewModel.channel.asFlow().collect(object : FlowCollector<List<Book>>{
                override suspend fun emit(value: List<Book>) {
                    Toast.makeText(this@MainActivity, "Result is $value", Toast.LENGTH_SHORT).show()
                    bookAdapter.submitList(value)
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //destroy the job
    }
}
