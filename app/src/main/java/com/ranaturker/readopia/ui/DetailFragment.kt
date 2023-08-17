package com.ranaturker.readopia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ranaturker.readopia.databinding.FragmentDetailBinding

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

        viewModel.getBookWithId(args.bookId)

        viewModel.book.observe(viewLifecycleOwner) { book ->
            if (book != null) {
                binding.name.text = book.title
            }
        }
        viewModel.book.observe(viewLifecycleOwner) { book ->
            if (book != null) {
                binding.name.text = book.title

                binding.button.setOnClickListener {
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

}
