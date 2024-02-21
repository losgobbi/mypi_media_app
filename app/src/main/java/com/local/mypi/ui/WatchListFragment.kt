package com.local.mypi.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.local.mypi.MyApplication
import com.local.mypi.adapters.WatchListItemAdapter
import com.local.mypi.databinding.FragmentWatchListBinding
import com.local.mypi.models.WatchListItem
import com.local.mypi.viewmodels.MediaViewModel
import kotlinx.coroutines.launch

class WatchListFragment : Fragment() {

    private lateinit var binding: FragmentWatchListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchListBinding.inflate(inflater, container, false)
        val recyclerView: RecyclerView = binding.watchListRecyclerView

        val viewModel: MediaViewModel by activityViewModels {
            MediaViewModel.Factory((activity!!.application as MyApplication).getMediaRepository())
        }

        val adapter = WatchListItemAdapter {
            lifecycleScope.launch {
                viewModel.deleteWatchListItem(it)
            }
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.watchList.observe(viewLifecycleOwner) { watchItem ->
            watchItem?.let { adapter.submitList(it) }
        }

        binding.btnAddWatchItem.setOnClickListener {
            val action =
                WatchListFragmentDirections.actionWatchListFragmentToAddWatchListFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}
