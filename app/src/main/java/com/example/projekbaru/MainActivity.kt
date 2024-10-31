package com.example.projekbaru

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projekbaru.databinding.ActivityAddBookBinding
import com.example.projekbaru.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: BookDatabaseHelper
    private lateinit var booksAdapter: bookAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        db = BookDatabaseHelper(this)
        booksAdapter = bookAdaptor(db.getAllBooks(), this)
        setContentView(binding.root)

        binding.booksRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.booksRecyclerView.adapter = booksAdapter

        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddBook::class.java)
            startActivity(intent)
        }

        binding.aboutUsButton.setOnClickListener{
            val intent = Intent(this, AboutBook::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        booksAdapter.refreshData(db.getAllBooks())
    }
}