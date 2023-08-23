package com.ranaturker.readopia.ui.list

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ranaturker.readopia.R
import com.ranaturker.readopia.databinding.FragmentListBinding
import com.ranaturker.readopia.ui.list.adapter.BookAdapter
import com.ranaturker.readopia.ui.list.model.UIState
import com.ranaturker.readopia.util.PrefUtil

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
        PrefUtil.initPref(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setAdapter()
        observeViewModel()
    }

    private fun setListeners() = with(binding) {
        val bookCount = PrefUtil.getSavedBooksCount().toString()
        val message = getString(R.string.your_total_readings, bookCount)

        imageViewBookCount.setOnClickListener {
            AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(getString(R.string.okay), null)
                .show()
        }
    }

    private fun setAdapter() {
        bookAdapter = BookAdapter(emptyList(), this@ListFragment)
        binding.recyclerView.adapter = bookAdapter
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { books ->
            if (books != null) {
                when (books) {
                    UIState.Loading -> {
                        binding.progressIndicator.isVisible = true
                    }

                    is UIState.Success -> {
                        bookAdapter.updateData(books.data)
                        binding.progressIndicator.isVisible = false
                    }
                }
            }
        }
    }

    override fun onItemClick(bookId: Int) {
        findNavController().navigate(
            ListFragmentDirections.toDetailFragment(
                bookId
            )
        )
    }
}
