// This file defines the adapter for the incidents RecyclerView.
package com.example.agricultureagritech.features.incidentReporting.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.agricultureagritech.features.incidentReporting.data.model.Incident
import com.safenation.agriculture.R
import com.safenation.agriculture.databinding.ListItemIncidentBinding

/**
 * Adapter for the incident list using ListAdapter for efficient list updates.
 *
 * @param onItemClicked A lambda function to handle clicks on list items.
 */
class IncidentAdapter(
    private val onItemClicked: (Incident) -> Unit
) : ListAdapter<Incident, IncidentAdapter.IncidentViewHolder>(IncidentDiffCallback) {

    /**
     * Creates new views for the list items.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncidentViewHolder {
        val binding = ListItemIncidentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IncidentViewHolder(binding, onItemClicked)
    }

    /**
     * Replaces the contents of a view with new data.
     */
    override fun onBindViewHolder(holder: IncidentViewHolder, position: Int) {
        val incident = getItem(position)
        holder.bind(incident)
    }

    /**
     * Holds the views for a single incident item and handles click binding.
     */
    class IncidentViewHolder(
        private val binding: ListItemIncidentBinding,
        private val onItemClicked: (Incident) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds the incident data to the views and sets the click listener.
         */
        fun bind(incident: Incident) {
            // Set click listener for the entire item view
            binding.root.setOnClickListener {
                onItemClicked(incident)
            }

            // Bind data
            binding.incidentPriorityId.text = "${incident.priority} #${incident.id}"
            binding.incidentTitle.text = incident.title
            binding.incidentTimestampOwner.text = "${incident.timestamp}, ${incident.owner}"
            binding.incidentStatus.text = incident.status

            // Set the background color of the status label
            val statusColor = when (incident.status) {
                "Issued" -> Color.parseColor("#FFC107") // Amber
                "Pending" -> Color.parseColor("#DC3545") // Red
                "Resolved" -> Color.parseColor("#28A745") // Green
                else -> Color.LTGRAY
            }

            val background = ContextCompat.getDrawable(binding.root.context, R.drawable.status_background)?.mutate()
            if (background is GradientDrawable) {
                background.setColor(statusColor)
                binding.incidentStatus.background = background
            }
        }
    }

    /**
     * DiffUtil.ItemCallback for calculating the difference between two lists
     * to enable efficient updates with ListAdapter.
     */
    companion object {
        private val IncidentDiffCallback = object : DiffUtil.ItemCallback<Incident>() {
            override fun areItemsTheSame(oldItem: Incident, newItem: Incident): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Incident, newItem: Incident): Boolean {
                return oldItem == newItem
            }
        }
    }
}