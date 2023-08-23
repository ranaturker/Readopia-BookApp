package com.ranaturker.readopia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ranaturker.readopia.R
import com.ranaturker.readopia.databinding.FragmentReadingBinding
import com.ranaturker.readopia.util.PrefUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReadingFragment : Fragment() {
    private lateinit var binding: FragmentReadingBinding
    private val args: ReadingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReadingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val navController = findNavController()
        toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }
        val url = args.url
        loadWebView(url)
    }

    private fun loadWebView(url: String) {
        with(binding.webView) {
            loadUrl(url)

            setOnScrollChangeListener { _, _, scrollY, _, _ ->
                PrefUtil.saveBookScrollPosition(args.bookId, scrollY)
            }

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    view?.loadUrl(url)
                    return true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    val scrollPosition = PrefUtil.getBookScrollPosition(args.bookId)
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(500)
                        view?.scrollTo(0, scrollPosition)
                    }
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    binding.progress.progress = newProgress
                    if (newProgress == 100) {
                        binding.progress.visibility = View.GONE
                    }
                }
            }
        }
    }
}
