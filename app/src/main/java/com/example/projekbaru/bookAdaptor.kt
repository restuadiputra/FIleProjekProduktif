package com.example.projekbaru

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class bookAdaptor(private var book: List<book>, context: Context): RecyclerView.Adapter<bookAdaptor.BookViewHolder>() {

    private val db: BookDatabaseHelper = BookDatabaseHelper(context)

    class BookViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nama : TextView = itemView.findViewById(R.id.txtNama)
        val foto : ImageView = itemView.findViewById(R.id.Photo)
        val updateButton : ImageView = itemView.findViewById(R.id.btnEdit)
        val deleteButton : ImageView = itemView.findViewById(R.id.btnDelete)
        val profile : CardView = itemView.findViewById(R.id.profile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int = book.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = book[position]
        holder.nama.text = book.name

        if (book.image != null) {
            val bmp = BitmapFactory.decodeByteArray(book.image, 0, book.image.size)
            holder.foto.setImageBitmap(bmp)
        } else {
            holder.foto.setImageResource(R.drawable.logo_24)
        }

        holder.profile.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java).apply {
                putExtra("book_id", book.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateBook::class.java).apply {
                putExtra("book_id", book.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteBook(book.id)
            refreshData(db.getAllBooks())
            Toast.makeText(holder.itemView.context, "Note Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newBooks : List<book>) {
        book = newBooks
        notifyDataSetChanged()
    }
}