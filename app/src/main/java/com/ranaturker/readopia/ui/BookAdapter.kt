package com.ranaturker.readopia.ui

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ranaturker.readopia.R
import com.ranaturker.readopia.databinding.BookItemBinding
import com.ranaturker.readopia.network.Result

class BookAdapter(
    private var bookList: List<Result?>?,
    val listener: RecyclerViewEvent
) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = BookItemBinding.inflate(LayoutInflater.from(parent.context))

        return BookViewHolder(binding)
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

    inner class BookViewHolder(val binding: BookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val id = bookList?.get(position)?.id
                    if (id != null) {
                        listener.onItemClick(id)
                    }
                }
            }
        }

        fun bind(book: Result?) = with(binding) {
            if (book != null) {

                val defaultCardBgColor = root.context.getColor(R.color.background)

                nameTextView.text = book.title
                val authorName = book.authors?.get(0)?.name ?: "Unknown Author"
                authorTextView.text = authorName
                imageViewBook.load(book.formats?.imageJpeg) {
                    placeholder(R.drawable.book_wallpaper)
                    error(R.drawable.ic_error_image)
                    allowHardware(false)
                    listener(
                        onSuccess = { _, result ->
                            // Create the palette on a background thread.
                            Palette.Builder(result.drawable.toBitmap()).generate { palette ->
                                if (palette != null) {
                                    val color = palette.getDominantColor(defaultCardBgColor)

                                    val gradientDrawable = GradientDrawable().apply {
                                        setColor(color)
                                        cornerRadius =
                                            root.context.resources.getDimension(R.dimen.margin_12)
                                    }

                                    constraintLayoutContainer.background = gradientDrawable
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    interface RecyclerViewEvent {
        fun onItemClick(bookId: Int)
    }
}