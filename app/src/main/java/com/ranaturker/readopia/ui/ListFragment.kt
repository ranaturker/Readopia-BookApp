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
import com.ranaturker.readopia.network.Result

class ListFragment : Fragment(), BookAdapter.RecyclerViewEvent {
    private lateinit var binding: FragmentListBinding
    private lateinit var bookAdapter: BookAdapter
    private val viewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookAdapter = BookAdapter(emptyList(), this@ListFragment)
        recyclerView.adapter = bookAdapter

        viewModel.getBooks()
        viewModel.books.observe(viewLifecycleOwner) { books ->
            if (books != null) {
                bookAdapter.updateData(books)
            }

        }
    }

    override fun onItemClick(bookId: Int) {
        findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment(bookId))
    }
}

