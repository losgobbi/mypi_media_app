package com.local.mypi.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.local.mypi.MyApplication
import com.local.mypi.R
import com.local.mypi.databinding.FragmentAddRequestBinding
import com.local.mypi.models.DownloadType
import com.local.mypi.models.MediaFile
import com.local.mypi.viewmodels.MediaViewModel
import kotlinx.coroutines.launch

class AddRequestFragment : Fragment() {

    private lateinit var binding: FragmentAddRequestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRequestBinding.inflate(inflater, container, false)
        var tmpAdapter = ArrayAdapter.createFromResource(
            context!!,
            R.array.media_types_array,
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
            lifecycleScope.launch {
                var size = binding.editEstimatedSize.text.toString() + " " +
                            binding.sizeUnit.selectedItem.toString()
                val request = MediaFile(binding.editMediaName.text.toString()).apply {
                    this.download_type = DownloadType.fromValue(binding.mediaType.selectedItem.toString())
                    this.download_link = binding.editLink.text.toString()
                    this.expected_size = size
                }
                Log.d("AddRequestFragment", "new request " + request)
                viewModel.addMedia(request)
                findNavController().navigate(AddRequestFragmentDirections.actionAddRequestFragmentToNavHome())
            }
        }

        return binding.root
    }
}