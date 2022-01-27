package com.kuka.app.tmm.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.kuka.app.tmm.core.BaseAdapter
import com.kuka.app.tmm.core.BaseEvent
import com.kuka.app.tmm.core.BaseViewHolder
import com.kuka.app.tmm.core.EventListener
import com.kuka.app.tmm.data.model.response.Movie
import com.kuka.app.tmm.databinding.ItemSearchBinding
import com.kuka.app.tmm.utils.hide
import com.kuka.app.tmm.utils.invisible
import com.kuka.app.tmm.utils.show

class SearchAdapter(private val listener: EventListener) : BaseAdapter<Movie>
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
        val binding: ItemSearchBinding =
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = BaseViewHolder(binding)

        binding.rootView.setOnClickListener {
            listener(SearchAdapterEvent.Click(currentList[holder.adapterPosition]))
        }
        return holder
    }

    override fun bind(binding: ViewBinding, position: Int) {
        if (binding is ItemSearchBinding) {
            binding.apply {
                txtTitle.text = currentList[position].title
                val date = currentList[position].releaseDate?.split("-")?.get(0) ?: ""
                txtDate.text = date
                if (date.isEmpty()) txtDate.invisible()
                else txtDate.show()
            }
        }
    }
}

sealed class SearchAdapterEvent : BaseEvent {
    class Click(val data: Movie) : SearchAdapterEvent()
}