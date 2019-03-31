package com.vairavans.block.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.vairavans.block.multiLet

/**
 * A [RecyclerView] supporting configurable empty view
 */
class RecyclerEmptyView : RecyclerView {

    constructor( context: Context ) : super(context)

    constructor( context: Context, attrs: AttributeSet? ) : super(context, attrs)

    constructor( context: Context, attrs: AttributeSet?, defStyle: Int ) : super(context, attrs, defStyle)

    var emptyView : View? = null
    val hiddenViewsOnEmptyDataSet : MutableList<View> = mutableListOf()

    // onChanged() is not called when ListAdapter.submitList is called on the adapter
    private val emptyObserver = object : RecyclerView.AdapterDataObserver() {

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) = handleDataChange()

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) = handleDataChange()

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) = handleDataChange()

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) = handleDataChange()

        override fun onChanged() = handleDataChange()

        private fun handleDataChange() {

            multiLet( adapter, emptyView ) { adapter, view ->

                if( adapter.itemCount == 0 ) {
                    view.visibility = View.VISIBLE
                    this@RecyclerEmptyView.visibility = View.GONE
                    hiddenViewsOnEmptyDataSet.onEach { it.visibility = View.GONE }
                } else {
                    view.visibility = View.GONE
                    this@RecyclerEmptyView.visibility = View.VISIBLE
                    hiddenViewsOnEmptyDataSet.onEach { it.visibility = View.VISIBLE }
                }

            }

        }

    }

    override fun setAdapter( adapter: Adapter<*>? ) {
        super.setAdapter( adapter )

        adapter?.registerAdapterDataObserver( emptyObserver )

        emptyObserver.onChanged()
    }
}
