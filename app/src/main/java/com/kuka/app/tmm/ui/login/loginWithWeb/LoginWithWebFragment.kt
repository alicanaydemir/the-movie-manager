package com.kuka.app.tmm.ui.login.loginWithWeb

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.webkit.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kuka.app.tmm.BuildConfig
import com.kuka.app.tmm.core.BaseFragment
import com.kuka.app.tmm.core.Constants
import com.kuka.app.tmm.databinding.FragmentLoginWithWebBinding
import com.kuka.app.tmm.ui.main.MainViewModel
import com.kuka.app.tmm.utils.extensions.showDialogProgress
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginWithWebFragment :
    BaseFragment<FragmentLoginWithWebBinding>(FragmentLoginWithWebBinding::inflate) {

    private val viewModel: LoginWithWebViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()


    override fun prepareView(savedInstanceState: Bundle?) {
        init()
        setListener()
    }

    private fun init() {
        initWebView()
    }

    private fun setListener() {
        viewModel.loading.observe(viewLifecycleOwner) {
            showDialogProgress(it)
        }
    }

    private fun initWebView() {
        binding.loginWebView.apply {
            settings.apply {
                javaScriptEnabled = true
                javaScriptCanOpenWindowsAutomatically = true
                loadsImagesAutomatically = true
            }
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)

                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    val uri = request?.url
                    handleUriLoading(uri)
                    return super.shouldOverrideUrlLoading(view, request)

                }

            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    if (newProgress == 100) {
                        viewModel.loading.postValue(false)
                    }
                }

                override fun onRequestFocus(view: WebView?) {
                    super.onRequestFocus(view)
                }


            }


        }
        if (BuildConfig.DEBUG)
            WebView.setWebContentsDebuggingEnabled(true)

        binding.loginWebView.loadUrl(
            "https://www.themoviedb.org/authenticate/${
                viewModel.sharedHelper.getStringData(Constants.Pref.REQUEST_TOKEN, "")
            }"
        )


    }

    private fun handleUriLoading(uri: Uri?) {

        if (uri?.path?.startsWith("/authenticate") == true && uri.lastPathSegment == "allow") {
            viewModel.sharedHelper.putStringData(
                Constants.Pref.REQUEST_TOKEN,
                uri.pathSegments?.get(1)
            )
            loginSuccess()


        }

    }

    private fun loginSuccess() {
        mainViewModel.account()
        val action = LoginWithWebFragmentDirections.actionLoginWithWebFragmentToNavGraphSearch()
        findNavController().navigate(action)
    }
}

