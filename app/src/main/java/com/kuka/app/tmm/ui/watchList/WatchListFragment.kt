package com.kuka.app.tmm.ui.watchList

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.kuka.app.tmm.NavGraphDirections
import com.kuka.app.tmm.core.BaseFragment
import com.kuka.app.tmm.databinding.FragmentWatchListBinding
import com.kuka.app.tmm.ui.favorites.FavoriteWatchListAdapter
import com.kuka.app.tmm.ui.favorites.FavoriteWatchListAdapterEvent
import com.kuka.app.tmm.utils.addOnPagingListener
import com.kuka.app.tmm.utils.extensions.hide
import com.kuka.app.tmm.utils.extensions.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchListFragment :
    BaseFragment<FragmentWatchListBinding>(FragmentWatchListBinding::inflate) {

    private val viewModel: WatchListViewModel by viewModels()

    override fun prepareView(savedInstanceState: Bundle?) {
        init()
        initAdapter()
        initObserver()
    }

    private fun init() {
        viewModel.getList(1)
    }

    private fun initAdapter() {
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

            addOnPagingListener { page ->
                viewModel.getList(page)
            }
        }
    }

    private fun initObserver() {
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