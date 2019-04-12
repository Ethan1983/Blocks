package com.vairavans.block

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * A [ListAdapter] to avoid boiler plate associated with creating a [DiffUtil.ItemCallback] class.
 *
 * If needed, concrete implementations can extend [DiffCallback] and provide an instance in [AbsListAdapter]
 * constructor.
 */
abstract class AbsListAdapter<T,VH : RecyclerView.ViewHolder>
    ( diffCallback : DiffCallback<T,VH> = DiffCallback() ): ListAdapter<T, VH>( diffCallback ) {

    init {
        @Suppress("LeakingThis")
        diffCallback.setAdapter(adapter = this)
    }

    abstract fun areItemsTheSame(oldItem: T, newItem: T) : Boolean

    abstract fun areContentsTheSame(oldItem: T, newItem: T) : Boolean

    open fun getChangePayload( oldItem: T, newItem: T ) : Any? {
        throw ChangePayLoadNotImplementedException()
    }
}

private class ChangePayLoadNotImplementedException : Exception()

open class DiffCallback<T,VH: RecyclerView.ViewHolder> : DiffUtil.ItemCallback<T>() {

    private lateinit var adapterRef : AbsListAdapter<T,VH>

    fun setAdapter( adapter: AbsListAdapter<T,VH> ) {
        adapterRef = adapter
    }

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = adapterRef.areItemsTheSame( oldItem, newItem )

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = adapterRef.areContentsTheSame( oldItem, newItem )

    override fun getChangePayload(oldItem: T, newItem: T): Any? {

        return try {
            adapterRef.getChangePayload( oldItem, newItem )
        } catch ( e : ChangePayLoadNotImplementedException) {
            super.getChangePayload( oldItem, newItem )
        }
    }
}
