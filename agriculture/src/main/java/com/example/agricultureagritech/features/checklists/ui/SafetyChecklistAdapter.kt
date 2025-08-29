// This file contains the adapter for the safety checklist RecyclerView.
package com.agricultureAgritech.features.safetyChecklists.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agricultureAgritech.features.safetyChecklists.domain.model.ChecklistItem
import com.safenation.agriculture.R

class SafetyChecklistAdapter(private val items: List<ChecklistItem>) :
    RecyclerView.Adapter<SafetyChecklistAdapter.ViewHolder>() {

    /*
     * This class holds the views for a single checklist item.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.item_title)
        val subtitle: TextView = view.findViewById(R.id.item_subtitle)
        val layout: LinearLayout = view.findViewById(R.id.item_layout)
        val checkbox: CheckBox = view.findViewById(R.id.item_checkbox)
    }

    /*
     * This function creates new views for the list items.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_safety_checklist, parent, false)
        return ViewHolder(view)
    }

    /*
     * This function binds the data to the views at a specific position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.subtitle.text = item.subtitle
        holder.layout.setBackgroundColor(item.backgroundColor)
        holder.checkbox.isChecked = item.isChecked

        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            item.isChecked = isChecked
        }
    }

    /*
     * This function returns the total number of items in the list.
     */
    override fun getItemCount() = items.size
}