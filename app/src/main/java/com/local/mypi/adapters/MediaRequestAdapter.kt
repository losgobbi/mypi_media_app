package com.local.mypi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.local.mypi.databinding.MediaRequestItemBinding
import com.local.mypi.models.DownloadType
import com.local.mypi.models.MediaFile

class MediaRequestAdapter(private val medias: List<MediaFile>) :
    RecyclerView.Adapter<MediaRequestAdapter.ViewHolder>() {

    class ViewHolder(binding: MediaRequestItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private var btnDetails : Button = binding.btnMediaItemStartDown
        private lateinit var mediaItem: MediaFile
        private lateinit var binding: MediaRequestItemBinding

        init {
            /*btnDetails.setOnClickListener {
                val action =
                    MediaRequestFragmentDirections.actionMediaRequestFragmentToMediaRequestFragmentDetails()
                Navigation.findNavController(binding.root)!!.navigate(action)
            }*/
            this.binding = binding
        }

        fun bind(mediaItem: MediaFile) {
            this.mediaItem = mediaItem
            this.binding.txtViewMediaItemName.text = mediaItem.name
            this.binding.txtViewMediaItemType.text = mediaItem.download_type.toString()
            var progress = if (mediaItem.progress > 0) mediaItem.progress else 0
            this.binding.txtViewMediaItemStatus.text = "Status: (" + progress + "%)"
            this.binding.txtViewMediaItemError.text =
                if (mediaItem.download_type == DownloadType.MANUAL) "<not available>" else mediaItem.statusMsg
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