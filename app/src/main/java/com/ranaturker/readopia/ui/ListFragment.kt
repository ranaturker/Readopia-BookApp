package com.ranaturker.readopia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ranaturker.readopia.databinding.FragmentListBinding

class ListFragment : Fragment(), BookAdapter.RecyclerViewEvent {
    private lateinit var binding: FragmentListBinding
    private lateinit var bookAdapter: BookAdapter
    private val viewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookAdapter = BookAdapter(emptyList(), this@ListFragment)
        recyclerView.adapter = bookAdapter

        viewModel.getBooks()
        viewModel.uiState.observe(viewLifecycleOwner) { books ->
            if (books != null) {
                when (books) {
                    UIState.Loading -> {
                        repeat(61) { index ->
                            binding.progressIndicator.progress = index
                        }
                    }
                    is UIState.Success -> {
                        bookAdapter.updateData(books.data)
                    }
                }
            }
        }
    }
    override fun onItemClick(bookId: Int) {
        findNavController().navigate(
            ListFragmentDirections.actionListFragmentToDetailFragment(
                bookId
            )
        )
    }
}

