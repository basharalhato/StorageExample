package dev.balhato.storageexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.balhato.storageexample.databinding.ItemPhotoBinding

class SharedPhotoAdapter(
    private val onPhotoClick: (SharedStoragePhoto) -> Unit
) : ListAdapter<SharedStoragePhoto, SharedPhotoAdapter.PhotoViewHolder>(Companion) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: SharedStoragePhoto) {
            binding.apply {
                ivPhoto.setImageURI(photo.contentUri)

                val aspectRatio = photo.width.toFloat() / photo.height.toFloat()
                ConstraintSet().apply {
                    clone(root)
                    setDimensionRatio(ivPhoto.id, aspectRatio.toString())
                    applyTo(root)
                }

                ivPhoto.setOnLongClickListener {
                    onPhotoClick(photo)
                    true
                }
            }
        }
    }

    companion object : DiffUtil.ItemCallback<SharedStoragePhoto>() {
        override fun areItemsTheSame(
            oldItem: SharedStoragePhoto,
            newItem: SharedStoragePhoto
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SharedStoragePhoto,
            newItem: SharedStoragePhoto
        ): Boolean {
            return oldItem == newItem
        }
    }
}