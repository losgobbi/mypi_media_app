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
import com.local.mypi.databinding.FragmentMediaRequestBinding
import com.local.mypi.viewmodels.MediaViewModel

class MediaRequestFragment : Fragment() {

    private lateinit var binding: FragmentMediaRequestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMediaRequestBinding.inflate(inflater, container, false)
        val recyclerView: RecyclerView = binding.mediaRequestRecyclerView

        val viewModel: MediaViewModel by activityViewModels {
            MediaViewModel.Factory((activity!!.application as MyApplication).getMediaRepository())
        }

        recyclerView.adapter = MediaRequestAdapter(viewModel.medias.value!!)
        return binding.root
   }
}