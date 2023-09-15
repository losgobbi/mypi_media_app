package com.local.mypi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.local.mypi.MyApplication
import com.local.mypi.R
import com.local.mypi.databinding.FragmentWatchListBinding
import com.local.mypi.viewmodels.MediaViewModel

class WatchListFragment : Fragment() {

    private lateinit var binding: FragmentWatchListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchListBinding.inflate(inflater, container, false)
        var tmpAdapter = ArrayAdapter.createFromResource(
            context!!,
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        )
        tmpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.mediaType.adapter = tmpAdapter

        tmpAdapter = ArrayAdapter.createFromResource(
            context!!,
            R.array.media_size_array,
            android.R.layout.simple_spinner_item
        )
        tmpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sizeUnit.adapter = tmpAdapter

        val viewModel: MediaViewModel by activityViewModels {
            MediaViewModel.Factory((activity!!.application as MyApplication).getMediaRepository())
        }
        binding.btnAddMedia.setOnClickListener {
            viewModel.addMedia()
        }

        return binding.root
    }
}