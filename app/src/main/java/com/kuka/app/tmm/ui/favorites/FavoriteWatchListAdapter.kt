package com.kuka.app.tmm.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.kuka.app.tmm.core.BaseAdapter
import com.kuka.app.tmm.core.BaseEvent
import com.kuka.app.tmm.core.BaseViewHolder
import com.kuka.app.tmm.core.EventListener
import com.kuka.app.tmm.data.model.response.Movie
import com.kuka.app.tmm.databinding.ItemFavoriteWatchlistBinding
import com.kuka.app.tmm.utils.loadImage

class FavoriteWatchListAdapter(private val listener: EventListener) : BaseAdapter<Movie>
    (object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem
}) {
    override fun createViewHolderInstance(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewBinding> {
        val binding: ItemFavoriteWatchlistBinding =
            ItemFavoriteWatchlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = BaseViewHolder(binding)

        binding.rootView.setOnClickListener {
            listener(FavoriteWatchListAdapterEvent.Click(currentList[holder.adapterPosition]))
        }
        return holder
    }

    override fun bind(binding: ViewBinding, position: Int) {
        if (binding is ItemFavoriteWatchlistBinding) {
            binding.apply {
                txtTitle.text = currentList[position].title
                imgMovie.loadImage(currentList[position].posterPath)
            }
        }
    }
}

sealed class FavoriteWatchListAdapterEvent : BaseEvent {
    class Click(val data: Movie) : FavoriteWatchListAdapterEvent()
}