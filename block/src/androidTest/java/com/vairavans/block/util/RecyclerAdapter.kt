package com.vairavans.block.util

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : ListAdapter<String,RecyclerAdapter.ViewHolder>( DiffCallback() ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder( TextView( parent.context ) )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind( getItem( position ))
    }


    class ViewHolder( private val textView : TextView) : RecyclerView.ViewHolder( textView ) {
        fun bind( value : String ) {
            textView.text = value
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem

    }
}
