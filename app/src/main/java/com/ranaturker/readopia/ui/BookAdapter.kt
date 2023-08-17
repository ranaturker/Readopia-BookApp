package com.ranaturker.readopia.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ranaturker.readopia.R
import com.ranaturker.readopia.network.Result

class BookAdapter(
    private var bookList: List<Result?>?,
    val listener: RecyclerViewEvent
) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList?.get(position)
        holder.bind(book)
    }

    override fun getItemCount(): Int {
        return bookList?.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<Result?>?) {
        bookList = newList
        notifyDataSetChanged()
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val id = bookList?.get(position)?.id
                    if (id != null) {
                        listener.onItemClick(id)
                    }
                }
            }
        }

        fun bind(book: Result?) {
            if (book != null) {
                nameTextView.text = book.title
            }
        }
    }

    interface RecyclerViewEvent {
        fun onItemClick(bookId: Int)
    }
}
