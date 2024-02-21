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
import com.local.mypi.MyApplication
import com.local.mypi.R
import com.local.mypi.databinding.FragmentAddRequestBinding
import com.local.mypi.databinding.FragmentAddWatchlistBinding
import com.local.mypi.models.DownloadType
import com.local.mypi.models.MediaFile
import com.local.mypi.models.WatchListItem
import com.local.mypi.viewmodels.MediaViewModel
import kotlinx.coroutines.launch

class AddWatchListFragment : Fragment() {

    private lateinit var binding: FragmentAddWatchlistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddWatchlistBinding.inflate(inflater, container, false)

        val viewModel: MediaViewModel by activityViewModels {
            MediaViewModel.Factory((activity!!.application as MyApplication).getMediaRepository())
        }

        binding.btnAddWatch.setOnClickListener {
            lifecycleScope.launch {
                viewModel.addWatchListItem(WatchListItem(0, binding.editWatchName.text.toString()))
            }
            findNavController().navigate(AddWatchListFragmentDirections.actionAddWatchListFragmentToWatchListFragment())
        }

        return binding.root
    }
}