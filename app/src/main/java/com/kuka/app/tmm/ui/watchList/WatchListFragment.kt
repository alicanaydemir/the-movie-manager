package com.kuka.app.tmm.ui.watchList

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuka.app.tmm.NavGraphDirections
import com.kuka.app.tmm.core.BaseFragment
import com.kuka.app.tmm.databinding.FragmentWatchListBinding
import com.kuka.app.tmm.ui.favorites.FavoriteWatchListAdapter
import com.kuka.app.tmm.ui.favorites.FavoriteWatchListAdapterEvent
import com.kuka.app.tmm.utils.EndlessScrollListener
import com.kuka.app.tmm.utils.extensions.hide
import com.kuka.app.tmm.utils.extensions.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchListFragment :
    BaseFragment<FragmentWatchListBinding>(FragmentWatchListBinding::inflate) {

    private val viewModel: WatchListViewModel by viewModels()
    private lateinit var pagingListener: EndlessScrollListener

    override fun prepareView(savedInstanceState: Bundle?) {
        init()
        initAdapter()
        initObserver()
    }

    private fun init() {
        viewModel.getList(1)
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
            adapter = FavoriteWatchListAdapter() {
                if (it is FavoriteWatchListAdapterEvent.Click) {
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

    private fun initObserver() {
        viewModel.totalItems.asLiveData().observe(viewLifecycleOwner){
            pagingListener.totalItemCount=it
        }
        viewModel.movieList.asLiveData().observe(viewLifecycleOwner) {
            if (it?.size == 0) {
                (binding.recyclerView.adapter as FavoriteWatchListAdapter).submitList(null)
            } else {
                (binding.recyclerView.adapter as FavoriteWatchListAdapter).submitList(it)
            }
        }
        viewModel.empty.observe(viewLifecycleOwner) {
            if (it) binding.txtEmptyInfoWatchList.show()
            else binding.txtEmptyInfoWatchList.hide()
        }
    }

}