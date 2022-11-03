package com.kuka.app.tmm.ui.search

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuka.app.tmm.NavGraphDirections
import com.kuka.app.tmm.core.BaseFragment
import com.kuka.app.tmm.databinding.FragmentSearchBinding
import com.kuka.app.tmm.ui.main.MainViewModel
import com.kuka.app.tmm.utils.EndlessScrollListener
import com.kuka.app.tmm.utils.extensions.hide
import com.kuka.app.tmm.utils.extensions.onQueryTextChanged
import com.kuka.app.tmm.utils.extensions.show
import com.kuka.app.tmm.utils.extensions.showDialogProgress
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var pagingListener: EndlessScrollListener

    override fun prepareView(savedInstanceState: Bundle?) {
        init()
        initAdapter()
        setListener()
        initObserver()
    }

    private fun init() {

    }

    private fun initAdapter() {
        pagingListener = object :
            EndlessScrollListener(binding.recyclerView.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int) {
                viewModel.getList(page)
            }
        }
        pagingListener.setCurrentPage(viewModel.currentPage)
        binding.recyclerView.apply {
            adapter = SearchAdapter {
                if (it is SearchAdapterEvent.Click) {
                    findNavController().navigate(
                        NavGraphDirections.actionGlobalMovieDetailFragment(
                            it.data
                        )
                    )
                }
            }
            addOnScrollListener(pagingListener)
        }
    }

    private fun setListener() {
        binding.tiEdtSearch.onQueryTextChanged {
            viewModel.getResult(it)
        }
        binding.btnLogOut.setOnClickListener {
            mainViewModel.logout()
        }
    }

    private fun initObserver() {
        viewModel.totalItems.asLiveData().observe(viewLifecycleOwner){
            pagingListener.totalItemCount=it
        }
        viewModel.movieList.asLiveData().observe(viewLifecycleOwner) {
            if (it?.size == 0) {
                binding.txtEmptyInfo.show()
                (binding.recyclerView.adapter as SearchAdapter).submitList(null)
            } else {
                binding.txtEmptyInfo.hide()
                (binding.recyclerView.adapter as SearchAdapter).submitList(it)
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            showDialogProgress(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}