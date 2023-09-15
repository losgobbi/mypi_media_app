package com.local.mypi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.local.mypi.databinding.MediaRequestItemBinding
import com.local.mypi.models.MediaFile
import com.local.mypi.ui.MediaRequestFragmentDirections

class MediaRequestAdapter(private val medias: List<MediaFile>) :
    RecyclerView.Adapter<MediaRequestAdapter.ViewHolder>() {

    class ViewHolder(binding: MediaRequestItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private var btnDetails : Button = binding.btnMediaItemStartDown
        private lateinit var mediaItem: MediaFile

        init {
            btnDetails.setOnClickListener {
                val action =
                    MediaRequestFragmentDirections.actionMediaRequestFragmentToMediaRequestFragmentDetails()
                Navigation.findNavController(binding.root)!!.navigate(action)
            }
        }

        fun bind(mediaItem: MediaFile) {
            this.mediaItem = mediaItem

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MediaRequestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(medias[position])
    }

    override fun getItemCount(): Int {
        return medias.size
    }
}