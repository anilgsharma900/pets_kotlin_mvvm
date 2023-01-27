package com.itsanilg.petsapp.ui.pet_details

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.itsanilg.petsapp.databinding.FragmentPetDetailsBinding

class PetDetailsFragment : Fragment() {
    private var _binding: FragmentPetDetailsBinding? = null;
    private val binding get() = _binding!!
    private var url: String? = null;

    companion object {
        fun newInstance() = PetDetailsFragment()
    }

    private lateinit var viewModel: PetDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        url = requireArguments().getString("url")

        _binding = FragmentPetDetailsBinding.inflate(inflater, container, false)

        binding.wvPetDetails.webViewClient = WebViewClient()
        binding.wvPetDetails.webChromeClient = WebChromeClient()

        binding.wvPetDetails.loadUrl(url!!)

        binding.wvPetDetails.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                binding.progressBar.visibility = View.VISIBLE
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding.progressBar.visibility = View.GONE
                super.onPageFinished(view, url)
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[PetDetailsViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}