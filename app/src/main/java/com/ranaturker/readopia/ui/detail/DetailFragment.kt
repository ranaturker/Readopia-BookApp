package com.ranaturker.readopia.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.ranaturker.readopia.R
import com.ranaturker.readopia.databinding.FragmentDetailBinding
import com.ranaturker.readopia.network.model.Result
import com.ranaturker.readopia.util.PrefUtil

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        PrefUtil.initPref(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListener()
        observeViewModel()
        getBooks()
    }

    private fun setClickListener() = with(binding) {
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setViews(book: Result?) = with(binding) {
        if (book != null) {
            val authorName = book.authors?.get(0)?.name ?:  getString(R.string.unknown_author)
            val languages = book.languages?.joinToString()
            val subjects = book.subjects?.take(4)
            val bookshelf =
                book.bookshelves?.let { shelves ->
                    if (shelves.isNotEmpty()) {
                        shelves.joinToString(",")
                    } else {
                        getString(R.string.this_book_has_no_bookshelf)
                    }
                } ?: getString(R.string.this_book_has_no_bookshelf)

            textViewBookName.text = book.title
            textViewAuthor.text = authorName
            textViewLanguages.text = languages
            textViewBookshelves.text = bookshelf.removePrefix("[").removeSuffix("]")
            textViewContent.text = subjects?.joinToString("\n")
            textViewDownloadCount.text = book.downloadCount.toString()

            imageViewBook.load(book.formats?.imageJpeg) {
                placeholder(R.drawable.img_book_wallpaper)
                error(R.drawable.ic_error_image)
            }
            imageViewBookBackground.load(book.formats?.imageJpeg) {
                placeholder(R.drawable.img_book_wallpaper)
                error(R.drawable.ic_error_image)
            }

            book.id?.let {
                if (PrefUtil.checkBook(id = it)) {
                    buttonRead.text = getString(R.string.book_detail_continue_button_text)
                }
            }

            buttonRead.setOnClickListener {
                book.id?.let { bookId ->
                    PrefUtil.saveBookId(bookId = bookId)

                    if (book.formats?.textHtmlUtf8 != null) {
                        val action =
                            DetailFragmentDirections.toReadingFragment(
                                book.formats.textHtmlUtf8,
                                bookId
                            )
                        findNavController().navigate(action)
                    } else if (book.formats?.textHtml != null) {
                        val action =
                            DetailFragmentDirections.toReadingFragment(
                                book.formats.textHtml,
                                bookId
                            )
                        findNavController().navigate(action)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.there_is_no_reading_page),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            showCopyrightStatus(book)
        }
    }

    private fun observeViewModel() = with(binding) {
        viewModel.book.observe(viewLifecycleOwner) { book ->
            setViews(book)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun getBooks() {
        if (viewModel.book.value == null) {
            viewModel.getBookWithId(args.bookId)
        }
    }

    private fun showCopyrightStatus(result: Result) = with(binding) {
        val isTrue = result.copyright ?: false
        val statusIcon = if (isTrue) {
            R.drawable.ic_copyright
        } else {
            R.drawable.ic_forbidden
        }
        imageViewCopyrightStatus.load(statusIcon)
    }
}