package com.kuka.app.tmm.core

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T>(callback: DiffUtil.ItemCallback<T>) : ListAdapter<T, BaseViewHolder<ViewBinding>>(callback) {

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding>, position: Int) {
        bind(holder.binding, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding> {
        return createViewHolderInstance(parent, viewType)
    }

    abstract fun createViewHolderInstance(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding>

    protected abstract fun bind(binding: ViewBinding, position: Int)
}
