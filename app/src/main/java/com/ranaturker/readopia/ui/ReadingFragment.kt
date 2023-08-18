package com.ranaturker.readopia.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.ranaturker.readopia.databinding.FragmentReadingBinding
import com.ranaturker.readopia.network.BooksApiService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val url = args.url
        loadWebView(url)
    }

    private fun loadAndDisplayContent(url: String) {
        val textView = binding.urlTextView

        val call = BooksApiService.api.getBookContent(url)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string() ?: ""
                    textView.text = responseBody
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("error", "Error: ${t.message}")
            }
        })
    }

    private fun loadWebView(url: String) {
        with(binding.webView) {
            settings.javaScriptEnabled = true
            // Load a URL
            loadUrl(url)
            // Set a WebViewClient to handle navigation inside the WebView
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    binding.textViewLoading.text = "Page loading : $newProgress%"
                    if (newProgress == 100) {
                        binding.textViewLoading.visibility = View.GONE
                    }
                }
            }
        }
    }
}
