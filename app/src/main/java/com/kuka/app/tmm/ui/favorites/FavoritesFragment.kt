package com.kuka.app.tmm.ui.favorites

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.kuka.app.tmm.NavGraphDirections
import com.kuka.app.tmm.core.BaseFragment
import com.kuka.app.tmm.databinding.FragmentFavoritesBinding
import com.kuka.app.tmm.utils.addOnPagingListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel: FavoritesViewModel by viewModels()

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
            (binding.recyclerView.adapter as FavoriteWatchListAdapter).submitList(it)
        }
    }
}