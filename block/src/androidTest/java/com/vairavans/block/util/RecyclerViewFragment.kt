package com.vairavans.block.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.vairavans.block.widget.RecyclerEmptyView

class RecyclerViewFragment : Fragment() {

    val listAdapter = RecyclerAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val context = inflater.context

        return LinearLayout( context ).apply {
            layoutParams = ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT )
            orientation = VERTICAL
            tag = "LinearLayout"

            val imageView = ImageView( context ).also {
                addView( it )
                it.tag = "ImageView"
                it.minimumHeight = 100
                it.minimumWidth = 100
            }

            addView( FrameLayout( context ).apply {

                val emptyTextView = TextView( context ).also {
                    addView( it )
                    it.tag = "TextView"
                    it.minimumHeight = 100
                    it.minimumWidth = 100
                }

                addView( RecyclerEmptyView( context ).apply {
                    adapter = listAdapter
                    emptyView = emptyTextView
                    hiddenViewsOnEmptyDataSet.add( imageView )
                    tag = "RecyclerView"
                    minimumHeight = 100
                    minimumWidth = 100
                })

            })
        }
    }
}
