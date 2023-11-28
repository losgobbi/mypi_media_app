package com.local.mypi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.local.mypi.MyApplication
import com.local.mypi.adapters.MediaRequestAdapter
import com.local.mypi.databinding.FragmentMediaRequestsBinding
import com.local.mypi.databinding.FragmentWatchListBinding
import com.local.mypi.viewmodels.MediaViewModel

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

        /*viewModel.medias.observe(viewLifecycleOwner) {
            recyclerView.adapter = MediaRequestAdapter(viewModel.medias.value!!)
        }*/

        binding.btnAddWatchItem.setOnClickListener {
            //viewModel.changeMediaList()
        }

        return binding.root
    }
}