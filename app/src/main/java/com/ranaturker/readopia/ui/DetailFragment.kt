package com.ranaturker.readopia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.ranaturker.readopia.R
import com.ranaturker.readopia.databinding.FragmentDetailBinding
import com.ranaturker.readopia.network.Result

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = binding.progressBar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        viewModel.getBookWithId(args.bookId)

        val navController = findNavController()
        toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }

        viewModel.book.observe(viewLifecycleOwner) { book ->
            if (book != null) {
                showCopyrightStatus(book)
                val authorName = book.authors?.get(0)?.name ?: "Unknown Author"
                val languages = book.languages?.joinToString(", ")
                val subjects = book.subjects
                val bookshelf = book.bookshelves?.toString()?: "This book has no bookshelf"
                with(binding) {
                    bookName.text = book.title
                    authorTextView.text = authorName
                    languageTextView.text = languages
                    bookshelves.text = bookshelf
                    content.text = subjects?.joinToString(",")
                    downloadCount.text = book.downloadCount.toString()
                    imageViewBook.load(book.formats?.imageJpeg) {
                        placeholder(R.drawable.book_wallpaper)
                        error(R.drawable.ic_error_image)
                    }
                    imageViewBookBg.load(book.formats?.imageJpeg) {
                        placeholder(R.drawable.book_wallpaper)
                        error(R.drawable.ic_error_image)
                    }
                    button.setOnClickListener {
                        if (book.formats?.textHtmlUtf8 != null) {
                            val action =
                                DetailFragmentDirections.actionDetailFragmentToReadingFragment(
                                    book.formats.textHtmlUtf8
                                )
                            findNavController().navigate(action)
                        } else if (book.formats?.textHtml != null) {
                            val action =
                                DetailFragmentDirections.actionDetailFragmentToReadingFragment(
                                    book.formats.textHtml
                                )
                            findNavController().navigate(action)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "There is no reading page.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun showCopyrightStatus(result: Result) = with(binding) {
        val isTrue = result.copyright ?: false
        val statusIcon = if (isTrue) {
            R.drawable.ic_copyright
        } else {
            R.drawable.ic_forbidden
        }
        imageViewStatus.load(statusIcon)
    }
}
