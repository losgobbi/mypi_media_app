package com.local.mypi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.local.mypi.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.btnAddReq.setOnClickListener {
            val action =
                HomeFragmentDirections.actionNavHomeToAddRequestFragment()
            findNavController().navigate(action)
        }

        binding.btnMediaReq.setOnClickListener {
            val action =
                HomeFragmentDirections.actionNavHomeToMediaRequestFragment()
            findNavController().navigate(action)
        }

        binding.btnWatchList.setOnClickListener {
            val action =
                HomeFragmentDirections.actionNavHomeToWatchListFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }
}