// This file defines the adapter for the incidents RecyclerView.
package com.example.agricultureagritech.features.incidentReporting.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

import com.example.agricultureagritech.features.incidentReporting.data.model.Incident
import com.safenation.agriculture.R
import com.safenation.agriculture.databinding.ListItemIncidentBinding

// This adapter manages the list of incidents and binds data to the views.
class IncidentAdapter(private var incidents: List<Incident>) : RecyclerView.Adapter<IncidentAdapter.IncidentViewHolder>() {

    // This creates new views for the list items.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncidentViewHolder {
        val binding = ListItemIncidentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IncidentViewHolder(binding)
    }

    // This replaces the contents of a view with new data.
    override fun onBindViewHolder(holder: IncidentViewHolder, position: Int) {
        val incident = incidents[position]
        holder.bind(incident)
    }

    // This returns the size of the dataset.
    override fun getItemCount() = incidents.size

    // This updates the list of incidents and notifies the adapter of the data change.
    fun updateIncidents(newIncidents: List<Incident>) {
        incidents = newIncidents
        notifyDataSetChanged()
    }

    // This class holds the views for a single incident item.
    class IncidentViewHolder(private val binding: ListItemIncidentBinding) : RecyclerView.ViewHolder(binding.root) {
        // This binds the incident data to the views.
        fun bind(incident: Incident) {
            binding.incidentPriorityId.text = "${incident.priority} #${incident.id}"
            binding.incidentTitle.text = incident.title
            binding.incidentTimestampOwner.text = "${incident.timestamp}, ${incident.owner}"
            binding.incidentStatus.text = incident.status

            // This sets the background color of the status label based on the status.
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
}