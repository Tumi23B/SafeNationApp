// RecyclerView adapter for training tiles using ViewBinding with safe item position handling
package com.example.agricultureagritech.features.training.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.agricultureagritech.features.training.domain.TrainingTile
import com.safenation.agriculture.R
import com.safenation.agriculture.databinding.ItemTrainingTileBinding

class TrainingTileAdapter(
    private val onClick: (TrainingTile) -> Unit
) : ListAdapter<TrainingTile, TrainingTileAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<TrainingTile>() {
        override fun areItemsTheSame(oldItem: TrainingTile, newItem: TrainingTile) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: TrainingTile, newItem: TrainingTile) =
            oldItem == newItem
    }

    class VH(
        private val binding: ItemTrainingTileBinding,
        private val onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TrainingTile) {
            binding.trainingIcon.setImageResource(item.iconRes)
            binding.trainingTitle.text = item.title
        }

        init {
            binding.root.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) onItemClick(pos)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemTrainingTileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val minHeight = parent.resources.getDimensionPixelSize(R.dimen.training_tile_min_height)
        binding.root.minimumHeight = minHeight
        binding.root.layoutParams?.let { lp ->
            if (lp.height == 0) {
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
                binding.root.layoutParams = lp
            }
        }

        return VH(binding) { pos -> onClick(getItem(pos)) }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}