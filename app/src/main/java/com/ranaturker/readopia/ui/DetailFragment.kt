package com.ranaturker.readopia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                    val action = DetailFragmentDirections.actionDetailFragmentToReadingFragment(
                        book.formats?.textplainCharsetusAscii?:""
                    )
                    findNavController().navigate(action)
                }
            }
        }

    }

}
