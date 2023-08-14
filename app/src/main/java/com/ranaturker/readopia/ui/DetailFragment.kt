package com.ranaturker.readopia.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ranaturker.readopia.R
import com.ranaturker.readopia.network.Result

class DetailFragment : Fragment() {
    private var book: Result ?= null
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val bookId = DetailFragmentArgs.fromBundle(requireArguments()).bookId
            if(bookId!=-1){
                viewModel.getBookWithId(bookId)
            }
        }
        viewModel.book.observe(viewLifecycleOwner) {
            if (it != null) {
                book = it
                Log.d("detail book", book.toString())
            }
        }

    }

}