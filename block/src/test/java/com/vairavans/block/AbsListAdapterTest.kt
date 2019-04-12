package com.vairavans.block

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AbsListAdapterTest {

    class SampleAdapter( diffCallback: DiffCallback<String, SampleAdapter.ViewHolder> )
        : AbsListAdapter<String, SampleAdapter.ViewHolder>( diffCallback = diffCallback ) {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            TODO("logic not implemented for tests")
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            TODO("logic not implemented for tests")
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            TODO("logic not implemented for tests")
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            TODO("logic not needed for tests")
        }

        class ViewHolder : RecyclerView.ViewHolder( mockk() )
    }

    @Test
    fun `AbsListAdapter should invoke setAdapter on DiffCallback`() {

        val diffCallback = mockk<DiffCallback<String, SampleAdapter.ViewHolder>>( relaxed = true )

        val adapter = SampleAdapter( diffCallback )

        verify { diffCallback.setAdapter(adapter) }
    }

    @Test
    fun `DiffCallback should relay areContentsTheSame to AbsListAdapter`() {

        val adapter = mockk<AbsListAdapter<String, SampleAdapter.ViewHolder>>( relaxed = true )
        val oldItem = "random1"
        val newItem = "random2"

        val diffCallback = DiffCallback<String, SampleAdapter.ViewHolder>()
        diffCallback.setAdapter( adapter )

        diffCallback.areContentsTheSame( oldItem, newItem )

        verify { adapter.areContentsTheSame( oldItem, newItem ) }
    }

    @Test
    fun `DiffCallback should relay areItemsTheSame to AbsListAdapter`() {

        val adapter = mockk<AbsListAdapter<String, SampleAdapter.ViewHolder>>( relaxed = true )
        val oldItem = "random1"
        val newItem = "random2"

        val diffCallback = DiffCallback<String, SampleAdapter.ViewHolder>()
        diffCallback.setAdapter( adapter )

        diffCallback.areItemsTheSame( oldItem, newItem )

        verify { adapter.areItemsTheSame( oldItem, newItem ) }
    }

    @Test
    fun `DiffCallback should relay getChangePayload to AbsListAdapter`() {

        val adapter = mockk<AbsListAdapter<String, SampleAdapter.ViewHolder>>( relaxed = true )
        val oldItem = "random1"
        val newItem = "random2"

        val diffCallback = DiffCallback<String, SampleAdapter.ViewHolder>()
        diffCallback.setAdapter( adapter )

        diffCallback.getChangePayload( oldItem, newItem )

        verify { adapter.getChangePayload( oldItem, newItem ) }
    }
}
