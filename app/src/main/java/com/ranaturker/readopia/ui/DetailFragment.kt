package com.ranaturker.readopia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ranaturker.readopia.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookId = DetailFragmentArgs.fromBundle(requireArguments()).bookId
        if (bookId != -1) {
            viewModel.getBookWithId(bookId)
        }
        viewModel.book.observe(viewLifecycleOwner) { book ->
            if (book != null) {
                binding.name.text = book.title
            }
        }
    }
}
