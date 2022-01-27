package com.kuka.app.tmm.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kuka.app.tmm.core.BaseFragment
import com.kuka.app.tmm.core.Constants
import com.kuka.app.tmm.databinding.FragmentLoginBinding
import com.kuka.app.tmm.ui.main.MainViewModel
import com.kuka.app.tmm.utils.extensions.showDialogProgress
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun prepareView(savedInstanceState: Bundle?) {
        checkLoggedIn()
        setListener()
        initObserver()
    }

    private fun checkLoggedIn() {
        if (viewModel.sharedHelper.getStringData(Constants.Pref.SESSION_ID, "").isNullOrEmpty()
                .not()
        ) {
            val action = LoginFragmentDirections.actionLoginFragmentToNavGraphSearch()
            findNavController().navigate(action)
        }
    }

    private fun setListener() {
        binding.btnLogin.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.login(
                    binding.tiEdtEmail.text.toString(),
                    binding.tiEdtPassword.text.toString()
                )
            }
        }
        binding.btnLoginWebsite.setOnClickListener {
            viewModel.loginWithWebSite()
        }
    }

    private fun initObserver() {
        viewModel.loading.observe(viewLifecycleOwner) {
            showDialogProgress(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.success.observe(viewLifecycleOwner) {
            mainViewModel.account()
            val action = LoginFragmentDirections.actionLoginFragmentToNavGraphSearch()
            findNavController().navigate(action)
        }
        viewModel.openWebsite.observe(viewLifecycleOwner) {
            val action = LoginFragmentDirections.actionLoginFragmentToLoginWithWebFragment()
            findNavController().navigate(action)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.loading.postValue(false)
    }

}