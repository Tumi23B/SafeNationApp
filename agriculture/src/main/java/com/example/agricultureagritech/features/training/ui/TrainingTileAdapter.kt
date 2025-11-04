// RecyclerView adapter for the training grid tiles
package com.example.agricultureagritech.features.training.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.agricultureagritech.features.training.domain.TrainingTile
import com.safenation.agriculture.databinding.ItemTrainingTileBinding

class TrainingTileAdapter(
    private val onItemClick: (TrainingTile) -> Unit
) : ListAdapter<TrainingTile, TrainingTileAdapter.TileViewHolder>(TrainingTileDiffCallback()) {

    // Inflates the layout for each tile
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder {
        val binding = ItemTrainingTileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TileViewHolder(binding)
    }

    // Binds data to the views in each tile
    override fun onBindViewHolder(holder: TileViewHolder, position: Int) {
        val tile = getItem(position)
        holder.bind(tile)
    }

    // ViewHolder class that holds the views for each tile
    inner class TileViewHolder(private val binding: ItemTrainingTileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Set the click listener which triggers the navigation
            binding.root.setOnClickListener {
                // Get the item at the clicked adapter position
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
        }

        // Binds the TrainingTile data to the layout
        fun bind(tile: TrainingTile) {
            // Get context to resolve the string resource
            val context = binding.root.context
            binding.trainingTitle.text = context.getString(tile.titleResId)
            binding.trainingIcon.setImageResource(tile.iconResId)
        }
    }
}

/**
 * DiffUtil callback for calculating the difference between two non-null items in a list.
 * This allows the ListAdapter to determine minimal updates.
 */
class TrainingTileDiffCallback : DiffUtil.ItemCallback<TrainingTile>() {
    override fun areItemsTheSame(oldItem: TrainingTile, newItem: TrainingTile): Boolean {
        // Check if the items represent the same object
        return oldItem.titleResId == newItem.titleResId
    }

    override fun areContentsTheSame(oldItem: TrainingTile, newItem: TrainingTile): Boolean {
        // Check if the item contents have changed
        return oldItem == newItem
    }
}