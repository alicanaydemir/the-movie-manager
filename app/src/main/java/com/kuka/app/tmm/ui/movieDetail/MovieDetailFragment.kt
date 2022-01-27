package com.kuka.app.tmm.ui.movieDetail

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kuka.app.tmm.core.BaseFragment
import com.kuka.app.tmm.databinding.FragmentMovieDetailBinding
import com.kuka.app.tmm.utils.extensions.loadImage
import dagger.hilt.android.AndroidEntryPoint
import android.text.method.ScrollingMovementMethod
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kuka.app.tmm.R
import com.kuka.app.tmm.utils.extensions.showDialogProgress
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

    private val args: MovieDetailFragmentArgs by navArgs()

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun prepareView(savedInstanceState: Bundle?) {
        init()
        setListener()
        initObserver()
        viewModel.getAccountState(
            args.movie.id ?: 0
        )
    }

    private fun init() {
        binding.txtOverview.movementMethod = ScrollingMovementMethod()
        args.movie.apply {
            binding.imgMovie.loadImage(backdropPath)
            binding.txtMovieTitle.text = title
            binding.txtOverview.text = overview
            binding.txtPoint.text = voteAverage.toString()
            if (releaseDate.isNullOrEmpty().not()) {
                binding.txtDate.text = formatDate(releaseDate!!)
            }
        }
    }

    private fun setListener() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imgFavorites.setOnClickListener {
            viewModel.markAsFavorite()
        }
        binding.btnAddWishList.setOnClickListener {
            viewModel.addWatchList()
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.accountStates.collect {
                    if (it.favorite == true) binding.imgFavorites.setImageResource(R.drawable.ic_favorite_filled)
                    else binding.imgFavorites.setImageResource(R.drawable.ic_favorite_empty)

                    if (it.watchlist == true) {
                        binding.btnAddWishList.apply {
                            backgroundTintList = ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.white)
                            )
                            text = resources.getText(R.string.remove_from_my_watchlist)
                            setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.colorBlue
                                )
                            )
                            iconTint = ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.colorBlue)
                            )
                        }
                    } else {
                        binding.btnAddWishList.apply {
                            backgroundTintList =
                                ColorStateList.valueOf(
                                    ContextCompat.getColor(requireContext(), R.color.colorBlue)
                                )
                            text =
                                resources.getText(R.string.add_to_my_watchlist)
                            setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                            iconTint = ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.white)
                            )
                        }

                    }
                }
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            showDialogProgress(it)
        }
    }

    private fun formatDate(dateStr: String): String {
        val date: Date = SimpleDateFormat("yyyy-MM-dd").parse(dateStr)
        return SimpleDateFormat("dd/MM/yyyy").format(date)
    }

}