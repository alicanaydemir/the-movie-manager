package com.kuka.app.tmm.ui.watchList

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.kuka.app.tmm.core.BaseFragment
import com.kuka.app.tmm.databinding.FragmentWatchListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchListFragment : BaseFragment<FragmentWatchListBinding>(FragmentWatchListBinding::inflate) {

    private val viewModel: WatchListViewModel by viewModels()

    override fun prepareView(savedInstanceState: Bundle?) {
        init()
        //setListener()
        //initObserver()
    }

    private fun init() {

    }
}